package com.example.mynotes

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.R.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import com.example.mynotes.Model.Notes
import com.example.mynotes.NotesDB.NotesDBHandler


class MainActivity2 : AppCompatActivity() {

    lateinit var btnAdd: AppCompatButton
    lateinit var edtTitle: EditText
    lateinit var edtNote: EditText
    lateinit var toolbar_action:Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        btnAdd = findViewById(R.id.btnAdd)
        edtTitle = findViewById(R.id.edtTitle)
        edtNote = findViewById(R.id.edtNote)
        toolbar_action = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar_action)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()

        btnAdd.setOnClickListener {
            AddNotesToDB()
        }


    }

    private fun AddNotesToDB() {


        var Title: String = edtTitle.text.toString()
        var Note: String = edtNote.text.toString()

        if (Title.isNullOrEmpty() && Note.isNullOrEmpty()) {
            if (Title.isNullOrEmpty()) {
                edtTitle.error = "Title is Required"
            }
            if (Note.isNullOrEmpty()) {
                edtNote.error = "Note is Required"
            }
        } else {
            var db = NotesDBHandler(this)
            var row = db.insertNote(Notes(null, Title, Note))

            if (row <= -1) {
                Toast.makeText(this, "Notes Cannot be Added!!", Toast.LENGTH_LONG).show()
                Log.d("MainActivity2", "Notes Cannot be added!! and rows = $row")
            } else {
                Toast.makeText(this, "Notes Added Successfully!!", Toast.LENGTH_LONG).show()
                Log.d("MainActivity2", "Notes Added Successfully!!")
                finish()
            }
        }
    }


}