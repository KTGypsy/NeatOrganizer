package one.gypsy.neatorganizer.presentation.tasks.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import one.gypsy.neatorganizer.R
import one.gypsy.neatorganizer.binding.BindableAdapter
import one.gypsy.neatorganizer.presentation.tasks.model.TaskListItem

class GroupedTasksAdapter(
    val headerClickListener: TaskHeaderViewHolder.ClickListener,
    val subItemClickListener: TaskSubItemViewHolder.ClickListener
) : ListAdapter<TaskListItem, TaskViewHolder>(DiffCallback()), BindableAdapter<TaskListItem> {

    override fun bindData(dataCollection: List<TaskListItem>) {
        submitList(dataCollection)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder =
        TaskViewType.values().first { it.resId == viewType }
            .getHolder(parent, headerClickListener, subItemClickListener)

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).getViewHolderType()
    }

    class DiffCallback : DiffUtil.ItemCallback<TaskListItem>() {

        override fun areItemsTheSame(oldItem: TaskListItem, newItem: TaskListItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TaskListItem, newItem: TaskListItem): Boolean {
            return oldItem == newItem
        }
    }
}

enum class TaskViewType(@LayoutRes val resId: Int) {
    HEADER(R.layout.item_task_header),
    SUB_ITEM(R.layout.item_task)
}

fun TaskListItem.getViewHolderType(): Int = when (this) {
    is TaskListItem.TaskListHeader -> TaskViewType.HEADER.resId
    is TaskListItem.TaskListSubItem -> TaskViewType.SUB_ITEM.resId
}

fun TaskViewType.getHolder(
    parent: ViewGroup,
    headerClickListener: TaskHeaderViewHolder.ClickListener,
    subItemClickListener: TaskSubItemViewHolder.ClickListener
) = when (this) {
    TaskViewType.HEADER -> TaskHeaderViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), resId, parent, false
        ), headerClickListener
    )
    TaskViewType.SUB_ITEM -> TaskSubItemViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            resId,
            parent,
            false
        ),
        subItemClickListener
    )
}

