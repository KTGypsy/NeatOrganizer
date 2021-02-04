package one.gypsy.neatorganizer.domain.dto.routines

import one.gypsy.neatorganizer.database.entity.routines.RoutineScheduleEntity

data class RoutineSchedule(
    val routineId: Long = 0,
    val scheduledDays: List<Boolean> = List(7) {
        false
    }
) {
    companion object {
        val EMPTY = RoutineSchedule()
    }
}

fun RoutineSchedule.toRoutineScheduleEntity() =
    RoutineScheduleEntity(
        routineId = this.routineId,
        monday = this.scheduledDays[0],
        tuesday = this.scheduledDays[1],
        wednesday = this.scheduledDays[2],
        thursday = this.scheduledDays[3],
        friday = this.scheduledDays[4],
        saturday = this.scheduledDays[5],
        sunday = this.scheduledDays[6]
    )
