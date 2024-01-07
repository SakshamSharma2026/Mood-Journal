package com.saksham.jetpack.moodjournal.feature_journal.domain.model

import android.net.Uri
import androidx.compose.runtime.Stable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.saksham.jetpack.moodjournal.feature_journal.util.Constants.Affirmations
import com.saksham.jetpack.moodjournal.feature_journal.util.Constants.Aspirations
import com.saksham.jetpack.moodjournal.feature_journal.util.Constants.Creative_Expression
import com.saksham.jetpack.moodjournal.feature_journal.util.Constants.Daily_Highlights
import com.saksham.jetpack.moodjournal.feature_journal.util.Constants.Goal_Setting
import com.saksham.jetpack.moodjournal.feature_journal.util.Constants.Gratitude
import com.saksham.jetpack.moodjournal.feature_journal.util.Constants.HappinessSnapshot
import com.saksham.jetpack.moodjournal.feature_journal.util.Constants.Mindfulness
import com.saksham.jetpack.moodjournal.feature_journal.util.Constants.Mindset_Check
import com.saksham.jetpack.moodjournal.feature_journal.util.Constants.Relationships
import com.saksham.jetpack.moodjournal.feature_journal.util.Constants.Self_Reflection
import com.saksham.jetpack.moodjournal.ui.theme.AlmondCream
import com.saksham.jetpack.moodjournal.ui.theme.BlushPink
import com.saksham.jetpack.moodjournal.ui.theme.Buttercream
import com.saksham.jetpack.moodjournal.ui.theme.ChampagneGold
import com.saksham.jetpack.moodjournal.ui.theme.Creamsicle
import com.saksham.jetpack.moodjournal.ui.theme.FeatherGray
import com.saksham.jetpack.moodjournal.ui.theme.LinenWhite
import com.saksham.jetpack.moodjournal.ui.theme.MintyFresh
import com.saksham.jetpack.moodjournal.ui.theme.MistyBlue
import com.saksham.jetpack.moodjournal.ui.theme.NimbusGray
import com.saksham.jetpack.moodjournal.ui.theme.PalePlatinum
import com.saksham.jetpack.moodjournal.ui.theme.PeachSorbet
import com.saksham.jetpack.moodjournal.ui.theme.PistachioGreen
import com.saksham.jetpack.moodjournal.ui.theme.Rosewater
import com.saksham.jetpack.moodjournal.ui.theme.SerenityBlue
import com.saksham.jetpack.moodjournal.ui.theme.SilverSand
import com.saksham.jetpack.moodjournal.ui.theme.Snowfall
import com.saksham.jetpack.moodjournal.ui.theme.VanillaCream
import com.saksham.jetpack.moodjournal.ui.theme.WhisperWhite
import com.saksham.jetpack.moodjournal.ui.theme.WhisperingWillow
import java.io.File


@Stable
@Entity
data class JournalDataResponse(
    val title: String,
    val content: String,
    val date: String,
    val color: Int,
    val imageUri: Uri = Uri.EMPTY,
    val audioFilePath: File? = null,
    val audioDuration: String = "",
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val journalColors =
            listOf(
                WhisperWhite,
                SilverSand,
                MistyBlue,
                VanillaCream,
                PalePlatinum,
                Snowfall,
                FeatherGray,
                BlushPink,
                MintyFresh,
                LinenWhite,
                Buttercream,
                SerenityBlue,
                PeachSorbet,
                PistachioGreen,
                ChampagneGold,
                NimbusGray,
                Rosewater,
                WhisperingWillow,
                AlmondCream,
                Creamsicle
            )
        val journalPrompts =
            listOf(
                Gratitude,
                Self_Reflection,
                Mindfulness,
                Goal_Setting,
                HappinessSnapshot,
                Relationships,
                Aspirations,
                Creative_Expression,
                Daily_Highlights,
                Affirmations,
                Mindset_Check
            )
    }
}


class InvalidJournalException(message: String) : Exception(message)