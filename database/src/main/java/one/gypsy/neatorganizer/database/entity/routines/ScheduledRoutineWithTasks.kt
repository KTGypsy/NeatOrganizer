package one.gypsy.neatorganizer.database.entity.routines

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Represents a scheduled routine with its associated tasks and schedule.
 * This class is used to fetch a routine along with its tasks and schedule in a single query.
 *
 * @property routine The [RoutineEntity] object representing the routine.
 * @property tasks A list of [RoutineTaskEntity] objects representing the tasks associated with the routine.
 * @property schedule A nullable [RoutineScheduleEntity] representing the schedule for the routine,
 * or null if no schedule is associated.
 */
data class ScheduledRoutineWithTasks(
    @Embedded val routine: RoutineEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "routineId"
    ) val tasks: List<RoutineTaskEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "routineId"
    ) val schedule: RoutineScheduleEntity?
)

