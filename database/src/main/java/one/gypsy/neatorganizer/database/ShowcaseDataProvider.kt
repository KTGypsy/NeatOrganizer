package one.gypsy.neatorganizer.database

import android.content.ContentValues
import androidx.room.OnConflictStrategy
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import one.gypsy.neatorganizer.database.entity.tasks.SingleTaskGroupEntity
import one.gypsy.tutorial.TutorialTaskGroup

/**
 * A custom [RoomDatabase.Callback] used for initializing data in the Room database.
 * This class is called when the database is created, allowing for custom setup or
 * data insertion during the database creation process.
 *
 * In this implementation, a showcase task group is created when the database is first created.
 * The showcase task group is inserted with the name and creation time from the `TutorialTaskGroup`.
 */
class ShowcaseDataProvider : RoomDatabase.Callback() {

    /**
     * Called when the database is created. This method invokes the [createShowcaseTaskGroup] method
     * to insert a predefined showcase task group into the database.
     *
     * @param db The [SupportSQLiteDatabase] instance that represents the SQLite database.
     */
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        db.createShowcaseTaskGroup()
    }

    /**
     * Inserts a predefined showcase task group into the `single_task_group` table.
     * The task group is inserted with the name and creation time from the [TutorialTaskGroup].
     * The insertion is done using the `OnConflictStrategy.IGNORE` strategy, meaning if the record
     * already exists, it won't be replaced.
     *
     * @param db The [SupportSQLiteDatabase] instance to interact with the SQLite database.
     */
    private fun SupportSQLiteDatabase.createShowcaseTaskGroup() {
        val showcaseRecord = ContentValues().apply {
            put(SingleTaskGroupEntity.NAME_FIELD, TutorialTaskGroup.name)
            put(SingleTaskGroupEntity.CREATED_AT_FIELD, TutorialTaskGroup.creationTime)
        }
        insert(SingleTaskGroupEntity.TABLE_NAME, OnConflictStrategy.IGNORE, showcaseRecord)
    }
}

