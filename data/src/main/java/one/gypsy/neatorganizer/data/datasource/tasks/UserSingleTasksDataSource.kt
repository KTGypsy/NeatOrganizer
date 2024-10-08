package one.gypsy.neatorganizer.data.datasource.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import one.gypsy.neatorganizer.data.model.tasks.SingleTaskEntry
import one.gypsy.neatorganizer.data.model.tasks.toSingleTaskEntity
import one.gypsy.neatorganizer.data.model.tasks.toSingleTaskEntry
import one.gypsy.neatorganizer.database.dao.tasks.SingleTasksDao

internal class UserSingleTasksDataSource(private val singleTasksDao: SingleTasksDao) :
    SingleTasksDataSource {
    override suspend fun add(singleTaskEntry: SingleTaskEntry) =
        singleTasksDao.insert(singleTaskEntry.toSingleTaskEntity())

    override suspend fun update(singleTaskEntry: SingleTaskEntry) =
        singleTasksDao.update(singleTaskEntry.toSingleTaskEntity())

    override suspend fun remove(singleTaskEntry: SingleTaskEntry) =
        singleTasksDao.delete(singleTaskEntry.toSingleTaskEntity())

    override suspend fun getAllSingleTasksByGroupIdObservable(groupId: Long): LiveData<List<SingleTaskEntry>> =
        Transformations.map(singleTasksDao.getAllSingleTasksByGroupIdObservable(groupId)) { tasks ->
            tasks.map { it.toSingleTaskEntry() }
        }

    override suspend fun getAllSingleTasksByGroupId(groupId: Long): List<SingleTaskEntry> =
        singleTasksDao.getAllSingleTasksByGroupId(groupId).map { it.toSingleTaskEntry() }
}
