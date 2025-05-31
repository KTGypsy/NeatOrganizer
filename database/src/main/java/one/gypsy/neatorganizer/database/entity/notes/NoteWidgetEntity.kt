package one.gypsy.neatorganizer.database.entity.notes

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a note widget in the database.
 *
 * This class defines the structure of the `note_widgets` table,
 * storing widget information such as its ID, associated note ID, and color.
 *
 * @property widgetId The unique ID of the widget, used as the primary key.
 * @property noteId The ID of the associated note.
 * @property color The color associated with the widget.
 */
@Entity(tableName = "note_widgets")
data class NoteWidgetEntity(
    /**
     * The unique identifier for the widget.
     * This serves as the primary key in the database.
     */
    @PrimaryKey val widgetId: Int = 0,

    /**
     * The ID of the note that this widget is associated with.
     */
    val noteId: Long,

    /**
     * The color assigned to the widget, represented as an integer.
     */
    val color: Int
)