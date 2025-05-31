package one.gypsy.neatorganizer.database.dao.tasks

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import one.gypsy.neatorganizer.database.dao.BaseDao
import one.gypsy.neatorganizer.database.entity.tasks.GroupWithSingleTasks
import one.gypsy.neatorganizer.database.entity.tasks.SingleTaskGroupEntity

/**
 * Data Access Object (DAO) for managing single task groups in the database.
 */
@Dao
interface SingleTaskGroupsDao : BaseDao<SingleTaskGroupEntity> {

    /**
     * Retrieves all single task groups along with their associated tasks, ordered by the creation date in descending order.
     * The result is returned as an observable [LiveData] object.
     *
     * @return A [LiveData] list of [GroupWithSingleTasks] containing all task groups and their associated tasks.
     */
    @Transaction
    @Query("SELECT * FROM single_task_group ORDER BY createdAt DESC")
    fun getAllGroupsWithSingleTasks(): LiveData<List<GroupWithSingleTasks>>

    /**
     * Retrieves all single task groups from the database as an observable [LiveData] object.
     *
     * @return A [LiveData] list of [SingleTaskGroupEntity] representing all stored task groups.
     */
    @Query("SELECT * FROM single_task_group")
    fun getAllSingleTaskGroups(): LiveData<List<SingleTaskGroupEntity>>

    /**
     * Retrieves a single task group by its ID as an observable [LiveData] object.
     *
     * @param taskGroupId The ID of the task group to retrieve.
     * @return A [LiveData] object containing the [SingleTaskGroupEntity] with the given ID.
     */
    @Query("SELECT * FROM single_task_group WHERE id = :taskGroupId")
    fun getSingleTaskGroupByIdObservable(taskGroupId: Long): LiveData<SingleTaskGroupEntity>

    /**
     * Retrieves a single task group by its ID.
     *
     * @param taskGroupId The ID of the task group to retrieve.
     * @return The [SingleTaskGroupEntity] with the given ID.
     */
    @Query("SELECT * FROM single_task_group WHERE id = :taskGroupId")
    fun getSingleTaskGroupById(taskGroupId: Long): SingleTaskGroupEntity

    /**
     * Retrieves a single task group along with its associated tasks by the group's ID as an observable [LiveData] object.
     *
     * @param taskGroupId The ID of the task group to retrieve along with its tasks.
     * @return A [LiveData] object containing the [GroupWithSingleTasks] for the given task group ID.
     */
    @Query("SELECT * FROM single_task_group WHERE id = :taskGroupId")
    fun getGroupWithSingleTasksById(taskGroupId: Long): LiveData<GroupWithSingleTasks>

    /**
     * Deletes a task group from the database based on the provided task group ID.
     *
     * @param taskGroupId The ID of the task group to be deleted.
     */
    @Query("DELETE FROM single_task_group WHERE id = :taskGroupId")
    fun deleteTaskGroupById(taskGroupId: Long)
}
