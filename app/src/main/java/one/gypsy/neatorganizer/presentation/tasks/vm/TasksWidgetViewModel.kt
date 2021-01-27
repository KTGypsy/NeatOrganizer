package one.gypsy.neatorganizer.presentation.tasks.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import one.gypsy.neatorganizer.domain.dto.tasks.SingleTaskGroup
import one.gypsy.neatorganizer.domain.interactors.tasks.GetSingleTaskGroupById

class TasksWidgetViewModel(
    taskGroupId: Long,
    private val getSingleTaskGroupUseCase: GetSingleTaskGroupById,
) : ViewModel() {

    private val _taskGroup: MediatorLiveData<SingleTaskGroup> = MediatorLiveData()
    val taskGroup: LiveData<SingleTaskGroup> = _taskGroup

    private val _widgetDataLoaded = MutableLiveData<TaskWidgetDataLoadingStatus>()
    val widgetDataLoaded: LiveData<TaskWidgetDataLoadingStatus> = _widgetDataLoaded

    init {
        loadTaskGroupData(taskGroupId)
    }

    private fun onGetSingleTaskGroupSuccess(taskGroup: LiveData<SingleTaskGroup>) {
        _taskGroup.addSource(taskGroup) {
            _taskGroup.postValue(taskGroup.value)
        }
        _widgetDataLoaded.postValue(TaskWidgetDataLoadingStatus.LoadingSuccess)
    }

    fun loadTaskGroupData(taskGroupId: Long) = getSingleTaskGroupUseCase.invoke(
        viewModelScope,
        GetSingleTaskGroupById.Params(taskGroupId)
    ) {
        it.either(
            { _widgetDataLoaded.postValue(TaskWidgetDataLoadingStatus.LoadingError) },
            ::onGetSingleTaskGroupSuccess
        )
    }
}

sealed class TaskWidgetDataLoadingStatus {
    object LoadingError : TaskWidgetDataLoadingStatus()
    object LoadingSuccess : TaskWidgetDataLoadingStatus()
}
