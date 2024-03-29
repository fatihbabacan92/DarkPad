/*
 * Copyright (c) Fatih Babacan 2019.
 * DarkPad is created by Fatih Babacan and released under the GPL3 license.
 * Please refer to the GPL3 license for additional information.
 */

package be.demillennial.darkpad.Notes

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import be.demillennial.darkpad.Data.NotesDb
import be.demillennial.darkpad.R
import kotlinx.android.synthetic.main.activity_note_detail.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onKey
import org.jetbrains.anko.sdk27.coroutines.textChangedListener
import org.w3c.dom.Text
import android.content.Intent

import android.content.DialogInterface

import be.demillennial.darkpad.MainActivity


class NoteDetailActivity() : AppCompatActivity() {

    private lateinit var notesDb: NotesDb
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

        if (newNote) {
            noteTitle.hint = "Title"
            noteText.hint = "Write your note here..."
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

        editListener()
    }

    override fun onBackPressed() {
        saveNote()
        super.onBackPressed()
    }

    override fun onPause() {
        saveNote()
        super.onPause()
    }

    override fun onResume() {
        isSave = false;
        super.onResume()
    }

    private fun editListener() {
        var editText = findViewById<EditText>(R.id.detail_text)

        editText.textChangedListener {
            saveNote()
        }
    }

    private fun saveNote() {
        if (!isSave && newNote) {
            if (noteTitle.text.isNotEmpty() || noteText.text.isNotEmpty()) {
                notesDb.create(createNote())
                newNote = false;
                isSave = true;
            }
        } else if (!isSave && !newNote && note != null) {
            notesDb.update(createNote())
        }
    }

    private fun createNote(): Note {
        val saveText = noteText.text.toString()
        val saveTitle = noteTitle.text.toString()

        var newCreateNote: Note

        if (!newNote && note != null) {
            newCreateNote = Note(note?.id!!, saveTitle, saveText)
        } else {
            newCreateNote = Note(0, saveTitle, saveText)
        }

        return newCreateNote
    }

    private fun saveNewNoteListener() {
        var saveButton = findViewById<Button>(R.id.buttonSave)
        saveButton.onClick {
            notesDb.create(createNote())
            isSave = true
            onBackPressed()
        }
    }

    private fun saveOldNoteListener() {
        var saveButton = findViewById<Button>(R.id.buttonSave)
        saveButton.onClick {
            notesDb.update(createNote())
            isSave = true
            onBackPressed()
        }
    }

    private fun deleteNewNoteListener() {
        val alertDialogBuilder = AlertDialog.Builder(this, R.style.AlertDialog)
        alertDialogBuilder.setTitle("Delete note")
        alertDialogBuilder.setMessage("Are you sure you want to delete this note?")
        alertDialogBuilder.setPositiveButton("Delete") { _: DialogInterface, _: Int ->
            isSave = true
            onBackPressed()
        }
        alertDialogBuilder.setNegativeButton("cancel") { _: DialogInterface, _: Int -> }
        val alertDialog = alertDialogBuilder.create()

        var deleteButton = findViewById<Button>(R.id.buttonDelete)
        buttonDelete.onClick {
            alertDialog.show()
        }
    }

    private fun deleteOldNoteListener() {
        val alertDialogBuilder = AlertDialog.Builder(this, R.style.AlertDialog)
        alertDialogBuilder.setTitle("Delete note")
        alertDialogBuilder.setMessage("Are you sure you want to delete this note?")
        alertDialogBuilder.setPositiveButton("Delete") { _: DialogInterface, _: Int ->
            notesDb.delete(note!!)
            isSave = true
            onBackPressed()
        }
        alertDialogBuilder.setNegativeButton("cancel") { _: DialogInterface, _: Int -> }
        val alertDialog = alertDialogBuilder.create()

        var deleteButton = findViewById<Button>(R.id.buttonDelete)
        buttonDelete.onClick {
            alertDialog.show()
        }
    }
}