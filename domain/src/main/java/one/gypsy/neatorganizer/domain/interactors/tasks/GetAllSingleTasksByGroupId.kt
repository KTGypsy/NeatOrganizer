package one.gypsy.neatorganizer.domain.interactors.tasks

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import one.gypsy.neatorganizer.domain.dto.tasks.SingleTaskEntryDto
import one.gypsy.neatorganizer.domain.interactors.BaseUseCase
import one.gypsy.neatorganizer.domain.interactors.Either
import one.gypsy.neatorganizer.domain.interactors.Failure
import one.gypsy.neatorganizer.domain.interactors.tasks.GetAllSingleTasksByGroupId.Params
import one.gypsy.neatorganizer.domain.repositories.tasks.SingleTasksRepository

class GetAllSingleTasksByGroupId(private val dataSource: SingleTasksRepository) :
    BaseUseCase<List<SingleTaskEntryDto>, Params>() {

    override suspend fun run(params: Params): Either<Failure, List<SingleTaskEntryDto>> {
        return try {
            withContext(Dispatchers.IO) {
                Either.Right(dataSource.getAllSingleTasksByGroupId(params.taskGroupId))
            }
        } catch (exp: Exception) {
            Either.Left(
                GetAllSingleTasksByGroupIdFailure(
                    exp
                )
            )
        }
    }

    data class Params(val taskGroupId: Long)
    data class GetAllSingleTasksByGroupIdFailure(val error: Exception) : Failure.FeatureFailure()
}
