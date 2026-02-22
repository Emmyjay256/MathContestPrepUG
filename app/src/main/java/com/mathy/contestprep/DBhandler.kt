package com.mathy.contestprep

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlin.compareTo

class DBhandler(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // Original ladderState table
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + NAME_COL + " TEXT,"
                + STATE_COL + " TEXT)")
        db.execSQL(query)

        // New Bookmarks table - Added here for new installs
        val bookmarkQuery = ("CREATE TABLE " + TABLE_BOOKMARKS + " ("
                + BOOKMARK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + BOOKMARK_PDF_NAME + " TEXT,"
                + BOOKMARK_PAGE + " INTEGER,"
                + BOOKMARK_TITLE + " TEXT)")
        db.execSQL(bookmarkQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Engineering safety: only add table if upgrading from version 1
        if (oldVersion < 2) {
            val bookmarkQuery = ("CREATE TABLE " + TABLE_BOOKMARKS + " ("
                    + BOOKMARK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + BOOKMARK_PDF_NAME + " TEXT,"
                    + BOOKMARK_PAGE + " INTEGER,"
                    + BOOKMARK_TITLE + " TEXT)")
            db.execSQL(bookmarkQuery)
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

    companion object {
        private const val DB_NAME = "mathState"
        private const val DB_VERSION = 2 // Incremented for the update

        private const val TABLE_NAME = "ladderState"
        private const val NAME_COL = "name"
        private const val STATE_COL = "state"

        // New constants for bookmarks
        const val TABLE_BOOKMARKS = "bookmarks"
        const val BOOKMARK_ID = "id"
        const val BOOKMARK_PDF_NAME = "pdf_name"
        const val BOOKMARK_PAGE = "page_num"
        const val BOOKMARK_TITLE = "title"
    }
}
