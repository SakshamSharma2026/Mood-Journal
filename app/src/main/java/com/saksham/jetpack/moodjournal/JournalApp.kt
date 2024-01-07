package com.saksham.jetpack.moodjournal

import android.app.Application
import android.content.Intent
import android.net.Uri
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class JournalApp : Application() {




    init {
        instance = this
    }


    companion object {
        private var instance: JournalApp? = null

        fun getUriPermission(uri: Uri) {
            instance!!.applicationContext.contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }
    }
}