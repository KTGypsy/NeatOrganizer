package one.gypsy.neatorganizer.data.datasource.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import one.gypsy.neatorganizer.data.model.notes.NoteWidgetEntry
import one.gypsy.neatorganizer.data.model.notes.toNoteWidgetEntity
import one.gypsy.neatorganizer.data.model.notes.toNoteWidgetEntry
import one.gypsy.neatorganizer.data.model.notes.toTitledNoteWidgetEntry
import one.gypsy.neatorganizer.database.dao.notes.NoteWidgetsDao

internal class UserNoteWidgetsDataSource(private val noteWidgetsDao: NoteWidgetsDao) :
    NoteWidgetsDataSource {

    override suspend fun insert(noteWidget: NoteWidgetEntry) =
        noteWidgetsDao.insert(noteWidget.toNoteWidgetEntity())

    override suspend fun deleteById(noteWidgetId: Int) =
        noteWidgetsDao.deleteWidgetById(noteWidgetId)

    override suspend fun getTitledNoteWidget(noteWidgetId: Int) =
        noteWidgetsDao.getWidgetWithNoteById(noteWidgetId).toTitledNoteWidgetEntry()

    override suspend fun update(noteWidget: NoteWidgetEntry) =
        noteWidgetsDao.update(noteWidget.toNoteWidgetEntity())

    override suspend fun updateWidgetNoteId(noteWidgetId: Int, noteId: Long) =
        noteWidgetsDao.updateLinkedTaskGroupById(noteWidgetId, noteId)

    override suspend fun getAllWidgetIds(): IntArray = noteWidgetsDao.getAllWidgetIds()

    override suspend fun getAllNoteWidgets(): LiveData<List<NoteWidgetEntry>> =
        Transformations.map(noteWidgetsDao.getAllNoteWidgetsObservable()) {
            it.map { entry -> entry.toNoteWidgetEntry() }
        }
}
