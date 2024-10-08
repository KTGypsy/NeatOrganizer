package one.gypsy.neatorganizer.domain.interactors.notes.widget

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import one.gypsy.neatorganizer.domain.dto.notes.NoteWidgetEntryDto
import one.gypsy.neatorganizer.domain.interactors.BaseUseCase
import one.gypsy.neatorganizer.domain.interactors.Either
import one.gypsy.neatorganizer.domain.interactors.Failure
import one.gypsy.neatorganizer.domain.interactors.notes.widget.UpdateWidgetNote.Params
import one.gypsy.neatorganizer.domain.repositories.notes.NoteWidgetsRepository

class UpdateWidgetNote(private val noteWidgetsRepository: NoteWidgetsRepository) :
    BaseUseCase<Unit, Params>() {

    override suspend fun run(params: Params): Either<Failure, Unit> {
        return try {
            withContext(Dispatchers.IO) {
                Either.Right(noteWidgetsRepository.updateNoteWidget(params.noteWidget))
            }
        } catch (exp: Exception) {
            Either.Left(
                UpdateNoteWidgetFailure(
                    exp
                )
            )
        }
    }

    data class Params(val noteWidget: NoteWidgetEntryDto)
    data class UpdateNoteWidgetFailure(val error: Exception) : Failure.FeatureFailure(error)
}
