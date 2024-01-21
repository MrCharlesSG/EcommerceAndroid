package hr.algebra.ecommerce.handler

import android.content.Context
import android.util.Log
import hr.algebra.ecommerce.factory.createGetHttpUrlConnection
import java.io.File
import java.net.HttpURLConnection
import java.nio.file.Files
import java.nio.file.Paths

fun downloadAndStore(context: Context, url: String) : String? {


    val filename = url.substring(url.lastIndexOf(File.separatorChar) + 1)
    val file = createFile(context, filename)

    try {
        val con: HttpURLConnection = createGetHttpUrlConnection(url)

        Files.copy(con.inputStream, Paths.get(file.toURI()))

        return file.absolutePath

    } catch (e: Exception) {
        Log.e("IMAGES_HANDLER", e.toString(), e)
    }


    return null
}

fun createFile(context: Context, filename: String): File {
    val dir = context.applicationContext.getExternalFilesDir(null)
    val file = File(dir, filename)
    if(file.exists()) file.delete()
    return file
}


fun deleteImage(imagePath: String) {
    val file = File(imagePath)
    if (file.exists()) {
        file.delete()
    }
}

fun changeImageName(context: Context, url: String, newUrl: String): String? {
    try {
        val originalFile = File(url)
        if (!originalFile.exists()) {
            Log.e("IMAGES_HANDLER", "Does not exists $url")
            return null
        }

        val filename = newUrl.substring(newUrl.lastIndexOf(File.separatorChar) + 1)
        val newFile = createFile(context, filename)

        originalFile.inputStream().use { inputStream ->
            newFile.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }

        return newFile.absolutePath

    } catch (e: Exception) {
        Log.e("IMAGES_HANDLER", e.toString(), e)
    }

    return null
}
