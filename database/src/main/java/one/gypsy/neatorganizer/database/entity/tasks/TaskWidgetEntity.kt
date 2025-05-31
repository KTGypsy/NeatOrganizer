package one.gypsy.neatorganizer.database.entity.tasks

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a task widget entity in the "task_widgets" table of the database.
 * This data class holds the data for a widget associated with a task group, including its ID,
 * associated task group, and color settings.
 *
 * @property widgetId The unique identifier for the widget. This is the primary key for the entity.
 * @property taskGroupId The unique identifier for the task group this widget is associated with.
 * @property color The color associated with the widget, represented as an integer value.
 */
@Entity(tableName = "task_widgets")
data class TaskWidgetEntity(
    @PrimaryKey val widgetId: Int,
    val taskGroupId: Long,
    val color: Int
)
