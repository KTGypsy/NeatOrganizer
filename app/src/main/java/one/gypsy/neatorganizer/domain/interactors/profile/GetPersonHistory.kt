package one.gypsy.neatorganizer.domain.interactors.profile

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import one.gypsy.neatorganizer.data.repositories.people.PeopleRepository
import one.gypsy.neatorganizer.domain.dto.people.Person
import one.gypsy.neatorganizer.domain.dto.people.PersonProfile
import one.gypsy.neatorganizer.utils.BaseUseCase
import one.gypsy.neatorganizer.utils.Either
import one.gypsy.neatorganizer.utils.Failure
import java.util.*

class GetPersonHistory(var peopleRepository: PeopleRepository) :
    BaseUseCase<PersonProfile, GetPersonHistory.Params>() {

    override suspend fun run(params: Params): Either<Failure, PersonProfile> {
        return try {
            withContext(Dispatchers.IO) {
//                Either.Right(peopleRepository.getPersonHsitory(params.personId))
                //TODO create proper usecase with separate repository, also create new Dao and Entity with one to one relation(single person has its own interaction history)
                Either.Right(
                    PersonProfile(
                        "",
                        Person.Sex.MALE,
                        null,
                        0,
                        Date(),
                        listOf()
                    )
                )
            }
        } catch (exp: Exception) {
            Either.Left(
                GetPersonHistoryFailure(
                    exp
                )
            )
        }
    }

    data class Params(val personId: Long)
    data class GetPersonHistoryFailure(val error: Exception): Failure.FeatureFailure(error)
}