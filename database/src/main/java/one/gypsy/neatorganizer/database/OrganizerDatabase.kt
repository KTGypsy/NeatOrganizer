package one.gypsy.neatorganizer.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import one.gypsy.neatorganizer.database.dao.notes.NoteWidgetsDao
import one.gypsy.neatorganizer.database.dao.notes.NotesDao
import one.gypsy.neatorganizer.database.dao.routines.RoutineSchedulesDao
import one.gypsy.neatorganizer.database.dao.routines.RoutineSnapshotsDao
import one.gypsy.neatorganizer.database.dao.routines.RoutineTasksDao
import one.gypsy.neatorganizer.database.dao.routines.RoutinesDao
import one.gypsy.neatorganizer.database.dao.tasks.SingleTaskGroupsDao
import one.gypsy.neatorganizer.database.dao.tasks.SingleTasksDao
import one.gypsy.neatorganizer.database.dao.tasks.TaskWidgetsDao
import one.gypsy.neatorganizer.database.entity.notes.NoteEntity
import one.gypsy.neatorganizer.database.entity.notes.NoteWidgetEntity
import one.gypsy.neatorganizer.database.entity.routines.RoutineEntity
import one.gypsy.neatorganizer.database.entity.routines.RoutineScheduleEntity
import one.gypsy.neatorganizer.database.entity.routines.RoutineTaskEntity
import one.gypsy.neatorganizer.database.entity.routines.reset.RoutineSnapshotEntity
import one.gypsy.neatorganizer.database.entity.tasks.SingleTaskEntity
import one.gypsy.neatorganizer.database.entity.tasks.SingleTaskGroupEntity
import one.gypsy.neatorganizer.database.entity.tasks.TaskWidgetEntity

/**
 * Represents the Room database for the NeatOrganizer application.
 * This class defines the database structure, including the entities and DAOs.
 * It is annotated with `@Database` to define the version and entities of the database.
 * Additionally, it uses the `@TypeConverters` annotation to specify custom converters
 * for types that are not supported by Room (e.g., `Date`).
 *
 * @property routineSnapshotsDao DAO for accessing the `RoutineSnapshotEntity` table.
 * @property singleTaskGroupsDao DAO for accessing the `SingleTaskGroupEntity` table.
 * @property singleTasksDao DAO for accessing the `SingleTaskEntity` table.
 * @property routinesDao DAO for accessing the `RoutineEntity` table.
 * @property routinesSchedulesDao DAO for accessing the `RoutineScheduleEntity` table.
 * @property routineTasksDao DAO for accessing the `RoutineTaskEntity` table.
 * @property taskWidgetDao DAO for accessing the `TaskWidgetEntity` table.
 * @property notesDao DAO for accessing the `NoteEntity` table.
 * @property noteWidgetDao DAO for accessing the `NoteWidgetEntity` table.
 */
@Database(
    entities = [
        SingleTaskEntity::class,
        SingleTaskGroupEntity::class,
        RoutineEntity::class,
        RoutineScheduleEntity::class,
        RoutineTaskEntity::class,
        RoutineSnapshotEntity::class,
        TaskWidgetEntity::class,
        NoteEntity::class,
        NoteWidgetEntity::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
internal abstract class OrganizerDatabase : RoomDatabase() {

    /**
     * Provides access to the [RoutineSnapshotsDao] for performing database operations on the `routine_snapshots` table.
     */
    abstract fun routineSnapshotsDao(): RoutineSnapshotsDao

    /**
     * Provides access to the [SingleTaskGroupsDao] for performing database operations on the `single_task_group` table.
     */
    abstract fun singleTaskGroupsDao(): SingleTaskGroupsDao

    /**
     * Provides access to the [SingleTasksDao] for performing database operations on the `single_tasks` table.
     */
    abstract fun singleTasksDao(): SingleTasksDao

    /**
     * Provides access to the [RoutinesDao] for performing database operations on the `routines` table.
     */
    abstract fun routinesDao(): RoutinesDao

    /**
     * Provides access to the [RoutineSchedulesDao] for performing database operations on the `routine_schedules` table.
     */
    abstract fun routinesSchedulesDao(): RoutineSchedulesDao

    /**
     * Provides access to the [RoutineTasksDao] for performing database operations on the `routine_tasks` table.
     */
    abstract fun routineTasksDao(): RoutineTasksDao

    /**
     * Provides access to the [TaskWidgetsDao] for performing database operations on the `task_widgets` table.
     */
    abstract fun taskWidgetDao(): TaskWidgetsDao

    /**
     * Provides access to the [NotesDao] for performing database operations on the `notes` table.
     */
    abstract fun notesDao(): NotesDao

    /**
     * Provides access to the [NoteWidgetsDao] for performing database operations on the `note_widgets` table.
     */
    abstract fun noteWidgetDao(): NoteWidgetsDao
}

