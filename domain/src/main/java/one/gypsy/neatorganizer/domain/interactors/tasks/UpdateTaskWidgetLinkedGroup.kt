package one.gypsy.neatorganizer.domain.interactors.tasks

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import one.gypsy.neatorganizer.domain.interactors.BaseUseCase
import one.gypsy.neatorganizer.domain.interactors.Either
import one.gypsy.neatorganizer.domain.interactors.Failure
import one.gypsy.neatorganizer.domain.interactors.tasks.UpdateTaskWidgetLinkedGroup.Params
import one.gypsy.neatorganizer.domain.repositories.tasks.TaskWidgetsRepository

class UpdateTaskWidgetLinkedGroup(private val taskWidgetsRepository: TaskWidgetsRepository) :
    BaseUseCase<Unit, Params>() {

    override suspend fun run(params: Params): Either<Failure, Unit> {
        return try {
            withContext(Dispatchers.IO) {
                Either.Right(
                    taskWidgetsRepository.updateLinkedTaskGroup(
                        params.taskWidgetId,
                        params.taskGroupId
                    )
                )
            }
        } catch (exp: Exception) {
            Either.Left(
                UpdateTaskWidgetLinkedGroupFailure(
                    exp
                )
            )
        }
    }

    data class Params(val taskWidgetId: Int, val taskGroupId: Long)
    data class UpdateTaskWidgetLinkedGroupFailure(val error: Exception) :
        Failure.FeatureFailure(error)
}
