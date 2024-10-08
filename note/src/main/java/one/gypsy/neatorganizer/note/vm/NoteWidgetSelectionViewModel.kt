package one.gypsy.neatorganizer.note.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import one.gypsy.neatorganizer.domain.dto.notes.NoteEntryDto
import one.gypsy.neatorganizer.domain.dto.notes.NoteWidgetEntryDto
import one.gypsy.neatorganizer.domain.interactors.notes.GetAllNoteEntries
import one.gypsy.neatorganizer.domain.interactors.notes.widget.UpdateWidgetNote
import one.gypsy.neatorganizer.note.model.NoteEntryItem
import one.gypsy.neatorganizer.note.model.WidgetNoteItem
import one.gypsy.neatorganizer.note.model.toNoteEntryItem

internal class NoteWidgetSelectionViewModel(
    getAllNoteEntriesUseCase: GetAllNoteEntries,
    private val widgetId: Int,
    private val updateNoteWidgetUseCase: UpdateWidgetNote
) : ViewModel() {

    private val _selectedNote = MutableLiveData<WidgetNoteItem>()
    val selectedNote: LiveData<WidgetNoteItem> = _selectedNote

    private val _listedNotes: MediatorLiveData<List<NoteEntryItem>> = MediatorLiveData()
    val listedNotes: LiveData<List<NoteEntryItem>> = _listedNotes

    private val _widgetSelectionStatus = MutableLiveData<NoteWidgetSelectionStatus>()
    val widgetSelectionStatus: LiveData<NoteWidgetSelectionStatus> = _widgetSelectionStatus

    init {
        getAllNoteEntriesUseCase.invoke(viewModelScope, Unit) {
            it.either(onSuccess = ::onGetAllNoteEntriesSuccess)
        }
    }

    private fun onGetAllNoteEntriesSuccess(noteEntriesObservable: LiveData<List<NoteEntryDto>>) {
        _listedNotes.addSource(noteEntriesObservable) {
            viewModelScope.launch {
                _listedNotes.postValue(it.map { it.toNoteEntryItem() })
            }
        }
    }

    fun onItemSelected(note: WidgetNoteItem) {
        if (note != selectedNote.value) {
            _selectedNote.postValue(note)
        }
    }

    fun onSubmitClicked() = if (selectedNote.value == null) {
        _widgetSelectionStatus.postValue(
            NoteWidgetSelectionStatus.NoteNotSelectedStatus
        )
    } else {
        submitWidgetSelection()
    }

    private fun submitWidgetSelection() =
        (selectedNote.value as? WidgetNoteItem.EntryItem)?.let { noteEntry ->
            updateNoteWidgetUseCase.invoke(
                viewModelScope,
                UpdateWidgetNote.Params(
                    NoteWidgetEntryDto(
                        widgetId = widgetId,
                        noteId = noteEntry.id,
                        color = noteEntry.color
                    )
                )
            ) {
                it.either(
                    onSuccess = { _widgetSelectionStatus.postValue(NoteWidgetSelectionStatus.SelectionSuccessStatus) }
                )
            }
        }
}

sealed class NoteWidgetSelectionStatus {
    object NoteNotSelectedStatus : NoteWidgetSelectionStatus()
    object SelectionSuccessStatus : NoteWidgetSelectionStatus()
}
