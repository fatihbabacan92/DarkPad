/*
 * Copyright (c) Fatih Babacan 2019.
 * DarkPad is created by Fatih Babacan and released under the GPL3 license.
 * Please refer to the GPL3 license for additional information.
 */

package be.demillennial.darkpad.Data

import android.content.Context
import be.demillennial.darkpad.Notes.Note
import be.demillennial.darkpad.Data.DbHelper
import org.jetbrains.anko.db.*
import android.database.sqlite.SQLiteDatabase
import be.demillennial.darkpad.TABLE_NAME
import be.demillennial.darkpad.database
import be.demillennial.darkpad.dateFormat

class NotesDb(val context: Context) {

    fun create(note: Note) = context.database.use {
        insert(TABLE_NAME,
            "title" to note.title,
            "text" to note.text,
            "date" to dateFormat.format(note.date))
    }

    fun update(note: Note) = context.database.use {
        update(TABLE_NAME,
            "title" to note.title,
            "text" to note.text)
            .whereArgs("id = {noteId}", "noteId" to note.id)
            .exec()

    }

    fun delete(note: Note) = context.database.use {
        delete(TABLE_NAME, "id = {noteId}", "noteId" to note.id)
    }

    fun getAll() : ArrayList<Note> = context.database.use {
        val notes = ArrayList<Note>()

        select(TABLE_NAME, "id", "title", "text", "date")
            .parseList(object: MapRowParser<List<Note>>{
                override fun parseRow(columns: Map<String, Any?>): List<Note> {
                    val id = columns.getValue("id")
                    val title = columns.getValue("title")
                    val text = columns.getValue("text")
                    val date = columns.getValue("date")

                    val note = Note(id.toString().toLong(), title.toString(), text.toString())
                    note.date = dateFormat.parse(date.toString())

                    notes.add(note)

                    return notes
                }
            })
        notes
    }
}