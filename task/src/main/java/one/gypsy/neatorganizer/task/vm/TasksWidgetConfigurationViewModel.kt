package one.gypsy.neatorganizer.task.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import one.gypsy.neatorganizer.domain.dto.tasks.SingleTaskGroupEntryDto
import one.gypsy.neatorganizer.domain.dto.tasks.TaskWidgetEntryDto
import one.gypsy.neatorganizer.domain.interactors.tasks.CreateTaskWidget
import one.gypsy.neatorganizer.domain.interactors.tasks.GetAllSingleTaskGroupEntries
import one.gypsy.neatorganizer.task.model.WidgetTaskGroupItem
import one.gypsy.neatorganizer.task.model.toTaskGroupEntryItem

internal class TasksWidgetConfigurationViewModel(
    getAllTaskGroupEntriesUseCase: GetAllSingleTaskGroupEntries,
    private val widgetCreationUseCase: CreateTaskWidget
) : ViewModel() {

    private val _listedTaskGroups = MediatorLiveData<List<WidgetTaskGroupItem>>()
    val listedTaskGroups: LiveData<List<WidgetTaskGroupItem>> = _listedTaskGroups

    private val _selectedTaskGroup = MutableLiveData<WidgetTaskGroupItem>()
    val selectedTaskGroup: LiveData<WidgetTaskGroupItem> = _selectedTaskGroup

    private val _widgetCreationStatus = MutableLiveData<TaskWidgetCreationStatus>()
    val widgetCreationStatus: LiveData<TaskWidgetCreationStatus> = _widgetCreationStatus

    private var pickedColor: Int? = null

    init {
        getAllTaskGroupEntriesUseCase.invoke(viewModelScope, Unit) {
            it.either(onSuccess = ::onGetAllSingleTaskGroupEntriesSuccess)
        }
    }

    private fun onGetAllSingleTaskGroupEntriesSuccess(taskGroupEntriesObservable: LiveData<List<SingleTaskGroupEntryDto>>) =
        _listedTaskGroups.addSource(taskGroupEntriesObservable) { taskGroupEntries ->
            _listedTaskGroups.postValue(
                taskGroupEntries.map { it.toTaskGroupEntryItem() }
                    .plus(WidgetTaskGroupItem.Footer)
            )
        }

    fun onItemSelected(selectedItem: WidgetTaskGroupItem) {
        if (selectedItem != selectedTaskGroup.value) {
            _selectedTaskGroup.postValue(selectedItem)
        }
    }

    fun onColorPicked(color: Int) {
        pickedColor = color
    }

    fun onSubmitClicked(widgetId: Int) = when {
        selectedTaskGroup.value == null -> _widgetCreationStatus.postValue(
            TaskWidgetCreationStatus.TaskNotSelectedStatus
        )
        pickedColor == null -> _widgetCreationStatus.postValue(TaskWidgetCreationStatus.ColorNotPickedStatus)
        else -> submitWidgetCreation(widgetId)
    }

    private fun submitWidgetCreation(widgetId: Int) =
        createTaskWidgetEntry(widgetId)?.let { widgetEntry ->
            widgetCreationUseCase.invoke(
                viewModelScope,
                CreateTaskWidget.Params(widgetEntry)
            ) {
                it.either(onSuccess = ::onCreateTaskWidgetSuccess)
            }
        }

    private fun createTaskWidgetEntry(widgetId: Int): TaskWidgetEntryDto? {
        val taskGroup = selectedTaskGroup.value
        val widgetColor = pickedColor
        return if (taskGroup != null && widgetColor != null) {
            TaskWidgetEntryDto(
                appWidgetId = widgetId,
                taskGroupId = taskGroup.id,
                widgetColor = widgetColor
            )
        } else null
    }

    private fun onCreateTaskWidgetSuccess(unit: Unit) =
        _widgetCreationStatus.postValue(TaskWidgetCreationStatus.CreationSuccessStatus)
}

internal sealed class TaskWidgetCreationStatus {
    object TaskNotSelectedStatus : TaskWidgetCreationStatus()
    object ColorNotPickedStatus : TaskWidgetCreationStatus()
    object CreationSuccessStatus : TaskWidgetCreationStatus()
}
