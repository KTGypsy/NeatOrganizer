package one.gypsy.neatorganizer.database.entity.routines

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Represents a routine schedule entity in the "routine_schedules" table of the database.
 * This data class holds the schedule information for a routine, specifying the days of the week
 * the routine is set to occur on.
 *
 * @property monday Boolean flag indicating if the routine occurs on Monday.
 * @property tuesday Boolean flag indicating if the routine occurs on Tuesday.
 * @property wednesday Boolean flag indicating if the routine occurs on Wednesday.
 * @property thursday Boolean flag indicating if the routine occurs on Thursday.
 * @property friday Boolean flag indicating if the routine occurs on Friday.
 * @property saturday Boolean flag indicating if the routine occurs on Saturday.
 * @property sunday Boolean flag indicating if the routine occurs on Sunday.
 * @property routineId The unique identifier for the routine. This is a foreign key referencing the [RoutineEntity].
 * It is the primary key for this entity.
 *
 * The `routineId` serves as the foreign key linking this schedule to a specific routine in the `routines` table.
 */
@Entity(
    tableName = "routine_schedules",
    foreignKeys = [
        ForeignKey(
            entity = RoutineEntity::class,
            parentColumns = ["id"],
            childColumns = ["routineId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class RoutineScheduleEntity(
    val monday: Boolean,
    val tuesday: Boolean,
    val wednesday: Boolean,
    val thursday: Boolean,
    val friday: Boolean,
    val saturday: Boolean,
    val sunday: Boolean,

    @PrimaryKey val routineId: Long
)

