package hr.algebra.ecommerce.handler

import android.content.Context
import android.util.Log
import com.google.firebase.storage.StorageReference
import hr.algebra.ecommerce.factory.createGetHttpUrlConnection
import java.io.File
import java.net.HttpURLConnection
import java.nio.file.Files
import java.nio.file.Paths

fun downloadAndStore(context: Context, url: String) : String? {

    val realUrl = getUrl(url)
    if(realUrl!=null) {
        val file = createFile(context, generateFileName(realUrl))

        try {
            val con: HttpURLConnection = createGetHttpUrlConnection(realUrl)

            Files.copy(con.inputStream, Paths.get(file.toURI()))

            return file.absolutePath

        } catch (e: Exception) {
            Log.e("IMAGES_HANDLER", e.toString(), e)
        }
    }
    return null
}

fun downloadAndStoreFromFirebase(context: Context, imageRef: StorageReference, onComplete: (String?) -> Unit) {
    val imageName = imageRef.name
    val localFile = File(context.getExternalFilesDir(null), imageName)

    imageRef.getFile(localFile)
        .addOnSuccessListener {
            onComplete(localFile.absolutePath)
        }
        .addOnFailureListener {
            onComplete(null)
        }
}

fun getUrl(url: String): String? {
    val regex = Regex("""https?://[^\s"]+""")
    val matchResult = regex.find(url)

    return matchResult?.value
}

fun createFile(context: Context, filename: String): File {
    val dir = context.applicationContext.getExternalFilesDir(null)
    val file = File(dir, filename)
    if(file.exists()) file.delete()
    return file
}

fun generateFileName(url: String): String {
    val hash = url.hashCode()
    val timeStamp = System.currentTimeMillis()
    return "${hash}_${timeStamp}"
}


fun deleteImage(imagePath: String) {
    val file = File(imagePath)
    if (file.exists()) {
        file.delete()
    }
}

fun changeImageName(context: Context, url: String, newUrl: String?): String? {
    try {
        if(newUrl!=null) {
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
        }
    } catch (e: Exception) {
        Log.e("IMAGES_HANDLER", e.toString(), e)
    }

    return null
}
