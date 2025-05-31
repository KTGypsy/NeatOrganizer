package one.gypsy.neatorganizer.database

import androidx.room.TypeConverter
import java.util.Date

/**
 * A utility class to handle the conversion between `Date` and `Long` for Room database.
 * This class provides methods to convert a `Long` timestamp to a `Date` object and vice versa.
 * The `@TypeConverter` annotations are used to mark these methods for Room to use during database operations.
 */
internal class Converters {

    /**
     * Converts a `Long` timestamp value to a `Date` object.
     *
     * @param value The timestamp as a `Long`, or null if the value is not provided.
     * @return A `Date` object representing the timestamp, or null if the input value is null.
     */
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let {
            Date(it)
        }
    }

    /**
     * Converts a `Date` object to a `Long` timestamp value.
     *
     * @param date The `Date` object to be converted, or null if no date is provided.
     * @return The timestamp as a `Long`, or null if the input date is null.
     */
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}

