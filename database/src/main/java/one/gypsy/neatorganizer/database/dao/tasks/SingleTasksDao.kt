package one.gypsy.neatorganizer.database.dao.tasks

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import one.gypsy.neatorganizer.database.dao.BaseDao
import one.gypsy.neatorganizer.database.entity.tasks.SingleTaskEntity

/**
 * Data Access Object (DAO) for managing single tasks in the database.
 */
@Dao
interface SingleTasksDao : BaseDao<SingleTaskEntity> {

    /**
     * Retrieves all single tasks associated with a given task group ID.
     *
     * @param taskGroupId The ID of the task group to fetch the single tasks for.
     * @return A list of [SingleTaskEntity] representing all tasks in the specified task group.
     */
    @Query("SELECT * FROM single_tasks WHERE groupId = :taskGroupId")
    fun getAllSingleTasksByGroupId(taskGroupId: Long): List<SingleTaskEntity>

    /**
     * Retrieves all single tasks associated with a given task group ID as an observable [LiveData] object.
     *
     * @param taskGroupId The ID of the task group to fetch the single tasks for.
     * @return A [LiveData] list of [SingleTaskEntity] representing all tasks in the specified task group.
     */
    @Query("SELECT * FROM single_tasks WHERE groupId = :taskGroupId")
    fun getAllSingleTasksByGroupIdObservable(taskGroupId: Long): LiveData<List<SingleTaskEntity>>
}
