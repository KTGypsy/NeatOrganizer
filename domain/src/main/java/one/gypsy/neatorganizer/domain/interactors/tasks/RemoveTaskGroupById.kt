package one.gypsy.neatorganizer.domain.interactors.tasks

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import one.gypsy.neatorganizer.domain.interactors.BaseUseCase
import one.gypsy.neatorganizer.domain.interactors.Either
import one.gypsy.neatorganizer.domain.interactors.Failure
import one.gypsy.neatorganizer.domain.repositories.tasks.SingleTaskGroupsRepository

class RemoveTaskGroupById(private val singleTaskGroupsRepository: SingleTaskGroupsRepository) :
    BaseUseCase<Unit, RemoveTaskGroupById.Params>() {

    override suspend fun run(params: Params): Either<Failure, Unit> {
        return try {
            withContext(Dispatchers.IO) {
                Either.Right(singleTaskGroupsRepository.removeSingleTaskGroupById(params.id))
            }
        } catch (exp: Exception) {
            Either.Left(
                RemoveRoutineFailure(
                    exp
                )
            )
        }
    }

    data class Params(val id: Long)
    data class RemoveRoutineFailure(val error: Exception) : Failure.FeatureFailure(error)
}
