package one.gypsy.neatorganizer.data.datasource.routines

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import one.gypsy.neatorganizer.data.model.routines.RoutineWithTasks
import one.gypsy.neatorganizer.data.model.routines.toRoutineEntity
import one.gypsy.neatorganizer.data.model.routines.toRoutineWithTasks
import one.gypsy.neatorganizer.database.dao.routines.RoutinesDao

internal class UserRoutinesDataSource(private val routinesDao: RoutinesDao) :
    RoutinesDataSource {

    override suspend fun add(routine: RoutineWithTasks) =
        routinesDao.insertAndGetId(routine.toRoutineEntity())

    override suspend fun remove(routine: RoutineWithTasks) =
        routinesDao.delete(routine.toRoutineEntity())

    override suspend fun update(routine: RoutineWithTasks) =
        routinesDao.update(routine.toRoutineEntity())

    override suspend fun getAllRoutinesObservable(): LiveData<List<RoutineWithTasks>> =
        Transformations.map(routinesDao.getAllScheduledRoutinesWithTasksObservable()) { scheduledRoutinesWithTasks ->
            scheduledRoutinesWithTasks.map {
                it.toRoutineWithTasks()
            }
        }

    override suspend fun getAllRoutines(): List<RoutineWithTasks> =
        routinesDao.getAllScheduledRoutinesWithTasks().map { it.toRoutineWithTasks() }

    override suspend fun removeRoutineById(routineId: Long) =
        routinesDao.deleteRoutineById(routineId)
}
