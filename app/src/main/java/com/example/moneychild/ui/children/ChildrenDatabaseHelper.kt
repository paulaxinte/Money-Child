package com.example.moneychild.ui.children

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ChildrenDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "ChildrenList.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "children"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_EMAIL TEXT)"
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addChild(name: String, email: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_EMAIL, email)
        }
        db.insert(TABLE_NAME, null, values)
    }

    fun readAllChildren() : Cursor {
        val query = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase

        return db.rawQuery(query, null)
    }

    fun deleteChild(childId: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID LIKE ?", arrayOf(childId))
    }
}