package com.example.mynotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import com.example.mynotes.Model.Notes
import com.example.mynotes.NotesDB.NotesDBHandler

class EditActivity : AppCompatActivity() {

    var _id:Int = 0
    lateinit var toolbar:Toolbar
    lateinit var edtUpdateTitle:EditText
    lateinit var edtUpdateNote:EditText
    lateinit var btnUpdate:Button
    lateinit var notes:Notes
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        _id = intent.getIntExtra("_id",0)

        toolbar = findViewById(R.id.toolbar)
        edtUpdateTitle = findViewById(R.id.edtUpdateTitle)
        edtUpdateNote = findViewById(R.id.edtUpdateNote)
        btnUpdate = findViewById(R.id.btnUpdate)

        setSupportActionBar(toolbar)

        Log.d("EditActivity","This is id : $_id")

        var dbHandler = NotesDBHandler(this)
        notes= dbHandler.readSpecificNotes(_id)

        edtUpdateTitle.setText(notes.note_title)
        edtUpdateNote.setText(notes.note_detail)
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
        btnUpdate.setOnClickListener {
            if(notes.note_title == edtUpdateTitle.text.toString() &&
                    notes.note_detail == edtUpdateNote.text.toString()){
                finish()
            }else{
                var dbHandler = NotesDBHandler(this)
               var row = dbHandler.UpdateNotes(Notes(_id,
                    edtUpdateTitle.text.toString(),
                    edtUpdateNote.text.toString()))

                if(row>0){
                    Toast.makeText(this,"Notes Updated!!",Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
    }
}