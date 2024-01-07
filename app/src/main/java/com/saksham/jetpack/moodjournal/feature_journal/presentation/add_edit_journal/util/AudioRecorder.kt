package com.saksham.jetpack.moodjournal.feature_journal.presentation.add_edit_journal.util

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun stop()
}