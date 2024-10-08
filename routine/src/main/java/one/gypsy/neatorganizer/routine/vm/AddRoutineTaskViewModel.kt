package one.gypsy.neatorganizer.routine.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import one.gypsy.neatorganizer.domain.dto.routines.RoutineTaskEntryDto
import one.gypsy.neatorganizer.domain.interactors.routines.AddRoutineTask

internal class AddRoutineTaskViewModel(
    private val addRoutineTask: AddRoutineTask,
    private val routineId: Long
) : ViewModel() {

    val taskTitle = MutableLiveData<String>()

    private val _finishedAdding = MutableLiveData<Boolean>()
    val finishedAdding: LiveData<Boolean> = _finishedAdding

    fun addRoutineTask() = add { _finishedAdding.postValue(true) }

    fun addNextRoutineTask() = add { taskTitle.postValue("") }

    private fun add(
        onSuccess: (Unit) -> Any
    ) {
        addRoutineTask.invoke(
            viewModelScope,
            AddRoutineTask.Params(
                RoutineTaskEntryDto(
                    routineId = routineId,
                    name = taskTitle.value.orEmpty(),
                    done = false,
                    createdAt = System.currentTimeMillis()
                )
            )
        ) {
            it.either(onSuccess = onSuccess)
        }
    }
}
