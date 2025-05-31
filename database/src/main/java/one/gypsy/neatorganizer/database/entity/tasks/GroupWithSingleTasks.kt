package one.gypsy.neatorganizer.database.entity.tasks

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Represents a task group with its associated single tasks.
 * This class is used to fetch a task group along with its tasks in a single query.
 *
 * @property group The [SingleTaskGroupEntity] object representing the task group.
 * @property tasks A list of [SingleTaskEntity] objects representing the single tasks associated with the task group.
 */
data class GroupWithSingleTasks(
    @Embedded val group: SingleTaskGroupEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "groupId"
    ) val tasks: List<SingleTaskEntity>
)
