package com.amar.chat.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.core.graphics.scale
import java.io.ByteArrayOutputStream

object ImageUtils {

    private const val TAG = "ImageUtilsTAG"

    fun compressAndEncodeImage(context: Context, imageUri: Uri, maxWidth: Int = 640, quality: Int = 70): String? {
        val input = context.contentResolver.openInputStream(imageUri) ?: return null
        val original = BitmapFactory.decodeStream(input) ?: return null
        input.close()

        val scaled = scaleBitmapMaintainingAspect(original, maxWidth)
        val byteArrayOutputStream = ByteArrayOutputStream()
        scaled.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun base64ToBitmap(base64Image: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64Image, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (ex: Exception) {
            Log.d(TAG, "decodeBase64ToBitmap: ${ex.message}")
            null
        }
    }

    private fun scaleBitmapMaintainingAspect(bitmap: Bitmap, maxWidth: Int): Bitmap {
        val w = bitmap.width
        val h = bitmap.height
        if (w <= maxWidth) return bitmap
        val ratio = maxWidth.toFloat() / w
        val nh = (h * ratio).toInt()
        return bitmap.scale(maxWidth, nh)
    }
}
