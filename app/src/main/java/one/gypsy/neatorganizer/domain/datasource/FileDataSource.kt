package one.gypsy.neatorganizer.domain.datasource

import android.graphics.Bitmap
import android.net.Uri

interface FileDataSource  {
    suspend fun getImageBitmapFromUri(imageUri: Uri): Bitmap
}