package one.gypsy.neatorganizer.database.dao.notes

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import one.gypsy.neatorganizer.database.dao.BaseDao
import one.gypsy.neatorganizer.database.entity.notes.NoteEntity

    /**
     * Data Access Object (DAO) for managing operations related to notes in the database.
     */
    @Dao
    interface NotesDao : BaseDao<NoteEntity> {

        /**
         * Retrieves all notes as an observable LiveData list.
         * @return LiveData containing a list of all NoteEntity objects.
         */
        @Query("SELECT * FROM notes")
        fun getAllNotesObservable(): LiveData<List<NoteEntity>>

        /**
         * Retrieves all notes as a list.
         * @return List of all NoteEntity objects in the database.
         */
        @Query("SELECT * FROM notes")
        fun getAllNotes(): List<NoteEntity>

        /**
         * Retrieves a specific note by its ID as an observable LiveData object.
         * @param noteId The ID of the note to retrieve.
         * @return LiveData containing the requested NoteEntity.
         */
        @Query("SELECT * FROM notes WHERE id = :noteId")
        fun getNoteByIdObservable(noteId: Long): LiveData<NoteEntity>

        /**
         * Retrieves a specific note by its ID.
         * @param noteId The ID of the note to retrieve.
         * @return The requested NoteEntity.
         */
        @Query("SELECT * FROM notes WHERE id = :noteId")
        fun getNoteById(noteId: Long): NoteEntity

        /**
         * Deletes a specific note by its ID.
         * @param noteId The ID of the note to delete.
         */
        @Query("DELETE FROM notes WHERE id = :noteId")
        fun deleteNoteById(noteId: Long)
    }


