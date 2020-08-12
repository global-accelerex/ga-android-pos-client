package com.globalaccelerex.globalaccelerexandroidposclientlibrary.printing

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object PrintingUtils {

    const val ALIGN_LEFT = "left"
    const val ALIGN_RIGHT = "right"
    const val ALIGN_CENTER = "center"
    const val LARGE_SIZE = "large"
    const val SMALL_SIZE = "small"
    private const val LOGO_DIR = "GA/rexretail/res"
    private const val LOGO_FILE = "rexretail_receipt_logo.png"

    suspend fun getLogoPath(context: Context): String? = withContext(Dispatchers.IO) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) return@withContext null
        val logoPath = "$LOGO_DIR/$LOGO_FILE"
        val fullLogoPath = "${Environment.getExternalStorageDirectory().absolutePath}/$logoPath"
        if (File(fullLogoPath).exists()) return@withContext fullLogoPath

        try {
            val inputStream = context.assets.open(LOGO_FILE)
            val outFile = File(fullLogoPath)
            val outDir = File("${Environment.getExternalStorageDirectory().absolutePath}/$LOGO_DIR")
            outDir.mkdirs()
            outFile.createNewFile()
            val outputStream = FileOutputStream(outFile)

            // Copy the file
            val buffer = ByteArray(1024)
            var length: Int = inputStream.read(buffer)
            while (length != -1) {
                outputStream.write(buffer, 0, length)
                length = inputStream.read(buffer)
            }
            inputStream.close()
            outputStream.flush()
            outputStream.close()
            return@withContext fullLogoPath
        } catch (e: IOException) {
            return@withContext null
        }
    }
}