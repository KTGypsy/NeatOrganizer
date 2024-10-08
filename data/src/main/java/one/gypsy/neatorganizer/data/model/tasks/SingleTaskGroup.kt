package one.gypsy.neatorganizer.data.model.tasks

import one.gypsy.neatorganizer.database.entity.tasks.SingleTaskGroupEntity

data class SingleTaskGroup(
    val name: String,
    var id: Long = 0,
    val createdAt: Long
)

internal fun SingleTaskGroup.toSingleTaskGroupEntity() = SingleTaskGroupEntity(
    name = this.name,
    id = this.id,
    createdAt = this.createdAt
)
