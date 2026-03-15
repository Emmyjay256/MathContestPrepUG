package com.mathy.contestprep

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlin.compareTo

class DBhandler(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

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
        private const val DB_VERSION = 3 // Incremented for the update

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
    }
}
