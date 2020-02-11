/*
 * Copyright (c) Fatih Babacan 2019.
 * DarkPad is created by Fatih Babacan and released under the GPL3 license.
 * Please refer to the GPL3 license for additional information.
 */

package be.demillennial.darkpad.Notes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import be.demillennial.darkpad.Data.NotesDb
import be.demillennial.darkpad.R
import kotlinx.android.synthetic.main.fragment_notes.*

class NotesFragment : Fragment() {

    private lateinit var noteAdapter: NoteAdapter
    private lateinit var  notesDb: NotesDb

    companion object {
        fun newInstance(): NotesFragment {
            var notesFragment = NotesFragment()
            var args = Bundle()
            notesFragment.arguments = args
            return notesFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_notes, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context: Context? = super.getContext()
        val noteContext: Context = activity!!.applicationContext!!
        notesDb = NotesDb(noteContext)
        noteAdapter = NoteAdapter(notesDb.getAll(), context)
        notes_list.adapter = noteAdapter
        noteAdapter.onItemClick = { note ->
            val intent = Intent(context, NoteDetailActivity::class.java)
            intent.putExtra("note", note)
            startActivity(intent)
        }
    }
}