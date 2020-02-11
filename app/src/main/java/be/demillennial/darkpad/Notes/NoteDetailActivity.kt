/*
 * Copyright (c) Fatih Babacan 2019.
 * DarkPad is created by Fatih Babacan and released under the GPL3 license.
 * Please refer to the GPL3 license for additional information.
 */

package be.demillennial.darkpad.Notes

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import be.demillennial.darkpad.Data.NotesDb
import be.demillennial.darkpad.R

class NoteDetailActivity() : AppCompatActivity() {

    private lateinit var  notesDb: NotesDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)
        notesDb = NotesDb(this)

        var noteTitle = findViewById<TextView>(R.id.detail_title)
        var noteText = findViewById<TextView>(R.id.detail_text)

        var note: Note? = intent.getParcelableExtra("note")
        noteTitle.text = note?.title
        noteText.text = note?.text
    }
}