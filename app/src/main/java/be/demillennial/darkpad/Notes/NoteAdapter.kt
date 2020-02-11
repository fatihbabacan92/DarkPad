/*
 * Copyright (c) Fatih Babacan 2019.
 * DarkPad is created by Fatih Babacan and released under the GPL3 license.
 * Please refer to the GPL3 license for additional information.
 */

package be.demillennial.darkpad.Notes

import android.app.Activity
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import be.demillennial.darkpad.R
import kotlinx.android.synthetic.main.item_list_note.view.*

class NoteAdapter(val notes : ArrayList<Note>, val context: Context?) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var noteTitle: TextView? = null
        var noteText: TextView? = null

        init {
            this.noteTitle = itemView?.findViewById<TextView>(R.id.note_title)
            this.noteText = itemView?.findViewById<TextView>(R.id.note_title)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_list_note, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.noteTitle?.text = notes[i].title
        viewHolder.noteText?.text = notes[i].text

    }

    override fun getItemId(position: Int): Long {
        return getItemId(position)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

}