package com.example.moneychild.ui.chores

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ChoresDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "ChoresList.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "chores"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_REWARD = "reward"
        private const val COLUMN_CHILD = "child"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_TITLE TEXT, " +
                "$COLUMN_REWARD TEXT, " +
                "$COLUMN_CHILD TEXT)"
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun createChore(title: String, reward: String, child: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, title)
            put(COLUMN_REWARD, reward)
            put(COLUMN_CHILD, child)
        }
        db.insert(TABLE_NAME, null, values)
    }

    fun readAllChores() : Cursor {
        val query = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase

        return db.rawQuery(query, null)
    }

    fun deleteChore(choreId: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID LIKE ?", arrayOf(choreId))
    }
}