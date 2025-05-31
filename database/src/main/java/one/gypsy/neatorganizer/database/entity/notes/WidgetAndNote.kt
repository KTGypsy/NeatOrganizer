package one.gypsy.neatorganizer.database.entity.notes

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Represents a relationship between a note and its associated widget.
 * This class is used to fetch the note and the widget together in a single query.
 *
 * @property note The [NoteEntity] object representing the note associated with the widget.
 * @property widget The [NoteWidgetEntity] object representing the widget associated with the note.
 */
class WidgetAndNote(
    @Relation(
        parentColumn = "noteId",
        entityColumn = "id"
    )
    val note: NoteEntity,

    @Embedded
    val widget: NoteWidgetEntity
)
