package one.gypsy.neatorganizer.database.entity.tasks

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Represents a widget and its associated task group.
 * This class is used to fetch a widget along with its associated task group in a single query.
 *
 * @property singleTaskGroup The [SingleTaskGroupEntity] object representing the task group associated with the widget.
 * @property widget The [TaskWidgetEntity] object representing the widget.
 */
class WidgetAndTaskGroup(
    @Relation(
        parentColumn = "taskGroupId",
        entityColumn = "id"
    )
    val singleTaskGroup: SingleTaskGroupEntity,

    @Embedded
    val widget: TaskWidgetEntity
)

