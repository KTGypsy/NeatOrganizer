package one.gypsy.neatorganizer.interactors

import android.graphics.Bitmap
import android.net.Uri
import one.gypsy.neatorganizer.data.FileRepository
import one.gypsy.neatorganizer.utils.BaseUseCase
import one.gypsy.neatorganizer.utils.Either
import one.gypsy.neatorganizer.utils.Failure
import javax.inject.Inject

class GetImageBitmap @Inject constructor(var fileRepository: FileRepository): BaseUseCase<Bitmap, GetImageBitmap.Params>() {
    override suspend fun run(params: Params): Either<Failure, Bitmap> {
        return try {
            Either.Right(fileRepository.getImageBitmapFromUri(params.imageUri))
        } catch (exp: Exception) {
            Either.Left(GetImageBitmapFailure(exp))
        }
    }
data class Params(val imageUri: Uri)
    data class GetImageBitmapFailure(val error: Exception): Failure.FeatureFailure(error)

}