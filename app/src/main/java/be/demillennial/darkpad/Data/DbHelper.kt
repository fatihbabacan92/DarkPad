/*
 * Copyright (c) Fatih Babacan 2019.
 * DarkPad is created by Fatih Babacan and released under the GPL3 license.
 * Please refer to the GPL3 license for additional information.
 */

package be.demillennial.darkpad.Data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper

class DbHelper(context: Context) : ManagedSQLiteOpenHelper(context, "NotesDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable("Notes", true,
            "id" to INTEGER + PRIMARY_KEY + UNIQUE,
            "title" to TEXT,
            "text" to TEXT,
            "data" to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable("Notes", true)
    }

}