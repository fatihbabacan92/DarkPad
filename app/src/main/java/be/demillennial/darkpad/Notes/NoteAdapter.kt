/*
 * Copyright (c) Fatih Babacan 2019.
 * DarkPad is created by Fatih Babacan and released under the GPL3 license.
 * Please refer to the GPL3 license for additional information.
 */

package be.demillennial.darkpad.Notes

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.preference.PreferenceManager
import be.demillennial.darkpad.R


class NoteAdapter(val notes : ArrayList<Note>, val context: Context?) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    var onItemClick: ((Note) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var noteTitle: TextView? = null
        var noteText: TextView? = null

        init {
            this.noteTitle = itemView?.findViewById<TextView>(R.id.note_title)
            this.noteText = itemView?.findViewById<TextView>(R.id.note_text)

            itemView.setOnClickListener {
                onItemClick?.invoke(notes[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_list_note, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        if (notes[i].title.length > 40 && getLayout() == "Grid") {
            viewHolder.noteTitle?.text = notes[i].title.substring(0, 40) + "..."
        } else if (notes[i].title.length > 85 && getLayout() == "List") {
            viewHolder.noteTitle?.text = notes[i].title.substring(0, 85) + "..."
        }
        else {
            viewHolder.noteTitle?.text = notes[i].title
        }

        if (notes[i].text.length > 55 && getLayout() == "Grid") {
            viewHolder.noteText?.text = notes[i].text.substring(0, 55) + "..."
        } else if (notes[i].text.length > 150 && getLayout() == "List") {
            viewHolder.noteText?.text = notes[i].text.substring(0, 150) + "..."
        }
        else {
            viewHolder.noteText?.text = notes[i].text
        }
    }

    private fun getLayout(): String? {
        val pref = PreferenceManager.getDefaultSharedPreferences(context!!)

        return pref.getString("layout_list", "")
    }

    override fun getItemId(position: Int): Long {
        return getItemId(position)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

}