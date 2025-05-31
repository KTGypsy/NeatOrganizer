package one.gypsy.neatorganizer.database.dao.routines

import androidx.room.Dao
import androidx.room.Query
import one.gypsy.neatorganizer.database.dao.BaseDao
import one.gypsy.neatorganizer.database.entity.routines.RoutineTaskEntity

/**
 * Data Access Object (DAO) for managing routine tasks in the database.
 */
@Dao
interface RoutineTasksDao : BaseDao<RoutineTaskEntity> {

    /**
     * Resets the progress of tasks scheduled for Monday by setting the `done` field to 0.
     */
    @Query("UPDATE routine_tasks SET done = 0 WHERE EXISTS(SELECT * FROM routine_schedules WHERE routine_schedules.routineId = routine_tasks.routineId AND routine_schedules.monday = 1)")
    fun resetMondayTasksProgress()

    /**
     * Resets the progress of tasks scheduled for Tuesday by setting the `done` field to 0.
     */
    @Query("UPDATE routine_tasks SET done = 0 WHERE EXISTS(SELECT * FROM routine_schedules WHERE routine_schedules.routineId = routine_tasks.routineId AND routine_schedules.tuesday = 1)")
    fun resetTuesdayTasksProgress()

    /**
     * Resets the progress of tasks scheduled for Wednesday by setting the `done` field to 0.
     */
    @Query("UPDATE routine_tasks SET done = 0 WHERE EXISTS(SELECT * FROM routine_schedules WHERE routine_schedules.routineId = routine_tasks.routineId AND routine_schedules.wednesday = 1)")
    fun resetWednesdayTasksProgress()

    /**
     * Resets the progress of tasks scheduled for Thursday by setting the `done` field to 0.
     */
    @Query("UPDATE routine_tasks SET done = 0 WHERE EXISTS(SELECT * FROM routine_schedules WHERE routine_schedules.routineId = routine_tasks.routineId AND routine_schedules.thursday = 1)")
    fun resetThursdayTasksProgress()

    /**
     * Resets the progress of tasks scheduled for Friday by setting the `done` field to 0.
     */
    @Query("UPDATE routine_tasks SET done = 0 WHERE EXISTS(SELECT * FROM routine_schedules WHERE routine_schedules.routineId = routine_tasks.routineId AND routine_schedules.friday = 1)")
    fun resetFridayTasksProgress()

    /**
     * Resets the progress of tasks scheduled for Saturday by setting the `done` field to 0.
     */
    @Query("UPDATE routine_tasks SET done = 0 WHERE EXISTS(SELECT * FROM routine_schedules WHERE routine_schedules.routineId = routine_tasks.routineId AND routine_schedules.saturday = 1)")
    fun resetSaturdayTasksProgress()

    /**
     * Resets the progress of tasks scheduled for Sunday by setting the `done` field to 0.
     */
    @Query("UPDATE routine_tasks SET done = 0 WHERE EXISTS(SELECT * FROM routine_schedules WHERE routine_schedules.routineId = routine_tasks.routineId AND routine_schedules.sunday = 1)")
    fun resetSundayTasksProgress()

    /**
     * Retrieves all routine tasks from the database.
     *
     * @return A list of [RoutineTaskEntity] objects representing all stored routine tasks.
     */
    @Query("SELECT * FROM routine_tasks")
    fun getAllRoutineTasks(): List<RoutineTaskEntity>
}

