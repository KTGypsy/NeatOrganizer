package one.gypsy.neatorganizer.database.dao.notes

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import one.gypsy.neatorganizer.database.dao.BaseDao
import one.gypsy.neatorganizer.database.entity.notes.NoteEntity

@Dao
interface NotesDao : BaseDao<NoteEntity> {

    @Query("SELECT * FROM notes")
    fun getAllNotesObservable(): LiveData<List<NoteEntity>>

    @Query("SELECT * FROM notes")
    fun getAllNotes(): List<NoteEntity>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNoteByIdObservable(noteId: Long): LiveData<NoteEntity>

    // TODO unit test
    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNoteById(noteId: Long): NoteEntity

    @Query("DELETE FROM notes WHERE id = :noteId")
    fun deleteNoteById(noteId: Long)
}
