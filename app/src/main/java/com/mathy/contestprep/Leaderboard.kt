package com.mathy.contestprep

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class Leaderboard : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var dbHandler: DBhandler

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyText: TextView

    private lateinit var currentSeasonText: TextView
    private lateinit var myRankText: TextView
    private lateinit var myPointsText: TextView
    private lateinit var myTierText: TextView

    private val leaderboardPlayers = mutableListOf<LeaderboardPlayer>()
    private lateinit var adapter: LeaderboardAdapter

    data class LeaderboardPlayer(
        val uid: String,
        val displayName: String,
        val avatarId: Int,
        val seasonPoints: Int,
        val tier: String,
        val lastPointsEarned: Int,
        val lastGameTimeMillis: Long,
        val rank: Int
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_leaderboard)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Match MainActivity status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.action, theme)
        }

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        dbHandler = DBhandler(this)

        connectViews()
        setupRecycler()
        fillLocalSummary()
        loadLeaderboard()
    }

    private fun connectViews() {
        recyclerView = findViewById(R.id.leaderboard_recycler)
        progressBar = findViewById(R.id.leaderboard_progress)
        emptyText = findViewById(R.id.leaderboard_empty_text)

        currentSeasonText = findViewById(R.id.current_season_text)
        myRankText = findViewById(R.id.my_rank_text)
        myPointsText = findViewById(R.id.my_points_text)
        myTierText = findViewById(R.id.my_tier_text)
    }

    private fun setupRecycler() {
        adapter = LeaderboardAdapter(leaderboardPlayers)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun fillLocalSummary() {
        val seasonId = getCurrentSeasonId()
        val points = dbHandler.getCurrentSeasonPoints()
        val tier = dbHandler.getCurrentSeasonTier()

        currentSeasonText.text = "Season: $seasonId"
        myPointsText.text = "Your Points: $points"
        myTierText.text = "Tier: $tier"
        myRankText.text = "Rank: —"
    }

    private fun loadLeaderboard() {
        val seasonId = getCurrentSeasonId()
        val currentUid = auth.currentUser?.uid

        progressBar.visibility = View.VISIBLE
        emptyText.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE

        firestore
            .collection("seasons")
            .document(seasonId)
            .collection("leaderboard")
            .orderBy("seasonPoints", Query.Direction.DESCENDING)
            .limit(100)
            .get()
            .addOnSuccessListener { snapshot ->
                leaderboardPlayers.clear()

                var rankCounter = 1
                var myRank = "—"

                for (document in snapshot.documents) {
                    val uid = document.getString("uid") ?: document.id
                    val displayName = document.getString("displayName") ?: "Player"

                    // Your DB sync currently uses "avatarID".
                    // This also supports "avatarId" in case you rename it later.
                    val avatarId = document.getLong("avatarID")?.toInt()
                        ?: document.getLong("avatarId")?.toInt()
                        ?: 1

                    val seasonPoints = document.getLong("seasonPoints")?.toInt() ?: 0
                    val tier = document.getString("tier") ?: "Bronze"
                    val lastPointsEarned = document.getLong("lastPointsEarned")?.toInt() ?: 0
                    val lastGameTimeMillis = document.getLong("lastGameTimeMillis") ?: 0L

                    val player = LeaderboardPlayer(
                        uid = uid,
                        displayName = displayName,
                        avatarId = avatarId,
                        seasonPoints = seasonPoints,
                        tier = tier,
                        lastPointsEarned = lastPointsEarned,
                        lastGameTimeMillis = lastGameTimeMillis,
                        rank = rankCounter
                    )

                    leaderboardPlayers.add(player)

                    if (uid == currentUid) {
                        myRank = rankCounter.toString()
                    }

                    rankCounter++
                }

                adapter.notifyDataSetChanged()

                myRankText.text = "Rank: $myRank"

                progressBar.visibility = View.GONE

                if (leaderboardPlayers.isEmpty()) {
                    emptyText.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    emptyText.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener {
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.GONE
                emptyText.visibility = View.VISIBLE
                emptyText.text = "Could not load leaderboard"
            }
    }

    private fun getCurrentSeasonId(): String {
        val calendar = java.util.Calendar.getInstance()
        val year = calendar.get(java.util.Calendar.YEAR)
        val month = calendar.get(java.util.Calendar.MONTH) + 1

        val term = when (month) {
            in 1..4 -> "T1"
            in 5..8 -> "T2"
            else -> "T3"
        }

        return "${year}_$term"
    }

    inner class LeaderboardAdapter(
        private val players: List<LeaderboardPlayer>
    ) : RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {

        inner class LeaderboardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val rankText: TextView = itemView.findViewById(R.id.player_rank_text)
            val avatarImage: ImageView = itemView.findViewById(R.id.player_avatar)
            val nameText: TextView = itemView.findViewById(R.id.player_name_text)
            val tierText: TextView = itemView.findViewById(R.id.player_tier_text)
            val lastGameText: TextView = itemView.findViewById(R.id.player_last_game_text)
            val pointsText: TextView = itemView.findViewById(R.id.player_points_text)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_leaderboard_player, parent, false)

            return LeaderboardViewHolder(view)
        }

        override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
            val player = players[position]

            holder.rankText.text = "${player.rank}."
            holder.nameText.text = player.displayName
            holder.tierText.text = player.tier
            holder.pointsText.text = player.seasonPoints.toString()

            val pointsSign = if (player.lastPointsEarned >= 0) "+" else ""
            val timeText = formatTime(player.lastGameTimeMillis)

            holder.lastGameText.text = "Last: $pointsSign${player.lastPointsEarned} pts • $timeText"

            val safeAvatarId = if (player.avatarId in 1..12) player.avatarId else 1
            val avatarResId = holder.itemView.context.resources.getIdentifier(
                "av_$safeAvatarId",
                "drawable",
                holder.itemView.context.packageName
            )

            if (avatarResId != 0) {
                holder.avatarImage.setImageResource(avatarResId)
            } else {
                holder.avatarImage.setImageResource(R.drawable.info)
            }
        }

        override fun getItemCount(): Int {
            return players.size
        }

        private fun formatTime(timeMillis: Long): String {
            if (timeMillis <= 0L) return "—"

            val seconds = timeMillis / 1000.0
            return String.format("%.1fs", seconds)
        }
    }
}