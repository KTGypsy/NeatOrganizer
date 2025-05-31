package one.gypsy.neatorganizer.database.entity.notes

import one.gypsy.neatorganizer.database.DatabaseTest
import one.gypsy.neatorganizer.database.dao.notes.NotesDao
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

internal class NoteEntityTest : DatabaseTest() {

    private lateinit var notesDao: NotesDao

    @Before
    override fun setup() {
        super.setup()
        notesDao = database.notesDao()
    }

    @Test
    fun shouldInsertNote() {
        // given
        val noteId = 1L
        val note = NoteEntity(
            id = noteId,
            title = "test",
            content = "really short content",
            createdAt = System.currentTimeMillis(),
            color = 123123
        )
        notesDao.insert(note)

        // when
        val fetchedNotes = notesDao.getAllNotes()

        // then
        assertThat(fetchedNotes).containsOnly(note)
    }

    @Test
    fun shouldUpdateNote() {
        // given
        val noteId = 1L
        val note = NoteEntity(
            id = noteId,
            title = "test",
            content = "really short content",
            createdAt = System.currentTimeMillis(),
            color = 123123
        )
        notesDao.insert(note)
        val updatedNote = note.copy(title = "updated one", content = "updated story")

        // when
        notesDao.update(updatedNote)
        val fetchedNotes = notesDao.getAllNotes()

        // then
        assertThat(fetchedNotes).containsOnly(updatedNote)
    }

    @Test
    fun shouldRemoveNote() {
        // given
        val noteId = 1L
        val note = NoteEntity(
            id = noteId,
            title = "test",
            content = "really short content",
            createdAt = System.currentTimeMillis(),
            color = 123123
        )

        // when
        notesDao.insert(note)
        notesDao.delete(note)
        val fetchedNotes = notesDao.getAllNotes()

        // then
        assertThat(fetchedNotes).hasSize(0)
    }

    @Test
    fun shouldGetNoteById() {
        // given
        val noteId = 1L
        val note = NoteEntity(
            id = noteId,
            title = "test",
            content = "really short content",
            createdAt = System.currentTimeMillis(),
            color = 123123
        )
        notesDao.insert(note)

        // when
        val fetchedNote = notesDao.getNoteByIdObservable(noteId)

        // then
        fetchedNote.observeForever {
            assertThat(it).isEqualToComparingFieldByField(note)
        }
    }

    @Test
    fun shouldGetAllNotesObservable() {
        // given
        val notes = arrayOf(
            NoteEntity(
                id = 1,
                title = "test1",
                content = "really short content1",
                createdAt = System.currentTimeMillis(),
                color = 123123
            ),
            NoteEntity(
                id = 2,
                title = "test2",
                content = "really short content2",
                createdAt = System.currentTimeMillis(),
                color = 123123
            ),
            NoteEntity(
                id = 3,
                title = "test3",
                content = "really short content3",
                createdAt = System.currentTimeMillis(),
                color = 123123
            )
        )
        notesDao.insert(*notes)

        // when
        val fetchedNotesObservable = notesDao.getAllNotesObservable()

        // then
        fetchedNotesObservable.observeForever {
            assertThat(it).containsExactlyInAnyOrderElementsOf(notes.toList())
        }
    }

    @Test
    fun shouldGetAllNotes() {
        // given
        val notes = arrayOf(
            NoteEntity(
                id = 1,
                title = "test1",
                content = "really short content1",
                createdAt = System.currentTimeMillis(),
                color = 123122
            ),
            NoteEntity(
                id = 2,
                title = "test2",
                content = "really short content2",
                createdAt = System.currentTimeMillis(),
                color = 123111
            ),
            NoteEntity(
                id = 3,
                title = "test3",
                content = "really short content3",
                createdAt = System.currentTimeMillis(),
                color = 1266654
            )
        )
        notesDao.insert(*notes)

        // when
        val fetchedNotes = notesDao.getAllNotes()

        // then
        assertThat(fetchedNotes).containsExactlyInAnyOrderElementsOf(notes.toList())
    }
}
