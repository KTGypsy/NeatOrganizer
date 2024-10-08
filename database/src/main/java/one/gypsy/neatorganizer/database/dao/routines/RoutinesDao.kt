package one.gypsy.neatorganizer.database.dao.routines

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import one.gypsy.neatorganizer.database.dao.BaseDao
import one.gypsy.neatorganizer.database.entity.routines.RoutineEntity
import one.gypsy.neatorganizer.database.entity.routines.ScheduledRoutineWithTasks

@Dao
interface RoutinesDao : BaseDao<RoutineEntity> {

    @Transaction
    @Query("SELECT * FROM routines ORDER BY createdAt DESC")
    fun getAllScheduledRoutinesWithTasksObservable(): LiveData<List<ScheduledRoutineWithTasks>>

    @Transaction
    @Query("SELECT * FROM routines")
    fun getAllScheduledRoutinesWithTasks(): List<ScheduledRoutineWithTasks>

    @Query("DELETE FROM ROUTINES WHERE id = :routineId")
    fun deleteRoutineById(routineId: Long)

    @Query("SELECT * FROM routines")
    fun getAllRoutines(): List<RoutineEntity>
}
