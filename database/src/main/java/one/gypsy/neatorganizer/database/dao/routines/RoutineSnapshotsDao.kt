package one.gypsy.neatorganizer.database.dao.routines

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import one.gypsy.neatorganizer.database.dao.BaseDao
import one.gypsy.neatorganizer.database.entity.routines.reset.RoutineSnapshotEntity

/**
 * Data Access Object (DAO) for managing routine snapshots in the database.
 */
@Dao
interface RoutineSnapshotsDao : BaseDao<RoutineSnapshotEntity> {

    /**
     * Retrieves all routine snapshots from the database.
     *
     * @return A list of [RoutineSnapshotEntity] objects representing all stored routine snapshots.
     */
    @Transaction
    @Query("SELECT * FROM routine_snapshots")
    fun getAllRoutineSnapshots(): List<RoutineSnapshotEntity>

    /**
     * Retrieves the most recent routine snapshot entry, ordered by snapshot ID in descending order.
     *
     * @return The last [RoutineSnapshotEntity] entry or `null` if no snapshots exist.
     */
    @Query("SELECT * FROM routine_snapshots ORDER BY routineSnapshotId DESC LIMIT 1")
    fun getLastResetEntry(): RoutineSnapshotEntity?
}

