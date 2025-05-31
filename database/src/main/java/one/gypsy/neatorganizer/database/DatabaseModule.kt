package one.gypsy.neatorganizer.database

import androidx.room.Room
import org.koin.dsl.module

/**
 * Defines a Koin module for the database dependencies in the application.
 * This module provides a way to inject the Room database instance and DAOs (Data Access Objects)
 * for different entities in the application. It sets up the `OrganizerDatabase` and provides access
 * to various DAOs that interact with the database.
 */
val databaseModule = module {

    /**
     * Provides the singleton instance of [OrganizerDatabase].
     * The database is created using Room's [databaseBuilder] method.
     * A custom callback, [ShowcaseDataProvider], is added during the database initialization.
     */
    single {
        Room.databaseBuilder(
            get(),
            OrganizerDatabase::class.java,
            "NeatOrganizer.db"
        ).addCallback(ShowcaseDataProvider()).build()
    }

    /**
     * Provides the DAO for accessing the [RoutineTaskEntity] table.
     */
    factory { get<OrganizerDatabase>().routineTasksDao() }

    /**
     * Provides the DAO for accessing the [RoutineEntity] table.
     */
    factory { get<OrganizerDatabase>().routinesDao() }

    /**
     * Provides the DAO for accessing the [RoutineScheduleEntity] table.
     */
    factory { get<OrganizerDatabase>().routinesSchedulesDao() }

    /**
     * Provides the singleton instance of the [SingleTaskGroupEntity] DAO.
     */
    single { get<OrganizerDatabase>().singleTaskGroupsDao() }

    /**
     * Provides the singleton instance of the [SingleTaskEntity] DAO.
     */
    single { get<OrganizerDatabase>().singleTasksDao() }

    /**
     * Provides the DAO for accessing the [RoutineSnapshotEntity] table.
     */
    factory { get<OrganizerDatabase>().routineSnapshotsDao() }

    /**
     * Provides the DAO for accessing the [TaskWidgetEntity] table.
     */
    factory { get<OrganizerDatabase>().taskWidgetDao() }

    /**
     * Provides the DAO for accessing the [NoteEntity] table.
     */
    factory { get<OrganizerDatabase>().notesDao() }

    /**
     * Provides the DAO for accessing the [NoteWidgetEntity] table.
     */
    factory { get<OrganizerDatabase>().noteWidgetDao() }
}
