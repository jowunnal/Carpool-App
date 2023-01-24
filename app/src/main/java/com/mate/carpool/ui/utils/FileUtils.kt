package com.mate.carpool.ui.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object FileUtils {
    @JvmStatic
    fun createTempFileFromInputStream(
        fileName:String,
        fileSuffix:String,
        inputStream: InputStream
    ): File? {
        val tempFile = File.createTempFile(fileName,fileSuffix)
        tempFile.deleteOnExit()
        createFileFromTempFile(inputStream,tempFile)
        return tempFile
    }

    @JvmStatic
    fun createFileFromTempFile(inputStream: InputStream, tempFile: File){
        val outputStream = FileOutputStream(tempFile)
        outputStream.write(inputStream.readBytes())
    }

    @JvmStatic
    fun getAbsolutelyFilePath(path: Uri?, context : Context): String {
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        val index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        val result = c?.getString(index!!)

        return result!!
    }
}