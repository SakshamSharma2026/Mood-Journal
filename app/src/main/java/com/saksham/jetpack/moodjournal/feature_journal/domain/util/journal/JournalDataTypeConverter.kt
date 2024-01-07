package com.saksham.jetpack.moodjournal.feature_journal.domain.util.journal

import android.net.Uri
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.io.File


@ProvidedTypeConverter
class JournalDataTypeConverter {
    @TypeConverter
    fun fromUri(uri: Uri): String {
        return uri.toString()
    }

    @TypeConverter
    fun toUri(uriString: String): Uri {
        return Uri.parse(uriString)
    }

    @TypeConverter
    fun fromFile(file: File): String {
        return file.absolutePath
    }

    @TypeConverter
    fun toFile(absolutePath: String): File {
        return File(absolutePath)
    }

}