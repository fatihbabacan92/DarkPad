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
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.demillennial.darkpad.Data.NotesDb
import be.demillennial.darkpad.R





class NotesFragment : Fragment() {

    private lateinit var noteAdapter: NoteAdapter
    private lateinit var  notesDb: NotesDb
    private lateinit var notes: ArrayList<Note>

    private lateinit var notes_list: RecyclerView
    private lateinit var empty_view: TextView
    private lateinit var fab: View

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

        notes_list = rootView.findViewById<RecyclerView>(R.id.notes_list)
        empty_view = rootView.findViewById<TextView>(R.id.empty_view)
        fab = rootView.findViewById<View>(R.id.fabAddNotes)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context: Context? = super.getContext()
        val noteContext: Context = requireActivity().applicationContext!!

        notesDb = NotesDb(noteContext)
        notes = notesDb.getAll()

        if (getLayout() == "Grid") {
            notes_list.layoutManager = GridLayoutManager(activity, 2)
        } else {
            notes_list.layoutManager = LinearLayoutManager(activity)
        }

        noteAdapter = NoteAdapter(notes, context)
        notes_list.adapter = noteAdapter

        if (notes.isEmpty()) {
            notes_list.visibility = View.INVISIBLE
            empty_view.visibility = View.VISIBLE
        } else {
            notes_list.visibility = View.VISIBLE
            empty_view.visibility = View.INVISIBLE
        }

        noteAdapter.onItemClick = { note ->
            val intent = Intent(context, NoteDetailActivity::class.java)
            intent.putExtra("openNote", note)
            startActivity(intent)
        }

        fab.setOnClickListener { f ->
            val intent = Intent(context, NoteDetailActivity::class.java)
            intent.putExtra("newNote", true)
            startActivity(intent)
            onPause()
        }
    }

    private fun loadNotes() {
        notes.clear()
        notes = notesDb.getAll()
        noteAdapter = NoteAdapter(notes, context)

        var notes_list = activity?.findViewById<RecyclerView>(R.id.notes_list)
        var empty_view = activity?.findViewById<TextView>(R.id.empty_view)

        if (getLayout() == "Grid") {
            notes_list?.layoutManager = GridLayoutManager(activity, 2)
        } else {
            notes_list?.layoutManager = LinearLayoutManager(activity)
        }

        notes_list?.adapter = noteAdapter

        if (notes.isEmpty()) {
            notes_list?.visibility = View.INVISIBLE
            empty_view?.visibility = View.VISIBLE
        } else {
            notes_list?.visibility = View.VISIBLE
            empty_view?.visibility = View.INVISIBLE
        }

        noteAdapter.onItemClick = { note ->
            val intent = Intent(context, NoteDetailActivity::class.java)
            intent.putExtra("openNote", note)
            startActivity(intent)
        }
        noteAdapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        loadNotes()
    }

    private fun getLayout(): String? {
        val pref = PreferenceManager.getDefaultSharedPreferences(activity)

        return pref.getString("layout_list", "")
    }
}