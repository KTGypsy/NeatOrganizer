package one.gypsy.neatorganizer.database.dao.routines

import androidx.room.Dao
import androidx.room.Query
import one.gypsy.neatorganizer.database.dao.BaseDao
import one.gypsy.neatorganizer.database.entity.routines.RoutineScheduleEntity

/**
 * Data Access Object (DAO) for managing routine schedules in the database.
 */
@Dao
interface RoutineSchedulesDao : BaseDao<RoutineScheduleEntity> {

    /**
     * Retrieves all routine schedules from the database.
     *
     * @return A list of [RoutineScheduleEntity] objects representing all stored routine schedules.
     */
    @Query("SELECT * FROM routine_schedules")
    fun getAllRoutineSchedules(): List<RoutineScheduleEntity>
}