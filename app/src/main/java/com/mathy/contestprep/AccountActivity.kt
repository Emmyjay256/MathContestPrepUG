package com.mathy.contestprep

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.material.bottomsheet.BottomSheetDialog
import android.view.LayoutInflater
import android.view.View

class AccountActivity : AppCompatActivity() {

    // Firebase instances
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var selectedAvatarId: Int = 0 // Default to 1.png

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_account)

        // Standard Theme Sync (Keeping your Action color status bar)
        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = this.resources.getColor(R.color.action, theme)
        }

        // Apply Edge-to-Edge Padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_account)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()


        // --- INDEPENDENT LOCAL SYNC BLOCK ---
        val userId = auth.currentUser?.uid
        val dbHandler = DBhandler(this)

        try {
            // Direct assignment: Attempt to pull exactly what is in the local DB
            val localProfile = dbHandler.getUserProfile(userId!!)
            selectedAvatarId = localProfile!!.avatarId
        } catch (e: Exception) {
            // Fail Gracefully: If local fails, we default to 1
            // The Cloud load (Step 2 in loadUserData) will correct this later anyway
            selectedAvatarId = 1
        }
// ------------------------------------


        loadUserData()


        // UI Binding
        val etName = findViewById<TextView>(R.id.etProfileName)
        val tvEmail = findViewById<TextView>(R.id.tvUserEmail)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val btnChangePass = findViewById<Button>(R.id.btnChangePass)

        etName.setOnClickListener {
            showNameInputDialog(etName.text.toString())
        }

        // Load current user basic info
        val user = auth.currentUser
        if (user != null) {
           // Toast.makeText(this, "User info loaded", Toast.LENGTH_SHORT).show()
            tvEmail.text = user.email
           // etName.setText(user.displayName ?: "Builder")
           // Toast.makeText(this, user.displayName, Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
        }

        // 1. LOGOUT LOGIC
        btnLogout.setOnClickListener {
            auth.signOut()
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
            finish() // Return to MainActivity
        }

        // 2. PASSWORD RESET LOGIC (Firebase Magic)
        btnChangePass.setOnClickListener {
            user?.email?.let { email ->
                auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Reset link sent to your email", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }


        val imgAvatar = findViewById<ImageView>(R.id.imgAvatar)

        // 3. AVATAR PICKER LOGIC
        imgAvatar.setOnClickListener {
            showAvatarPickerDialog()
        }
        // 3. TODO: Set up the Avatar Picker Dialog
        // 4. TODO: Set up the Name change listener to sync with Firestore
    }






    private fun loadUserData() {
        val userId = auth.currentUser?.uid ?: return
        val dbHandler = DBhandler(this)
        val etName = findViewById<TextView>(R.id.etProfileName)
        val imgAvatar = findViewById<ImageView>(R.id.imgAvatar)

        // Step 1: Load from Local DB immediately (High speed)
        val localProfile = dbHandler.getUserProfile(userId)
        localProfile?.let {
            etName.setText(it.name)
            val resId = resources.getIdentifier("av_${it.avatarId}", "drawable", packageName)
            imgAvatar.setImageResource(resId)
        }

        // Step 2: Fetch from Firestore to ensure we have the latest (High accuracy)
        db.collection("users").document(userId).get().addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val remoteName = document.getString("displayName")
                val remoteAvatar = document.getLong("avatarId")?.toInt() ?: 1

                // Update UI only if remote name is actually set
                if (!remoteName.isNullOrEmpty()) {
                    etName.setText(remoteName)
                }

                val resId = resources.getIdentifier("av_$remoteAvatar", "drawable", packageName)
                imgAvatar.setImageResource(resId)

                // Update Local DB: Keep local name if remote is null to maintain integrity
                val finalName = remoteName ?: etName.text.toString()
                dbHandler.saveUserProfile(userId, finalName, auth.currentUser?.email ?: "", remoteAvatar)
            }
        }
    }








    private fun showAvatarPickerDialog() {
        val dialog = BottomSheetDialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_avatar_picker, null)

        // We will find our grid in this view
        val gridLayout = view.findViewById<GridLayout>(R.id.avatarGrid)

        // Loop through your 12 avatars
        for (i in 1..12) {
            val avatarView = LayoutInflater.from(this).inflate(R.layout.item_avatar, gridLayout, false)
            val image = avatarView.findViewById<ImageView>(R.id.imgItemAvatar)

            // Get resource ID dynamically (assumes names are av_1, av_2...)
            val resId = resources.getIdentifier("av_$i", "drawable", packageName)
            image.setImageResource(resId)



            // Inside showAvatarPickerDialog() -> avatarView.setOnClickListener
            avatarView.setOnClickListener {
                val newAvatarId = i // Local variable for the loop index

                // Update the class variable IMMEDIATELY so onPause has the right ID
                selectedAvatarId = newAvatarId
                val resId = resources.getIdentifier("av_$newAvatarId", "drawable", packageName)

                // 1. Instant UI Feedback
                findViewById<ImageView>(R.id.imgAvatar).setImageResource(resId)

                val userId = auth.currentUser?.uid
                if (userId != null) {
                    val userMap = hashMapOf("avatarId" to newAvatarId)

                    // 2. Update Firestore
                    db.collection("users").document(userId)
                        .set(userMap, com.google.firebase.firestore.SetOptions.merge())
                        .addOnSuccessListener {
                            // 3. FORCE Local Update immediately on success
                            val dbHandler = DBhandler(this@AccountActivity)
                            dbHandler.updateAvatar(userId, newAvatarId)

                            // Set the global selectedAvatarId so loadUserData doesn't get confused
                            selectedAvatarId = newAvatarId

                            Toast.makeText(this@AccountActivity, "Sync Successful!", Toast.LENGTH_SHORT).show()
                        }
                }
                dialog.dismiss()
            }



            gridLayout.addView(avatarView)
        }

        dialog.setContentView(view)
        dialog.show()
    }








    private fun showNameInputDialog(currentName: String) {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Update Name")

        // Create a dynamic EditText for the dialog
        val input = EditText(this)
        input.setText(currentName)
        input.setSelection(input.text.length) // Cursor at the end
        builder.setView(input)

        builder.setPositiveButton("Save") { _, _ ->
            val newName = input.text.toString().trim()
            if (newName.isNotEmpty()) {
                syncNameChange(newName)
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        builder.show()
    }






    private fun syncNameChange(newName: String) {
        val userId = auth.currentUser?.uid ?: return
        val tvName = findViewById<TextView>(R.id.etProfileName)

        // 1. Instant UI update
        tvName.text = newName

        // 2. Update Remote (Firestore)
        val userMap = hashMapOf("displayName" to newName)
        db.collection("users").document(userId)
            .set(userMap, com.google.firebase.firestore.SetOptions.merge())
            .addOnSuccessListener {
                // 3. Update Local (SQLite)
                val dbHandler = DBhandler(this)
                dbHandler.updateName(userId, newName)
                Toast.makeText(this, "Name synced!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Sync failed, but saved locally", Toast.LENGTH_SHORT).show()
            }
    }








    override fun onPause() {
        super.onPause()
        saveCurrentStateToLocal()
    }

    private fun saveCurrentStateToLocal() {
        val userId = auth.currentUser?.uid ?: return
        val dbHandler = DBhandler(this)

        // Scrape data directly from the UI elements
        val currentVisibleName = findViewById<TextView>(R.id.etProfileName).text.toString()
        val currentEmail = auth.currentUser?.email ?: ""

        // selectedAvatarId is already updated in your ClickListeners
        // We use saveUserProfile because it uses CONFLICT_REPLACE
        dbHandler.saveUserProfile(
            userId,
            currentVisibleName,
            currentEmail,
            selectedAvatarId
        )
    }
}