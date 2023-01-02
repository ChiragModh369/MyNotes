package com.example.mynotes.NotesDB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import com.example.mynotes.Model.Notes

class NotesDBHandler(context: Context) : SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION) {
    companion object
    {

        const val DB_NAME = "notes_db"
        const val DB_VERSION = 1
        const val TBL_NOTE = "notes_tbl"

        //NOTES COLUMNS
        const val NOTE_ID = "note_id"
        const val NOTE_TITLE = "note_title"
        const val NOTE_DETAIL = "note_detail"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        var CREATE_NOTE_TBL = "CREATE TABLE $TBL_NOTE(" +
                "$NOTE_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$NOTE_TITLE TEXT NOT NULL," +
                "$NOTE_DETAIL TEXT NOT NULL" +
                ")"

        p0!!.execSQL(CREATE_NOTE_TBL)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE if exists $TBL_NOTE")
        onCreate(p0)
    }

    fun insertNote(note: Notes):Long
    {
        var db = this.writableDatabase

        val cv =ContentValues()
        cv.put(NOTE_TITLE,note.note_title)
        cv.put(NOTE_DETAIL,note.note_detail)

        var row:Long = db.insert(TBL_NOTE,null,cv)
        return row
    }

    fun readNotes():ArrayList<Notes>{
        var db = this.readableDatabase

        var noteCursor = db.rawQuery("SELECT * FROM $TBL_NOTE",null)

        var noteList:ArrayList<Notes> = ArrayList()

        if (noteCursor.moveToLast()){
            do {
                noteList.add(Notes(noteCursor.getInt(0),
                        noteCursor.getString(1),
                        noteCursor.getString(2)))
            }while (noteCursor.moveToPrevious())
        }

        return noteList

    }

    fun deleteNotes(id:Int):Int{
        var db = this.writableDatabase

        var row = db.delete(TBL_NOTE,"$NOTE_ID=?", arrayOf<String>(id.toString()))

        return row
    }

    fun readSpecificNotes(id:Int):Notes{
        var db = this.readableDatabase

        var noteCursor = db.rawQuery("SELECT * FROM $TBL_NOTE WHERE $NOTE_ID = $id",null)

        noteCursor.moveToFirst()

        var notes:Notes = Notes(noteCursor.getInt(0),
                                noteCursor.getString(1),
                                noteCursor.getString(2))

        return notes
    }

    fun UpdateNotes(notes:Notes):Int{
        var db = this.writableDatabase
        var cv = ContentValues()

        cv.put(NOTE_TITLE,notes.note_title)
        cv.put(NOTE_DETAIL,notes.note_detail)

        var rows = db.update(TBL_NOTE,cv,"$NOTE_ID = ?", arrayOf<String>(notes.note_id.toString()))

        return rows
    }
}