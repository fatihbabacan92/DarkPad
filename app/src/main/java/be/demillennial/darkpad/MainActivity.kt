/*
 * Copyright (c) Fatih Babacan 2019.
 * DarkPad is created by Fatih Babacan and released under the GPL3 license.
 * Please refer to the GPL3 license for additional information.
 */

package be.demillennial.darkpad

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import be.demillennial.darkpad.Data.NotesDb
import be.demillennial.darkpad.Notes.Note
import be.demillennial.darkpad.Notes.NoteAdapter
import be.demillennial.darkpad.Notes.NotesFragment
import be.demillennial.darkpad.Settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.android.synthetic.main.item_list_note.*

class MainActivity : AppCompatActivity() {

    private var content: FrameLayout? = null
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_note -> {
                //TODO: Change Titles?
                //message.setText(R.string.title_alarm)
                val fragment = NotesFragment.Companion.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                //message.setText(R.string.title_settings)
                val fragment = SettingsFragment.Companion.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        content = findViewById(R.id.content)
        val navigation: BottomNavigationView = findViewById(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val fragment = NotesFragment.Companion.newInstance()
        addFragment(fragment)
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.content, fragment, fragment.javaClass.simpleName)
            .commit()
    }
}
