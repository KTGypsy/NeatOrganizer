package one.gypsy.neatorganizer.task.view

import com.guanaj.easyswipemenulibrary.SwipeMenuListener
import one.gypsy.neatorganizer.core.binding.setEditionEnabled
import one.gypsy.neatorganizer.core.listing.ListedSubItem
import one.gypsy.neatorganizer.core.listing.SubItemClickListener
import one.gypsy.neatorganizer.core.utils.extensions.show
import one.gypsy.neatorganizer.core.utils.extensions.shrink
import one.gypsy.neatorganizer.task.databinding.ItemTaskBinding
import one.gypsy.neatorganizer.task.model.TaskListItem

internal class TaskSubItemViewHolder(
    private val itemBinding: ItemTaskBinding,
    private val clickListener: SubItemClickListener<TaskListItem.TaskListSubItem>? = null
) : TaskViewHolder(itemBinding.root), ListedSubItem<TaskListItem.TaskListSubItem> {

    override lateinit var viewData: TaskListItem.TaskListSubItem

    override fun bind(data: TaskListItem) {
        require(data is TaskListItem.TaskListSubItem)
        viewData = data

        updateEditable()
        setUpSwipeMenuBehavior()
        setUpEditListener()
        setUpEditionSubmitListener()
        setUpRemoveListener()
        setUpDoneListener()

        itemBinding.apply {
            taskSubItem = viewData
            executePendingBindings()
        }
    }

    override fun setUpSwipeMenuBehavior() {
        itemBinding.swipeLayoutItemTaskRoot.setMenuSwipeListener(object :
                SwipeMenuListener {
                override fun onLeftMenuOpen() {
                    clearEditionStatus()
                }

                override fun onRightMenuOpen() {
                    clearEditionStatus()
                }
            })
    }

    override fun clearEditionStatus() {
        viewData = viewData.copy(edited = false)
        updateEditable()
    }

    override fun updateEditable() {
        changeEditionAttributes()

        if (viewData.edited) {
            onEditionStart()
        } else {
            onEditionFinish()
        }
    }

    private fun changeEditionAttributes() =
        setEditionEnabled(
            view = itemBinding.editTextItemTaskName,
            editionEnabled = viewData.edited,
            requestEdit = viewData.edited
        )

    private fun onEditionFinish() {
        itemBinding.buttonItemTaskSubmit.shrink()
        itemBinding.checkBoxItemTaskDone.show()
    }

    private fun onEditionStart() {
        itemBinding.buttonItemTaskSubmit.show()
        itemBinding.checkBoxItemTaskDone.shrink()
    }

    override fun setUpEditListener() {
        itemBinding.setEditClickListener {
            viewData = viewData.copy(edited = !viewData.edited)
            updateEditable()
            itemBinding.swipeLayoutItemTaskRoot.resetStatus()
        }
    }

    override fun setUpEditionSubmitListener() {
        itemBinding.setEditionSubmitClickListener {
            if (didItemNameChange()) {
                viewData = viewData.copy(
                    title = itemBinding.editTextItemTaskName.text.toString()
                )
                clickListener?.onEditionSubmitClick?.invoke(viewData)
            } else {
                clearEditionStatus()
            }
        }
    }

    private fun didItemNameChange() =
        viewData.title != itemBinding.editTextItemTaskName.text.toString()

    override fun setUpRemoveListener() {
        itemBinding.setRemoveClickListener {
            itemBinding.swipeLayoutItemTaskRoot.resetStatus()
            clickListener?.onRemoveClick?.invoke(viewData)
        }
    }

    override fun setUpDoneListener() {
        itemBinding.setDoneClickListener {
            viewData = viewData.copy(
                done = !viewData.done
            )
            clickListener?.onDoneClick?.invoke(viewData)
        }
    }
}
