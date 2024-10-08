package one.gypsy.neatorganizer.note.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import one.gypsy.neatorganizer.core.binding.BindableAdapter
import one.gypsy.neatorganizer.note.R
import one.gypsy.neatorganizer.note.model.NoteEntryItem

internal class NoteEntriesAdapter :
    ListAdapter<NoteEntryItem, NoteEntryViewHolder>(DiffCallback()),
    BindableAdapter<NoteEntryItem> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteEntryViewHolder =
        NoteEntryViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_note, parent, false
            )
        )

    override fun onBindViewHolder(holder: NoteEntryViewHolder, position: Int) =
        holder.bind(getItem(position))

    override fun bindData(dataCollection: List<NoteEntryItem>) =
        submitList(dataCollection)

    class DiffCallback : DiffUtil.ItemCallback<NoteEntryItem>() {

        override fun areItemsTheSame(oldItem: NoteEntryItem, newItem: NoteEntryItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteEntryItem, newItem: NoteEntryItem): Boolean {
            return oldItem == newItem
        }
    }
}
