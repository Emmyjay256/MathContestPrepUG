package com.mathy.contestprep

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBhandler  // below variable for our course description column.
// private static final String DESCRIPTION_COL = "description";
// below variable is for our course tracks column.
//private static final String TRACKS_COL = "tracks";
// creating a constructor for our database handler.
    (context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    // below method is for creating a database by running a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + NAME_COL + " TEXT,"
                + STATE_COL + " TEXT)")

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query)
    }

    fun readVal(name: String?): String {
        val db = this.writableDatabase
        val emmm =
            db.query(TABLE_NAME, arrayOf(NAME_COL, STATE_COL), null, null, null, null, null, null)

        emmm.moveToPosition(2)

        //  Cursor resultE = db.rawQuery("SELECT * from ladderState",null);
        //String resultEE = resultE.getString(1);
        val answer = emmm.getString(1)



        emmm.close()

        return answer
    }

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

    // this method is use to add new course to our sqlite database.
    fun addNewCourse(courseName: String?, courseState: String?) {
        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.

        val db = this.writableDatabase

        // on below line we are creating a
        // variable for content values.
        val values = ContentValues()

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, courseName)
        values.put(STATE_COL, courseState)


        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values)


        // at last we are closing our
        // database after adding database.
        db.close()
    }

    fun update(courseName: String?, courseState: String?, oldName: String) {
        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.

        val db = this.writableDatabase

        // on below line we are creating a
        // variable for content values.
        val values = ContentValues()

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, courseName)
        values.put(STATE_COL, courseState)


        // after adding all values we are passing
        // content values to our table.
        // db.insert(TABLE_NAME, null, values);
        db.update(TABLE_NAME, values, "name=?", arrayOf(oldName))


        // at last we are closing our
        // database after adding database.
        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    companion object {
        // creating a constant variables for our database.
        // below variable is for our database name.
        private const val DB_NAME = "mathState"

        // below int is our database version
        private const val DB_VERSION = 1

        // below variable is for our table name.
        private const val TABLE_NAME = "ladderState"

        // below variable is for our id column.
        private const val ID_COL = "id"

        // below variable is for our course name column
        private const val NAME_COL = "name"

        // below variable id for our course duration column.
        private const val STATE_COL = "state"
    }
}