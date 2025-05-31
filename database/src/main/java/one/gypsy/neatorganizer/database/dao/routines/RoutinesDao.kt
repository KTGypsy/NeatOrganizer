package one.gypsy.neatorganizer.database.dao.routines

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import one.gypsy.neatorganizer.database.dao.BaseDao
import one.gypsy.neatorganizer.database.entity.routines.RoutineEntity
import one.gypsy.neatorganizer.database.entity.routines.ScheduledRoutineWithTasks

/**
 * Data Access Object (DAO) for managing routines in the database.
 */
@Dao
interface RoutinesDao : BaseDao<RoutineEntity> {

    /**
     * Retrieves all scheduled routines along with their associated tasks as an observable LiveData object.
     * The results are ordered by the creation date in descending order.
     *
     * @return A [LiveData] list of [ScheduledRoutineWithTasks] containing all scheduled routines and their tasks.
     */
    @Transaction
    @Query("SELECT * FROM routines ORDER BY createdAt DESC")
    fun getAllScheduledRoutinesWithTasksObservable(): LiveData<List<ScheduledRoutineWithTasks>>

    /**
     * Retrieves all scheduled routines along with their associated tasks.
     *
     * @return A list of [ScheduledRoutineWithTasks] containing all scheduled routines and their tasks.
     */
    @Transaction
    @Query("SELECT * FROM routines")
    fun getAllScheduledRoutinesWithTasks(): List<ScheduledRoutineWithTasks>

    /**
     * Deletes a routine from the database based on the given routine ID.
     *
     * @param routineId The ID of the routine to be deleted.
     */
    @Query("DELETE FROM routines WHERE id = :routineId")
    fun deleteRoutineById(routineId: Long)

    /**
     * Retrieves all routines from the database.
     *
     * @return A list of [RoutineEntity] objects representing all stored routines.
     */
    @Query("SELECT * FROM routines")
    fun getAllRoutines(): List<RoutineEntity>
}
