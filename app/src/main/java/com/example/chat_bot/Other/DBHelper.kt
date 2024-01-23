package com.example.chat_bot.Other

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(val context: Context, val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "localSQL", factory, 1) {

    override fun onCreate(db: SQLiteDatabase?) {

    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun createDB(){

        val db = this.writableDatabase
        db.execSQL("CREATE TABLE IF NOT EXISTS breakfast (product_name TEXT, product_callories REAL, product_PFC REAL, product_date TEXT)")
        db.execSQL("CREATE TABLE IF NOT EXISTS lunch (product_name TEXT, product_callories REAL, product_PFC REAL, product_date TEXT)")
        db.execSQL("CREATE TABLE IF NOT EXISTS dinner (product_name TEXT, product_callories REAL, product_PFC REAL, product_date TEXT)")
        db.execSQL("CREATE TABLE IF NOT EXISTS other (product_name TEXT, product_callories REAL, product_PFC REAL, product_date TEXT)")

        db.close()
    }

    fun insertToEating(table_name: String, product_name: String, product_callories: Double, product_PFC: Double, product_date: String){

        val values = ContentValues()
        val db = this.writableDatabase

        values.put("product_name", product_name)
        values.put("product_callories", product_callories)
        values.put("product_PFC", product_PFC)
        values.put("product_date", product_date)

        db.insert(table_name,null ,values)

        db.close()
    }

    fun deleteRowByColumnValue(TABLE_NAME: String, columnName: String, word: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$columnName=?", arrayOf(word))
        db.close()
    }

    fun getTableData(tableName: String?): Array<Array<String>> {

        val db = this.writableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $tableName", null)

        val tableData = mutableListOf<Array<String>>()

        if (cursor.moveToFirst()) {
            do {
                val row = mutableListOf<String>()

                for (i in 0 until cursor.columnCount) {
                    row.add(cursor.getString(i))
                }

                tableData.add(row.toTypedArray())
            } while (cursor.moveToNext())
        }

        cursor.close()
        return tableData.toTypedArray()
    }

}