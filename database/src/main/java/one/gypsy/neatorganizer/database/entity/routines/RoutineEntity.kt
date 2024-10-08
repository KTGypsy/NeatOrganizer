package one.gypsy.neatorganizer.database.entity.routines

import androidx.room.Entity
import androidx.room.PrimaryKey
import one.gypsy.neatorganizer.database.entity.Timestamped

@Entity(tableName = "routines")
data class RoutineEntity(
    val name: String,
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    override val createdAt: Long
) : Timestamped
