package one.gypsy.neatorganizer.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

/**
 * Base Data Access Object (DAO) interface for generic database operations.
 * This interface provides common methods for inserting, updating, and deleting entities.
 */
interface BaseDao<T> {

    /**
     * Inserts an object into the database, replacing any existing entry with the same primary key.
     *
     * @param obj The object to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: T)

    /**
     * Inserts an object into the database and returns the generated ID of the inserted row.
     * Replaces any existing entry with the same primary key.
     *
     * @param obj The object to insert.
     * @return The generated ID of the inserted object.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndGetId(obj: T): Long

    /**
     * Inserts multiple objects into the database, replacing any existing entries with the same primary keys.
     *
     * @param obj The objects to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg obj: T)

    /**
     * Updates an existing object in the database.
     *
     * @param obj The object to update.
     */
    @Update
    fun update(obj: T)

    /**
     * Deletes an object from the database.
     *
     * @param obj The object to delete.
     */
    @Delete
    fun delete(obj: T)
}
