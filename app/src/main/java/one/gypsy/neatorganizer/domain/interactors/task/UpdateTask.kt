package one.gypsy.neatorganizer.domain.interactors.task

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import one.gypsy.neatorganizer.data.repositories.SingleTasksRepository
import one.gypsy.neatorganizer.domain.dto.SingleTaskEntry
import one.gypsy.neatorganizer.utils.BaseUseCase
import one.gypsy.neatorganizer.utils.Either
import one.gypsy.neatorganizer.utils.Failure
import javax.inject.Inject

class UpdateTask @Inject constructor(var singleTaskRepository: SingleTasksRepository): BaseUseCase<Unit, UpdateTask.Params>() {

    override suspend fun run(params: Params): Either<Failure, Unit> {
        return try {
            withContext(Dispatchers.IO) {
                Either.Right(singleTaskRepository.updateSingleTask(params.singleTask))
            }
        } catch(exp: Exception) {
            Either.Left(
                AddSingleTaskFailure(
                    exp
                )
            )
        }
    }

    data class Params(val singleTask: SingleTaskEntry)
    data class AddSingleTaskFailure(val error: Exception): Failure.FeatureFailure(error)
}