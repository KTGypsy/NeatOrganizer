package one.gypsy.neatorganizer.domain.dto.tasks

import one.gypsy.neatorganizer.database.entity.tasks.SingleTaskGroupEntity

data class SingleTaskGroupWithTasks(
    val taskGroup: SingleTaskGroup,
    val tasks: List<SingleTaskEntry> = emptyList(),
)

fun SingleTaskGroupWithTasks.toSingleTaskGroupEntity() =
    SingleTaskGroupEntity(
        name = this.taskGroup.name,
        id = this.taskGroup.id,
        createdAt = this.taskGroup.createdAt
    )
