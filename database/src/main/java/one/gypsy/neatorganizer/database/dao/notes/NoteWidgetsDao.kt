package one.gypsy.neatorganizer.database.dao.notes

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import one.gypsy.neatorganizer.database.dao.BaseDao
import one.gypsy.neatorganizer.database.entity.notes.NoteWidgetEntity
import one.gypsy.neatorganizer.database.entity.notes.WidgetAndNote

    /**
     * Data Access Object (DAO) for managing note widgets in the database.
     */
    @Dao
    interface NoteWidgetsDao : BaseDao<NoteWidgetEntity> {

        /**
         * Retrieves a widget and its associated note by the widget ID.
         * @param noteWidgetId The ID of the widget to retrieve.
         * @return A WidgetAndNote object containing the widget and its associated note.
         */
        @Query("SELECT * FROM note_widgets WHERE widgetId = :noteWidgetId")
        fun getWidgetWithNoteById(noteWidgetId: Int): WidgetAndNote

        /**
         * Deletes a widget by its ID.
         * @param noteWidgetId The ID of the widget to delete.
         */
        @Query("DELETE FROM note_widgets WHERE widgetId = :noteWidgetId")
        fun deleteWidgetById(noteWidgetId: Int)

        /**
         * Updates the linked note ID for a specific widget.
         * @param noteWidgetId The ID of the widget to update.
         * @param noteId The new note ID to link to the widget.
         */
        @Query("UPDATE note_widgets SET noteId = :noteId WHERE widgetId = :noteWidgetId")
        fun updateLinkedTaskGroupById(noteWidgetId: Int, noteId: Long)

        /**
         * Retrieves an array of all widget IDs in the database.
         * @return An array of widget IDs.
         */
        @Query("SELECT widgetId FROM note_widgets")
        fun getAllWidgetIds(): IntArray

        /**
         * Retrieves all note widgets as an observable LiveData list.
         * @return LiveData containing a list of all NoteWidgetEntity objects.
         */
        @Query("SELECT * FROM note_widgets")
        fun getAllNoteWidgetsObservable(): LiveData<List<NoteWidgetEntity>>
    }
