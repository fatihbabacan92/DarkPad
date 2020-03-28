/*
 * Copyright (c) Fatih Babacan 2019.
 * DarkPad is created by Fatih Babacan and released under the GPL3 license.
 * Please refer to the GPL3 license for additional information.
 */

package be.demillennial.darkpad.Data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import be.demillennial.darkpad.DB_NAME
import be.demillennial.darkpad.TABLE_NAME
import org.jetbrains.anko.db.*
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper

class DbHelper(context: Context) : ManagedSQLiteOpenHelper(context, DB_NAME, null, 1) {

    companion object {
        private var instance: DbHelper? = null

        @Synchronized
        fun getInstance(context: Context): DbHelper {
            if (instance == null){
                instance = DbHelper(context.applicationContext)
            }

            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            TABLE_NAME, true,
            "id" to INTEGER + PRIMARY_KEY + UNIQUE,
            "title" to TEXT,
            "text" to TEXT,
            "date" to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(TABLE_NAME, true)
    }

}