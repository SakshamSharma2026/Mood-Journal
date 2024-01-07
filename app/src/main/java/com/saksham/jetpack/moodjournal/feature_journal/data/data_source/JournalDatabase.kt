package com.saksham.jetpack.moodjournal.feature_journal.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.saksham.jetpack.moodjournal.feature_journal.domain.model.JournalDataResponse
import com.saksham.jetpack.moodjournal.feature_journal.domain.util.journal.JournalDataTypeConverter


@Database(entities = [JournalDataResponse::class], version = 1)
@TypeConverters(JournalDataTypeConverter::class)
abstract class JournalDatabase : RoomDatabase() {
    abstract val journalDao: JournalDao
}