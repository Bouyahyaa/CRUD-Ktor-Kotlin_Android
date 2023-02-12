package com.example.crud_ktor_kotlin_android.core.util

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore

object Constants {
    fun getImageFilePath(context: Context, uri: Uri?): String? {
        var path: String? = null
        var image_id: String? = null
        var cursor: Cursor =
            context.contentResolver.query(uri!!, null, null, null, null) ?: return null
        if (cursor != null) {
            cursor.moveToFirst()
            image_id = cursor.getString(0)
            image_id = image_id.substring(image_id.lastIndexOf(":") + 1)
            cursor.close()
        }
        cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            MediaStore.Images.Media._ID + " = ? ",
            arrayOf(image_id),
            null
        )!!
        if (cursor != null) {
            cursor.moveToFirst()
            path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
            cursor.close()
        }
        return path
    }
}