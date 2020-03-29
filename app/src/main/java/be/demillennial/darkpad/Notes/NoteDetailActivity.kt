/*
 * Copyright (c) Fatih Babacan 2019.
 * DarkPad is created by Fatih Babacan and released under the GPL3 license.
 * Please refer to the GPL3 license for additional information.
 */

package be.demillennial.darkpad.Notes

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import be.demillennial.darkpad.Data.NotesDb
import be.demillennial.darkpad.R
import kotlinx.android.synthetic.main.activity_note_detail.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.w3c.dom.Text

class NoteDetailActivity() : AppCompatActivity() {

    private lateinit var  notesDb: NotesDb
    private var note: Note? = null
    private lateinit var noteTitle: TextView
    private lateinit var noteText: TextView
    private var newNote: Boolean = false
    private var isSave = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        noteTitle = findViewById<TextView>(R.id.detail_title)
        noteText = findViewById<TextView>(R.id.detail_text)

        notesDb = NotesDb(this)

        note = intent.getParcelableExtra("openNote")
        newNote = intent.getBooleanExtra("newNote", false)

        if (newNote)
        {
            noteTitle.setHint("Title")
            noteText.setHint("Write your note here...")
            noteTitle.text = ""
            noteText.text = ""
            deleteNewNoteListener()
            saveNewNoteListener()
        } else {
            noteTitle.text = note?.title
            noteText.text = note?.text
            deleteOldNoteListener()
            saveOldNoteListener()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!isSave) {
            notesDb.create(createNote())
        }
    }

    private fun createNote(): Note {
        val saveText = noteText.text.toString()
        val saveTitle = noteTitle.text.toString()

        var newCreateNote: Note

        if (!newNote) {
            newCreateNote = Note(note?.id!!, saveTitle, saveText)
        } else {
            newCreateNote = Note(0, saveTitle, saveText)
        }

        return newCreateNote
    }

    private fun saveNewNoteListener() {
        var saveButton = findViewById<Button>(R.id.buttonSave)
        saveButton.onClick { s ->
            notesDb.create(createNote())
            isSave = true
            onBackPressed()
        }
    }

    private fun saveOldNoteListener() {
        var saveButton = findViewById<Button>(R.id.buttonSave)
        saveButton.onClick { s ->
            notesDb.update(createNote())
            isSave = true
            onBackPressed()
        }
    }

    private fun deleteNewNoteListener() {
        var deleteButton = findViewById<Button>(R.id.buttonDelete)
        buttonDelete.onClick { d ->
            isSave = true
            onBackPressed()
        }
    }

    private fun deleteOldNoteListener() {
        var deleteButton = findViewById<Button>(R.id.buttonDelete)
        buttonDelete.onClick { d ->
            notesDb.delete(note!!)
            isSave = true
            onBackPressed()
        }
    }
}