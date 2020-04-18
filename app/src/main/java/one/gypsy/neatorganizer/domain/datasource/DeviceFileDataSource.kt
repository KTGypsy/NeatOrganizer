package one.gypsy.neatorganizer.domain.datasource

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import one.gypsy.neatorganizer.domain.datasource.FileDataSource
import javax.inject.Inject

class DeviceFileDataSource @Inject constructor(var context: Context):
    FileDataSource {
    override suspend fun getImageBitmapFromUri(imageUri: Uri): Bitmap {
        val inputStream = context.contentResolver.openInputStream(imageUri)
        return BitmapFactory.decodeStream(inputStream)
    }
}