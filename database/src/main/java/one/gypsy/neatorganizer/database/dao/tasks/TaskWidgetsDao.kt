package one.gypsy.neatorganizer.database.dao.tasks

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import one.gypsy.neatorganizer.database.dao.BaseDao
import one.gypsy.neatorganizer.database.entity.tasks.TaskWidgetEntity
import one.gypsy.neatorganizer.database.entity.tasks.WidgetAndTaskGroup

/**
 * Data Access Object (DAO) for managing task widgets in the database.
 */
@Dao
interface TaskWidgetsDao : BaseDao<TaskWidgetEntity> {

    /**
     * Retrieves all task widgets from the database.
     *
     * @return A list of [TaskWidgetEntity] representing all stored task widgets.
     */
    @Query("SELECT * FROM task_widgets")
    fun getAllTaskWidgets(): List<TaskWidgetEntity>

    /**
     * Retrieves a task widget by its ID.
     *
     * @param taskWidgetId The ID of the task widget to retrieve.
     * @return The [TaskWidgetEntity] corresponding to the given widget ID.
     */
    @Query("SELECT * FROM task_widgets WHERE widgetId = :taskWidgetId")
    fun getWidgetById(taskWidgetId: Int): TaskWidgetEntity

    /**
     * Retrieves a task widget along with its associated task group by the widget ID.
     *
     * @param taskWidgetId The ID of the task widget to retrieve along with its task group.
     * @return A [WidgetAndTaskGroup] object containing the widget and its associated task group.
     */
    @Query("SELECT * FROM task_widgets WHERE widgetId = :taskWidgetId")
    fun getWidgetWithTaskGroupById(taskWidgetId: Int): WidgetAndTaskGroup

    /**
     * Retrieves a task widget along with its associated task group by the widget ID as an observable [LiveData] object.
     *
     * @param taskWidgetId The ID of the task widget to retrieve along with its task group.
     * @return A [LiveData] object containing a [WidgetAndTaskGroup] for the specified widget ID.
     */
    @Query("SELECT * FROM task_widgets WHERE widgetId = :taskWidgetId")
    fun getWidgetWithTaskGroupByIdObservable(taskWidgetId: Int): LiveData<WidgetAndTaskGroup>

    /**
     * Retrieves all widget IDs from the database.
     *
     * @return An array of integers containing all widget IDs.
     */
    @Query("SELECT widgetId FROM task_widgets")
    fun getAllWidgetIds(): IntArray

    /**
     * Deletes a task widget from the database based on the given widget ID.
     *
     * @param taskWidgetId The ID of the task widget to be deleted.
     */
    @Query("DELETE FROM task_widgets WHERE widgetId = :taskWidgetId")
    fun deleteWidgetById(taskWidgetId: Int)

    /**
     * Updates the linked task group for a task widget based on the widget ID.
     *
     * @param taskWidgetId The ID of the task widget to update.
     * @param taskGroupId The new task group ID to link to the widget.
     */
    @Query("UPDATE task_widgets SET taskGroupId = :taskGroupId WHERE widgetId = :taskWidgetId")
    fun updateLinkedTaskGroupById(taskWidgetId: Int, taskGroupId: Long)

    /**
     * Retrieves all task widgets from the database as an observable [LiveData] object.
     *
     * @return A [LiveData] list of [TaskWidgetEntity] representing all stored task widgets.
     */
    @Query("SELECT * FROM task_widgets")
    fun getAllTaskWidgetsObservable(): LiveData<List<TaskWidgetEntity>>

    /**
     * Retrieves the task group ID linked to a specific task widget by its widget ID.
     *
     * @param taskWidgetId The ID of the task widget to retrieve the task group ID for.
     * @return The task group ID associated with the specified widget ID.
     */
    @Query("SELECT taskGroupId FROM task_widgets WHERE widgetId = :taskWidgetId")
    fun getTaskGroupIdByWidgetId(taskWidgetId: Int): Long
}

