package one.gypsy.neatorganizer.data.datasource.tasks

import androidx.lifecycle.LiveData
import one.gypsy.neatorganizer.data.model.tasks.SingleTaskGroup
import one.gypsy.neatorganizer.data.model.tasks.SingleTaskGroupEntry
import one.gypsy.neatorganizer.data.model.tasks.SingleTaskGroupWithTasks

interface SingleTaskGroupsDataSource {
    suspend fun addSingleTaskGroupWithTasks(singleTaskGroupWithTasks: SingleTaskGroupWithTasks)
    suspend fun addSingleTaskGroup(singleTaskGroup: SingleTaskGroup)
    suspend fun removeSingleTaskGroupWithTasks(singleTaskGroupWithTasks: SingleTaskGroupWithTasks)
    suspend fun removeById(taskGroupId: Long)
    suspend fun updateSingleTaskGroupWithTasks(singleTaskGroupWithTasks: SingleTaskGroupWithTasks)
    suspend fun updateSingleTaskGroup(singleTaskGroup: SingleTaskGroup)
    suspend fun getAllSingleTaskGroupsWithTasks(): LiveData<List<SingleTaskGroupWithTasks>>
    suspend fun getSingleTaskGroupWithTasksById(taskGroupId: Long): LiveData<SingleTaskGroupWithTasks>
    suspend fun getSingleTaskGroupById(taskGroupId: Long): LiveData<SingleTaskGroup>
    suspend fun getAllSingleTaskGroupEntries(): LiveData<List<SingleTaskGroupEntry>>
}
