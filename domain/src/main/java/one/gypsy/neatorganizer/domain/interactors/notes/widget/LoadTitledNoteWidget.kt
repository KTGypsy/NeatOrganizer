package one.gypsy.neatorganizer.domain.interactors.notes.widget

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import one.gypsy.neatorganizer.domain.dto.notes.TitledNoteWidgetEntryDto
import one.gypsy.neatorganizer.domain.interactors.BaseUseCase
import one.gypsy.neatorganizer.domain.interactors.Either
import one.gypsy.neatorganizer.domain.interactors.Failure
import one.gypsy.neatorganizer.domain.interactors.notes.widget.LoadTitledNoteWidget.Params
import one.gypsy.neatorganizer.domain.repositories.notes.NoteWidgetsRepository

class LoadTitledNoteWidget(private val noteWidgetsRepository: NoteWidgetsRepository) :
    BaseUseCase<TitledNoteWidgetEntryDto, Params>() {

    override suspend fun run(params: Params): Either<Failure, TitledNoteWidgetEntryDto> {
        return try {
            withContext(Dispatchers.IO) {
                Either.Right(noteWidgetsRepository.getTitledNoteWidget(params.noteWidgetId))
            }
        } catch (exp: Exception) {
            Either.Left(
                LoadTitledNoteWidgetFailure(
                    exp
                )
            )
        }
    }

    data class Params(val noteWidgetId: Int)
    data class LoadTitledNoteWidgetFailure(val error: Exception) : Failure.FeatureFailure(error)
}
