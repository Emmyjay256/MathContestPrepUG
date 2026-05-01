package com.mathy.contestprep

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlin.compareTo
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth


data class GameScoreResult(
    val resultId: Long,
    val pointsEarned: Int,
    val pointsBefore: Int,
    val pointsAfter: Int,
    val tierBefore: String,
    val tierAfter: String,
    val rank: String,
    val seasonId: String
)

data class GameResultBackup(
    val resultId: Int,
    val uid: String,
    val gameType: String,
    val seasonId: String,
    val target: Int,
    val numbers: String,
    val expression: String,
    val finalResult: Double,
    val timeTaken: Long,
    val pointsEarned: Int,
    val pointsBefore: Int,
    val pointsAfter: Int,
    val tierBefore: String,
    val tierAfter: String,
    val playedAt: Long
)
class DBhandler(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    private lateinit var auth: FirebaseAuth

    init {
        auth = FirebaseAuth.getInstance()
    }


    override fun onCreate(db: SQLiteDatabase) {
        // Existing Ladder Table
        db.execSQL("CREATE TABLE $TABLE_NAME ($NAME_COL TEXT, $STATE_COL TEXT)")

        // Existing Bookmarks Table
        db.execSQL("CREATE TABLE $TABLE_BOOKMARKS ($BOOKMARK_ID INTEGER PRIMARY KEY AUTOINCREMENT, $BOOKMARK_PDF_NAME TEXT, $BOOKMARK_PAGE INTEGER, $BOOKMARK_TITLE TEXT)")

        // NEW: User Profile Table
        val userQuery = ("CREATE TABLE $TABLE_USER ("
                + "$USER_UID TEXT PRIMARY KEY,"
                + "$USER_NAME TEXT,"
                + "$USER_EMAIL TEXT,"
                + "$USER_AVATAR INTEGER,"
                + "$USER_CREDITS INTEGER,"
                + "$USER_STREAK INTEGER,"
                + "$USER_SOLVED INTEGER,"
                + "$USER_LAST_LOGIN LONG)")
        db.execSQL(userQuery)

        // NEW: Sessions Table
        val sessionQuery = ("CREATE TABLE $TABLE_SESSIONS ("
                + "$SESSION_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$SESSION_LOGIN LONG,"
                + "$SESSION_LOGOUT LONG,"
                + "$SESSION_DURATION LONG)")
        db.execSQL(sessionQuery)


        // NEW: Game tables
        createGameTables(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            // Migration from 1 to 2 (Add Bookmarks)
            db.execSQL("CREATE TABLE $TABLE_BOOKMARKS ($BOOKMARK_ID INTEGER PRIMARY KEY AUTOINCREMENT, $BOOKMARK_PDF_NAME TEXT, $BOOKMARK_PAGE INTEGER, $BOOKMARK_TITLE TEXT)")
        }
        if (oldVersion < 3) {
            // Migration from 2 to 3 (Add Users and Sessions)
            db.execSQL("CREATE TABLE $TABLE_USER ($USER_UID TEXT PRIMARY KEY, $USER_NAME TEXT, $USER_EMAIL TEXT, $USER_AVATAR INTEGER, $USER_CREDITS INTEGER, $USER_STREAK INTEGER, $USER_SOLVED INTEGER, $USER_LAST_LOGIN LONG)")
            db.execSQL("CREATE TABLE $TABLE_SESSIONS ($SESSION_ID INTEGER PRIMARY KEY AUTOINCREMENT, $SESSION_LOGIN LONG, $SESSION_LOGOUT LONG, $SESSION_DURATION LONG)")
        }
        if (oldVersion < 4) {
            createGameTables(db)
        }
    }

    // RESTORED: Your original readVal with specific cursor positioning
    fun readVal(name: String?): String {
        val db = this.writableDatabase
        val emmm = db.query(TABLE_NAME, arrayOf(NAME_COL, STATE_COL), null, null, null, null, null, null)

        emmm.moveToPosition(2) // Kept exactly as you had it
        val answer = emmm.getString(1)
        emmm.close()

        return answer
    }


    // Check if a specific page in a specific PDF is bookmarked
    fun isBookmarked(pdfName: String, page: Int): Boolean {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_BOOKMARKS,
            arrayOf(BOOKMARK_ID),
            "$BOOKMARK_PDF_NAME = ? AND $BOOKMARK_PAGE = ?",
            arrayOf(pdfName, page.toString()),
            null, null, null
        )
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    // RESTORED: Your starter function for initial data
    fun starter() {
        addNewCourse("one", "false")
        addNewCourse("one", "false")
        addNewCourse("two", "false")
        addNewCourse("three", "false")
        addNewCourse("four", "false")
        addNewCourse("five", "false")
        addNewCourse("six", "false")
        addNewCourse("seven", "false")
        addNewCourse("eight", "false")
        addNewCourse("nine", "false")
        addNewCourse("ten", "false")
        addNewCourse("eleven", "false")
        addNewCourse("twelve", "false")
        addNewCourse("thirteen", "false")
        addNewCourse("fourteen", "false")
        addNewCourse("fifteen", "false")
        addNewCourse("sixteen", "false")
        addNewCourse("seventeen", "false")
        addNewCourse("eighteen", "false")
        addNewCourse("nineteen", "false")
        addNewCourse("twenty", "false")
        addNewCourse("twentyone", "false")
        addNewCourse("twentytwo", "false")
        addNewCourse("twentythree", "false")
        addNewCourse("twentyfour", "false")
        addNewCourse("twentyfive", "false")
        addNewCourse("twentysix", "false")
        addNewCourse("twentyseven", "false")
    }

    fun addNewCourse(courseName: String?, courseState: String?) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME_COL, courseName)
        values.put(STATE_COL, courseState)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun update(courseName: String?, courseState: String?, oldName: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME_COL, courseName)
        values.put(STATE_COL, courseState)
        db.update(TABLE_NAME, values, "name=?", arrayOf(oldName))
        db.close()
    }

    // --- ADDITION: Bookmark management ---
    fun addBookmark(pdfName: String, page: Int, title: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(BOOKMARK_PDF_NAME, pdfName)
        values.put(BOOKMARK_PAGE, page)
        values.put(BOOKMARK_TITLE, title)
        db.insert(TABLE_BOOKMARKS, null, values)
        db.close()
    }


















    //Create games table
    private fun createGameTables(db: SQLiteDatabase) {
        val seasonQuery = ("CREATE TABLE IF NOT EXISTS $TABLE_GAME_SEASON ("
                + "$SEASON_ID TEXT PRIMARY KEY,"
                + "$SEASON_START LONG,"
                + "$SEASON_END LONG,"
                + "$SEASON_POINTS INTEGER,"
                + "$SEASON_LAST_UPDATED LONG)")
        db.execSQL(seasonQuery)

        val resultsQuery = ("CREATE TABLE IF NOT EXISTS $TABLE_GAME_RESULTS ("
                + "$RESULT_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$RESULT_UID TEXT,"
                + "$RESULT_GAME_TYPE TEXT,"
                + "$RESULT_SEASON_ID TEXT,"
                + "$RESULT_TARGET INTEGER,"
                + "$RESULT_NUMBERS TEXT,"
                + "$RESULT_EXPRESSION TEXT,"
                + "$RESULT_FINAL_RESULT REAL,"
                + "$RESULT_TIME_TAKEN LONG,"
                + "$RESULT_POINTS_EARNED INTEGER,"
                + "$RESULT_POINTS_BEFORE INTEGER,"
                + "$RESULT_POINTS_AFTER INTEGER,"
                + "$RESULT_TIER_BEFORE TEXT,"
                + "$RESULT_TIER_AFTER TEXT,"
                + "$RESULT_PLAYED_AT LONG,"
                + "$RESULT_IS_SYNCED INTEGER)")
        db.execSQL(resultsQuery)
    }

    //numbers game scoring logic

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

    private fun getSeasonStartTime(): Long {
        val calendar = java.util.Calendar.getInstance()
        val month = calendar.get(java.util.Calendar.MONTH) + 1

        val startMonth = when (month) {
            in 1..4 -> java.util.Calendar.JANUARY
            in 5..8 -> java.util.Calendar.MAY
            else -> java.util.Calendar.SEPTEMBER
        }

        calendar.set(java.util.Calendar.MONTH, startMonth)
        calendar.set(java.util.Calendar.DAY_OF_MONTH, 1)
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
        calendar.set(java.util.Calendar.MINUTE, 0)
        calendar.set(java.util.Calendar.SECOND, 0)
        calendar.set(java.util.Calendar.MILLISECOND, 0)

        return calendar.timeInMillis
    }

    private fun getSeasonEndTime(): Long {
        val calendar = java.util.Calendar.getInstance()
        val month = calendar.get(java.util.Calendar.MONTH) + 1

        val endMonth = when (month) {
            in 1..4 -> java.util.Calendar.APRIL
            in 5..8 -> java.util.Calendar.AUGUST
            else -> java.util.Calendar.DECEMBER
        }

        calendar.set(java.util.Calendar.MONTH, endMonth)
        calendar.set(java.util.Calendar.DAY_OF_MONTH, calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH))
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 23)
        calendar.set(java.util.Calendar.MINUTE, 59)
        calendar.set(java.util.Calendar.SECOND, 59)
        calendar.set(java.util.Calendar.MILLISECOND, 999)

        return calendar.timeInMillis
    }

    private fun isFirstHalfOfSeason(): Boolean {
        val now = System.currentTimeMillis()
        val start = getSeasonStartTime()
        val end = getSeasonEndTime()
        val midpoint = start + ((end - start) / 2)

        return now <= midpoint
    }

    private fun getOrCreateSeasonPoints(db: SQLiteDatabase, seasonId: String): Int {
        val cursor = db.query(
            TABLE_GAME_SEASON,
            arrayOf(SEASON_POINTS),
            "$SEASON_ID=?",
            arrayOf(seasonId),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            val points = cursor.getInt(cursor.getColumnIndexOrThrow(SEASON_POINTS))
            cursor.close()
            return points
        }

        cursor.close()

        val values = ContentValues().apply {
            put(SEASON_ID, seasonId)
            put(SEASON_START, getSeasonStartTime())
            put(SEASON_END, getSeasonEndTime())
            put(SEASON_POINTS, 0)
            put(SEASON_LAST_UPDATED, System.currentTimeMillis())
        }

        db.insert(TABLE_GAME_SEASON, null, values)

        return 0
    }

    private fun calculatePoints(timeTakenMillis: Long, currentSeasonPoints: Int): Int {
        val seconds = timeTakenMillis / 1000.0

        val maxPoints = if (isFirstHalfOfSeason()) {
            100
        } else {
            50
        }

        val earnedPoints = when {
            seconds <= 8.0 -> maxPoints
            seconds <= 15.0 -> (maxPoints * 0.80).toInt()
            seconds <= 25.0 -> (maxPoints * 0.60).toInt()
            seconds <= 35.0 -> (maxPoints * 0.50).toInt()
            seconds <= 50.0 -> (maxPoints * 0.25).toInt()
            seconds <= 65.0 -> (maxPoints * 0.10).toInt()
            else -> 0
        }

        if (currentSeasonPoints < 7000) {
            return earnedPoints
        }

        return when {
            seconds <= 65.0 -> earnedPoints
            seconds <= 80.0 -> 0
            seconds <= 100.0 -> -5
            seconds <= 120.0 -> -10
            else -> -15
        }
    }

    private fun calculateRank(timeTakenMillis: Long): String {
        val seconds = timeTakenMillis / 1000.0

        return when {
            seconds <= 8.0 -> "S"
            seconds <= 15.0 -> "A"
            seconds <= 25.0 -> "B"
            seconds <= 35.0 -> "C"
            seconds <= 65.0 -> "D"
            else -> "No Points"
        }
    }

    private fun calculateTier(points: Int): String {
        return when {
            points >= 20000 -> "Mythic"
            points >= 10000 -> "Legendary"
            points >= 8500 -> "Master"
            points >= 7000 -> "Elite"
            points >= 5500 -> "Diamond"
            points >= 4000 -> "Platinum"
            points >= 2500 -> "Gold"
            points >= 1000 -> "Silver"
            else -> "Bronze"
        }
    }
//function to save game results
fun saveSixNumbersGameResult(
    uid: String,
    target: Int,
    numbers: List<Int>,
    expression: String,
    finalResult: Double,
    timeTakenMillis: Long
): GameScoreResult {
    val db = this.writableDatabase

    val seasonId = getCurrentSeasonId()
    val pointsBefore = getOrCreateSeasonPoints(db, seasonId)
    val tierBefore = calculateTier(pointsBefore)

    val pointsEarned = calculatePoints(timeTakenMillis, pointsBefore)

    var pointsAfter = pointsBefore + pointsEarned

    // Season points should never go below zero.
    if (pointsAfter < 0) {
        pointsAfter = 0
    }

    val tierAfter = calculateTier(pointsAfter)
    val rank = calculateRank(timeTakenMillis)
    val playedAt = System.currentTimeMillis()

    val numbersAsText = numbers.joinToString(",")

    val resultValues = ContentValues().apply {
        put(RESULT_UID, uid)
        put(RESULT_GAME_TYPE, "six_numbers")
        put(RESULT_SEASON_ID, seasonId)
        put(RESULT_TARGET, target)
        put(RESULT_NUMBERS, numbersAsText)
        put(RESULT_EXPRESSION, expression)
        put(RESULT_FINAL_RESULT, finalResult)
        put(RESULT_TIME_TAKEN, timeTakenMillis)
        put(RESULT_POINTS_EARNED, pointsEarned)
        put(RESULT_POINTS_BEFORE, pointsBefore)
        put(RESULT_POINTS_AFTER, pointsAfter)
        put(RESULT_TIER_BEFORE, tierBefore)
        put(RESULT_TIER_AFTER, tierAfter)
        put(RESULT_PLAYED_AT, playedAt)
        put(RESULT_IS_SYNCED, 0)
    }

    val resultId = db.insert(TABLE_GAME_RESULTS, null, resultValues)

    val seasonValues = ContentValues().apply {
        put(SEASON_POINTS, pointsAfter)
        put(SEASON_LAST_UPDATED, playedAt)
    }

    db.update(
        TABLE_GAME_SEASON,
        seasonValues,
        "$SEASON_ID=?",
        arrayOf(seasonId)
    )

    db.close()

    return GameScoreResult(
        resultId = resultId,
        pointsEarned = pointsEarned,
        pointsBefore = pointsBefore,
        pointsAfter = pointsAfter,
        tierBefore = tierBefore,
        tierAfter = tierAfter,
        rank = rank,
        seasonId = seasonId
    )
}

    //sync the unsynced game rows
    fun getUnsyncedGameResults(): List<GameResultBackup> {
        val results = mutableListOf<GameResultBackup>()
        val db = this.readableDatabase


        val cursor = db.query(
            TABLE_GAME_RESULTS,
            null,
            "$RESULT_IS_SYNCED=?",
            arrayOf("0"),
            null,
            null,
            "$RESULT_PLAYED_AT ASC"
        )

        if (cursor.moveToFirst()) {
            do {
                val result = GameResultBackup(
                    resultId = cursor.getInt(cursor.getColumnIndexOrThrow(RESULT_ID)),
                    uid = cursor.getString(cursor.getColumnIndexOrThrow(RESULT_UID)),
                    gameType = cursor.getString(cursor.getColumnIndexOrThrow(RESULT_GAME_TYPE)),
                    seasonId = cursor.getString(cursor.getColumnIndexOrThrow(RESULT_SEASON_ID)),
                    target = cursor.getInt(cursor.getColumnIndexOrThrow(RESULT_TARGET)),
                    numbers = cursor.getString(cursor.getColumnIndexOrThrow(RESULT_NUMBERS)),
                    expression = cursor.getString(cursor.getColumnIndexOrThrow(RESULT_EXPRESSION)),
                    finalResult = cursor.getDouble(cursor.getColumnIndexOrThrow(RESULT_FINAL_RESULT)),
                    timeTaken = cursor.getLong(cursor.getColumnIndexOrThrow(RESULT_TIME_TAKEN)),
                    pointsEarned = cursor.getInt(cursor.getColumnIndexOrThrow(RESULT_POINTS_EARNED)),
                    pointsBefore = cursor.getInt(cursor.getColumnIndexOrThrow(RESULT_POINTS_BEFORE)),
                    pointsAfter = cursor.getInt(cursor.getColumnIndexOrThrow(RESULT_POINTS_AFTER)),
                    tierBefore = cursor.getString(cursor.getColumnIndexOrThrow(RESULT_TIER_BEFORE)),
                    tierAfter = cursor.getString(cursor.getColumnIndexOrThrow(RESULT_TIER_AFTER)),
                    playedAt = cursor.getLong(cursor.getColumnIndexOrThrow(RESULT_PLAYED_AT))
                )

                results.add(result)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return results
    }

    fun markGameResultSynced(resultId: Int) {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(RESULT_IS_SYNCED, 1)
        }

        db.update(
            TABLE_GAME_RESULTS,
            values,
            "$RESULT_ID=?",
            arrayOf(resultId.toString())
        )

        db.close()
    }

    fun getCurrentSeasonPoints(): Int {
        val db = this.writableDatabase
        val seasonId = getCurrentSeasonId()
        val points = getOrCreateSeasonPoints(db, seasonId)
        db.close()
        return points
    }

    fun getCurrentSeasonTier(): String {
        val points = getCurrentSeasonPoints()
        return calculateTier(points)
    }

    fun getTotalGamesPlayed(uid: String): Int {
        val db = this.readableDatabase

        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM $TABLE_GAME_RESULTS WHERE $RESULT_UID = ?",
            arrayOf(uid)
        )

        var totalGames = 0

        if (cursor.moveToFirst()) {
            totalGames = cursor.getInt(0)
        }

        cursor.close()
        db.close()

        return totalGames
    }



    // ---------------------------------------------------------
// FIRESTORE BACKUP FOR UNSYNCED GAME RESULTS
// ---------------------------------------------------------

    fun syncUnsyncedGameResultsToFirestore(
        context: Context,
        uid: String,
        onComplete: ((Boolean, Int) -> Unit)? = null
    ) {
        android.util.Log.d("GameSync", "Starting sync for uid: $uid")

        if (!hasInternetConnection(context)) {
            android.util.Log.e("GameSync", "No internet connection")
            onComplete?.invoke(false, 0)
            return
        }

        val unsyncedResults = getUnsyncedGameResults()

        android.util.Log.d("GameSync", "Unsynced results count: ${unsyncedResults.size}")

        if (unsyncedResults.isEmpty()) {
            onComplete?.invoke(true, 0)
            return
        }

        val firestore = FirebaseFirestore.getInstance()

        var finishedCount = 0
        var successCount = 0
        var failedCount = 0

        val localProfile = getUserProfile(auth.currentUser!!.uid)
        val displayName = localProfile?.name ?: "Player"
        val avatarId = localProfile?.avatarId ?: 1

        for (result in unsyncedResults) {
            android.util.Log.d(
                "GameSync",
                "Trying to sync resultId=${result.resultId}, seasonId=${result.seasonId}, pointsAfter=${result.pointsAfter}"
            )

            val resultMap = hashMapOf(
                "resultId" to result.resultId,
                "uid" to uid,
                "gameType" to result.gameType,
                "seasonId" to result.seasonId,
                "target" to result.target,
                "numbers" to result.numbers,
                "expression" to result.expression,
                "finalResult" to result.finalResult,
                "timeTaken" to result.timeTaken,
                "pointsEarned" to result.pointsEarned,
                "pointsBefore" to result.pointsBefore,
                "pointsAfter" to result.pointsAfter,
                "tierBefore" to result.tierBefore,
                "tierAfter" to result.tierAfter,
                "playedAt" to result.playedAt,
                "syncedAt" to System.currentTimeMillis()
            )

            val leaderboardMap = hashMapOf(
                "uid" to uid,
                "displayName" to displayName,
                "avatarID" to avatarId,
                "seasonId" to result.seasonId,
                "seasonPoints" to result.pointsAfter,
                "tier" to result.tierAfter,
                "lastPointsEarned" to result.pointsEarned,
                "lastGameTimeMillis" to result.timeTaken,
                "lastPlayedAt" to result.playedAt,
                "updatedAt" to System.currentTimeMillis()
            )

            firestore
                .collection("users")
                .document(uid)
                .collection("game_results")
                .document(result.resultId.toString())
                .set(resultMap)
                .addOnSuccessListener {
                    android.util.Log.d("GameSync", "Game result uploaded: ${result.resultId}")

                    firestore
                        .collection("seasons")
                        .document(result.seasonId)
                        .collection("leaderboard")
                        .document(uid)
                        .set(leaderboardMap, com.google.firebase.firestore.SetOptions.merge())
                        .addOnSuccessListener {
                            android.util.Log.d("GameSync", "Leaderboard updated for result: ${result.resultId}")

                            markGameResultSynced(result.resultId)

                            successCount++
                            finishedCount++

                            if (finishedCount == unsyncedResults.size) {
                                android.util.Log.d("GameSync", "Sync complete. Success count: $successCount")
                                onComplete?.invoke(failedCount == 0, successCount)
                            }
                        }
                        .addOnFailureListener { e ->
                            android.util.Log.e(
                                "GameSync",
                                "Leaderboard upload failed for result ${result.resultId}: ${e.message}",
                                e
                            )

                            failedCount++
                            finishedCount++

                            if (finishedCount == unsyncedResults.size) {
                                onComplete?.invoke(false, successCount)
                            }
                        }
                }
                .addOnFailureListener { e ->
                    android.util.Log.e(
                        "GameSync",
                        "Game result upload failed for result ${result.resultId}: ${e.message}",
                        e
                    )

                    failedCount++
                    finishedCount++

                    if (finishedCount == unsyncedResults.size) {
                        onComplete?.invoke(false, successCount)
                    }
                }
        }
    }

    private fun hasInternetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }



















    // Update or Initialize User Profile
    fun saveUserProfile(uid: String, name: String, email: String, avatar: Int) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(USER_UID, uid)
            put(USER_NAME, name)
            put(USER_EMAIL, email)
            put(USER_AVATAR, avatar)
        }
        // "Insert with Conflict Replace" is the cleanest way to handle existing UIDs
        db.insertWithOnConflict(TABLE_USER, null, values, SQLiteDatabase.CONFLICT_REPLACE)
        db.close()
    }

    // Quick Update for just the Name
    fun updateName(uid: String, newName: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply { put(USER_NAME, newName) }
        db.update(TABLE_USER, values, "$USER_UID=?", arrayOf(uid))
        db.close()
    }

    // Quick Update for just the Avatar
    fun updateAvatar(uid: String, avatarId: Int) {
        val db = this.writableDatabase
        val values = ContentValues().apply { put(USER_AVATAR, avatarId) }
        db.update(TABLE_USER, values, "$USER_UID=?", arrayOf(uid))
        db.close()
    }

    // Update Stats (Credits, Solved Count, etc.)
    fun updateStats(uid: String, credits: Int, solved: Int, streak: Int) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(USER_CREDITS, credits)
            put(USER_SOLVED, solved)
            put(USER_STREAK, streak)
            put(USER_LAST_LOGIN, System.currentTimeMillis())
        }
        db.update(TABLE_USER, values, "$USER_UID=?", arrayOf(uid))
        db.close()
    }

    // The "Intrusive" Telemetry: Log a new Session
    fun logSession(login: Long, logout: Long) {
        val db = this.writableDatabase
        val duration = logout - login
        val values = ContentValues().apply {
            put(SESSION_LOGIN, login)
            put(SESSION_LOGOUT, logout)
            put(SESSION_DURATION, duration)
        }
        db.insert(TABLE_SESSIONS, null, values)
        db.close()
    }







    fun updateAppOpenStreak(uid: String): Int {
        val db = this.writableDatabase

        val todayKey = getTodayKey()
        val yesterdayKey = getYesterdayKey()

        var currentStreak = 0
        var lastLoginKey = ""

        val cursor = db.query(
            TABLE_USER,
            arrayOf(USER_STREAK, USER_LAST_LOGIN),
            "$USER_UID=?",
            arrayOf(uid),
            null,
            null,
            null
        )

        val userExists = cursor.moveToFirst()

        if (userExists) {
            currentStreak = cursor.getInt(cursor.getColumnIndexOrThrow(USER_STREAK))

            val savedLastLogin = cursor.getLong(cursor.getColumnIndexOrThrow(USER_LAST_LOGIN))
            lastLoginKey = savedLastLogin.toString()
        }

        cursor.close()

        val newStreak = when {
            lastLoginKey == todayKey -> {
                currentStreak
            }

            lastLoginKey == yesterdayKey -> {
                currentStreak + 1
            }

            else -> {
                1
            }
        }

        val values = ContentValues().apply {
            put(USER_STREAK, newStreak)
            put(USER_LAST_LOGIN, todayKey.toLong())
        }

        if (userExists) {
            db.update(
                TABLE_USER,
                values,
                "$USER_UID=?",
                arrayOf(uid)
            )
        } else {
            values.put(USER_UID, uid)
            values.put(USER_NAME, "Player")
            values.put(USER_EMAIL, "")
            values.put(USER_AVATAR, 1)
            values.put(USER_CREDITS, 0)
            values.put(USER_SOLVED, 0)

            db.insert(TABLE_USER, null, values)
        }

        db.close()

        return newStreak
    }

    private fun getTodayKey(): String {
        val calendar = java.util.Calendar.getInstance()

        val year = calendar.get(java.util.Calendar.YEAR)
        val dayOfYear = calendar.get(java.util.Calendar.DAY_OF_YEAR)

        return "$year${dayOfYear.toString().padStart(3, '0')}"
    }

    private fun getYesterdayKey(): String {
        val calendar = java.util.Calendar.getInstance()
        calendar.add(java.util.Calendar.DAY_OF_YEAR, -1)

        val year = calendar.get(java.util.Calendar.YEAR)
        val dayOfYear = calendar.get(java.util.Calendar.DAY_OF_YEAR)

        return "$year${dayOfYear.toString().padStart(3, '0')}"
    }













    fun getUserProfile(uid: String): UserProfile? {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_USER, null, "$USER_UID=?", arrayOf(uid), null, null, null)

        return if (cursor.moveToFirst()) {
            val profile = UserProfile(
                uid = cursor.getString(cursor.getColumnIndexOrThrow(USER_UID)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(USER_NAME)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(USER_EMAIL)),
                avatarId = cursor.getInt(cursor.getColumnIndexOrThrow(USER_AVATAR)),
                credits = cursor.getInt(cursor.getColumnIndexOrThrow(USER_CREDITS)),
                streak = cursor.getInt(cursor.getColumnIndexOrThrow(USER_STREAK)),
                solvedCount = cursor.getInt(cursor.getColumnIndexOrThrow(USER_SOLVED))
            )
            cursor.close()
            profile
        } else {
            cursor.close()
            null
        }
    }



















    companion object {
        private const val DB_NAME = "mathState"
        private const val DB_VERSION = 4 // Incremented for the update

        private const val TABLE_NAME = "ladderState"
        private const val NAME_COL = "name"
        private const val STATE_COL = "state"

        // New constants for bookmarks
        const val TABLE_BOOKMARKS = "bookmarks"
        const val BOOKMARK_ID = "id"
        const val BOOKMARK_PDF_NAME = "pdf_name"
        const val BOOKMARK_PAGE = "page_num"
        const val BOOKMARK_TITLE = "title"

        // Increment this to 3

        // User Profile Table
        const val TABLE_USER = "user_profile"
        const val USER_UID = "uid"
        const val USER_NAME = "display_name"
        const val USER_EMAIL = "email"
        const val USER_AVATAR = "avatar_id"
        const val USER_CREDITS = "credits"
        const val USER_STREAK = "streak"
        const val USER_SOLVED = "solved_count"
        const val USER_LAST_LOGIN = "last_login"

        // Sessions Table (Telemetry)
        const val TABLE_SESSIONS = "user_sessions"
        const val SESSION_ID = "session_id"
        const val SESSION_LOGIN = "login_time"
        const val SESSION_LOGOUT = "logout_time"
        const val SESSION_DURATION = "duration"
        // Game Season Profile Table
        const val TABLE_GAME_SEASON = "game_season_profile"
        const val SEASON_ID = "season_id"
        const val SEASON_START = "season_start"
        const val SEASON_END = "season_end"
        const val SEASON_POINTS = "season_points"
        const val SEASON_LAST_UPDATED = "last_updated"

        // Game Results Table
        const val TABLE_GAME_RESULTS = "game_results"
        const val RESULT_ID = "result_id"
        const val RESULT_UID = "uid"
        const val RESULT_GAME_TYPE = "game_type"
        const val RESULT_SEASON_ID = "season_id"
        const val RESULT_TARGET = "target"
        const val RESULT_NUMBERS = "numbers"
        const val RESULT_EXPRESSION = "expression"
        const val RESULT_FINAL_RESULT = "final_result"
        const val RESULT_TIME_TAKEN = "time_taken"
        const val RESULT_POINTS_EARNED = "points_earned"
        const val RESULT_POINTS_BEFORE = "points_before"
        const val RESULT_POINTS_AFTER = "points_after"
        const val RESULT_TIER_BEFORE = "tier_before"
        const val RESULT_TIER_AFTER = "tier_after"
        const val RESULT_PLAYED_AT = "played_at"
        const val RESULT_IS_SYNCED = "is_synced"
    }
}
