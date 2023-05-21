package org.d3if3139.notemify.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.d3if3139.notemify.R
import org.d3if3139.notemify.databinding.ListNoteBinding
import org.d3if3139.notemify.model.Note
import org.d3if3139.notemify.ui.DashboardFragment
import org.d3if3139.notemify.ui.DashboardFragmentDirections

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    class ViewHolder(
        val itemBinding: ListNoteBinding
    ) : RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback =
        object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id &&
                        oldItem.noteBody == newItem.noteBody &&
                        oldItem.noteTitle == newItem.noteTitle
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }

        }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListNoteBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNote = differ.currentList[position]

        holder.itemBinding.tvNoteTitle.text = currentNote.noteTitle
        holder.itemBinding.tvNoteBody.text = currentNote.noteBody

        holder.itemView.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_dashboardFragment_to_updateNoteFragment)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}