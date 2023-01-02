package com.example.mynotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.Adapter.NotesAdapter
import com.example.mynotes.Model.Notes
import com.example.mynotes.NotesDB.NotesDBHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var fab:FloatingActionButton
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        fab = findViewById(R.id.fab)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        var notesList: ArrayList<Notes>

        var dbHandler = NotesDBHandler(this)
        notesList = dbHandler.readNotes()


        recyclerView.adapter = NotesAdapter(notesList,this)

        viewNotes()
    }

    override fun onStart() {
        super.onStart()

        fab.setOnClickListener {
            var intent = Intent(this,MainActivity2::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        viewNotes()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId)
        {
            R.id.menu_profile ->
            {
                Toast.makeText(this,"You Clicked on Profile Menu",Toast.LENGTH_LONG).show()
                Log.d("MainActivity","You Cicked on Profile Menu")
            }
            R.id.menu_search ->
            {
                Toast.makeText(this,"You Clicked on Search Menu",Toast.LENGTH_LONG).show()
                Log.d("MainActivity","You Cicked on Search Menu")
            }
            R.id.menu_setting ->
            {
                Toast.makeText(this,"You Clicked on Setting Menu",Toast.LENGTH_LONG).show()
                Log.d("MainActivity","You Cicked on Setting Menu")
            }
            R.id.menu_share_app ->
            {
                Toast.makeText(this,"You Clicked on Share Menu",Toast.LENGTH_LONG).show()
                Log.d("MainActivity","You Cicked on Share Menu")
            }
        }

        return super.onOptionsItemSelected(item)

    }

    fun viewNotes(){
        var notesList = ArrayList<Notes>()

        var dbHandler = NotesDBHandler(this)
        notesList = dbHandler.readNotes()
        recyclerView.adapter = NotesAdapter(notesList,this)
    }
}