package com.mate.carpool.ui.util

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object FileUtils {
    @JvmStatic
    fun getImageMultipartBody(
        uri:Uri,
        context:Context
    ): MultipartBody.Part {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File.createTempFile("temp", ".jpg")
        file.deleteOnExit()
        inputStream?.copyTo(file.outputStream())
        inputStream?.close()
        return MultipartBody.Part.createFormData(
            name = "image",
            filename = file.name,
            body = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        )
    }
}