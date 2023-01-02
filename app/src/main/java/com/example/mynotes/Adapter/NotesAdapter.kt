package com.example.mynotes.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.EditActivity
import com.example.mynotes.Model.Notes
import com.example.mynotes.NotesDB.NotesDBHandler
import com.example.mynotes.R

class NotesAdapter(var notes:ArrayList<Notes>,var context:Context) : RecyclerView.Adapter<NotesAdapter.NotesHolder>() {
    class NotesHolder(view:View):RecyclerView.ViewHolder(view) {

        var txtTitle:TextView = view.findViewById(R.id.txtTitle)
        var txtDetail:TextView = view.findViewById(R.id.txtDescription)
        var imgDelete:ImageView = view.findViewById(R.id.imgDelete)
        var rootGroup:LinearLayout = view.findViewById(R.id.rootGroup)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.notes_item_layout,parent,false)
        return NotesHolder(view)
    }

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        holder.txtTitle.setText(notes[position].note_title.toString())
        holder.txtDetail.setText(notes[position].note_detail.toString())

        holder.imgDelete.setOnClickListener {
            var dbHandler = NotesDBHandler(context)

            var rows = dbHandler.deleteNotes(notes[position].note_id!!)
            if (rows > 0){
                Toast.makeText(context,"Note Deleted!!!",Toast.LENGTH_LONG).show()
                notes.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position,notes.size)
            }
            Log.d("NotesAdapter","Rows Deleted: $rows")
            Toast.makeText(context,"Rows Deleted:$rows",Toast.LENGTH_LONG).show()
        }

        holder.rootGroup.setOnClickListener {
            var intent = Intent(context,EditActivity::class.java)
            intent.putExtra("_id",notes[position].note_id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}