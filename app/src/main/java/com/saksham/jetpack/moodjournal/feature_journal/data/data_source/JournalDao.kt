package com.saksham.jetpack.moodjournal.feature_journal.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saksham.jetpack.moodjournal.feature_journal.domain.model.JournalDataResponse
import kotlinx.coroutines.flow.Flow


@Dao
interface JournalDao {

    @Query("SELECT * FROM journaldataresponse ORDER BY id DESC")
    fun getJournal(): Flow<List<JournalDataResponse>>


    @Query("SELECT * FROM journaldataresponse WHERE id=:id")
    suspend fun getJournalById(id: Int): JournalDataResponse?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournal(journalDataResponse: JournalDataResponse)


    @Delete
    suspend fun deleteJournal(journalDataResponse: JournalDataResponse)
}

