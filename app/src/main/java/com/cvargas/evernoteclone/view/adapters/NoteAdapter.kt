package com.cvargas.evernoteclone.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cvargas.evernoteclone.R
import com.cvargas.evernoteclone.data.model.Note
import com.cvargas.evernoteclone.databinding.ListItemNoteBinding

class NoteAdapter(
    private val notes: List<Note>,
    private val onClickListener: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteView =
        NoteView(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_note,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: NoteView, position: Int) {
        val note = notes[position]
        holder.binding.note = note
        holder.binding.root.setOnClickListener {
            onClickListener.invoke(note)
        }
    }

    override fun getItemCount(): Int = notes.size

    inner class NoteView constructor(val binding: ListItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

}
