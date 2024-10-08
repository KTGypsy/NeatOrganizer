package one.gypsy.neatorganizer.task.model

import one.gypsy.neatorganizer.domain.dto.tasks.SingleTaskEntryDto
import one.gypsy.neatorganizer.domain.dto.tasks.SingleTaskGroupWithTasksDto

internal class TaskListMapper {

    fun mapTasksToListItems(
        tasksGroupWithTasks: List<SingleTaskGroupWithTasksDto>,
        oldList: List<TaskListItem>
    ) = mutableListOf<TaskListItem>().apply {
        val oldHeaders = oldList.filterIsInstance<TaskListItem.TaskListHeader>()
        tasksGroupWithTasks.forEach { taskGroup ->
            this.addAll(
                mapTaskGroupToTaskListItems(
                    taskGroup,
                    wasHeaderExpanded(oldHeaders, taskGroup)
                )
            )
        }
    }

    private fun wasHeaderExpanded(
        oldHeaders: List<TaskListItem.TaskListHeader>,
        taskGroupWithTasks: SingleTaskGroupWithTasksDto
    ) = oldHeaders.firstOrNull { it.id == taskGroupWithTasks.taskGroup.id }?.expanded ?: false

    private fun mapTaskGroupToTaskListItems(
        taskGroupWithTasks: SingleTaskGroupWithTasksDto,
        expandedHeader: Boolean = false
    ): List<TaskListItem> = mutableListOf<TaskListItem>().apply {
        with(taskGroupWithTasks.toTaskListHeader(expandedHeader)) {
            add(this)
            addAll(mapTasksToListSubItems(taskGroupWithTasks.tasks))
        }
    }

    private fun mapTasksToListSubItems(
        tasks: List<SingleTaskEntryDto>
    ) = List(tasks.size) {
        tasks[it].toTaskListSubItem()
    }.sortedByDescending { it.createdAt }

    fun getVisibleItems(items: List<TaskListItem>): List<TaskListItem> =
        items.partition { it is TaskListItem.TaskListHeader }
            .let { partedLists ->
                mutableListOf<TaskListItem>().apply {
                    partedLists.first.filterIsInstance<TaskListItem.TaskListHeader>()
                        .forEach { header ->
                            this.addAll(
                                getHeaderWithItemsIfExpanded(
                                    header, partedLists.second
                                )
                            )
                        }
                }
            }

    private fun getHeaderWithItemsIfExpanded(
        header: TaskListItem.TaskListHeader,
        subItems: List<TaskListItem>
    ) = mutableListOf<TaskListItem>().apply {
        add(header)
        if (header.expanded) {
            addAll(
                subItems.filter { shouldAddToGroup(it, header.id) }
            )
        }
    }

    private fun shouldAddToGroup(
        listItem: TaskListItem,
        headerId: Long
    ) = listItem is TaskListItem.TaskListSubItem && headerId == listItem.groupId

    fun updateExpansion(headerItemId: Long, oldList: List<TaskListItem>?) =
        oldList?.map { negateExpandedIfHeader(it, headerItemId) }

    private fun negateExpandedIfHeader(
        listedItem: TaskListItem,
        headerItemId: Long
    ) = if (listedItem is TaskListItem.TaskListHeader && listedItem.id == headerItemId) {
        listedItem.copy(expanded = !listedItem.expanded)
    } else {
        listedItem
    }
}
