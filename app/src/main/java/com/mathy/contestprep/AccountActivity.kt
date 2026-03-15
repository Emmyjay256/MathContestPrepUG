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
    private var selectedAvatarId: Int = 1 // Default to 1.png

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

        // UI Binding
        val etName = findViewById<EditText>(R.id.etProfileName)
        val tvEmail = findViewById<TextView>(R.id.tvUserEmail)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val btnChangePass = findViewById<Button>(R.id.btnChangePass)

        // Load current user basic info
        val user = auth.currentUser
        if (user != null) {
            tvEmail.text = user.email
            etName.setText(user.displayName ?: "Builder")
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

            avatarView.setOnClickListener {
                selectedAvatarId = i
                // Update the main profile pic
                findViewById<ImageView>(R.id.imgAvatar).setImageResource(resId)

                // TODO: In the next step, we sync 'selectedAvatarId' to Firestore
                Toast.makeText(this, "Avatar $i selected!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            gridLayout.addView(avatarView)
        }

        dialog.setContentView(view)
        dialog.show()
    }
}