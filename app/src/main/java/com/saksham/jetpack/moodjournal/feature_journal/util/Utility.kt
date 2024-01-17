package com.saksham.jetpack.moodjournal.feature_journal.util

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.ComponentActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun getCurrentDateTime(
    format: String = "EEEE, d MMM yyyy", locale: Locale = Locale.getDefault()
): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(Calendar.getInstance().time).toString()
}


fun shareContent(context: Context, title: String, description: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "$title\n\n$description")
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}

fun formatDuration(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}


fun generateRecordingName(path: String?): String {
    return "${path}/MoodJournal-${
        SimpleDateFormat("ddMMyyyy-HHmmss", Locale.getDefault()).format(
            Calendar.getInstance().time
        )
    }.mp3"
}

@Composable
fun hsp(value: Int) {
    Spacer(modifier = Modifier.height(value.dp))
}

fun getAppVersion(context: Context): String {
    val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
    return packageInfo.versionName
}

fun openBrowser(context: Context) {
    val url = "https://www.app-privacy-policy.com/live.php?token=EoT0Pt8H4oV8Q3LzsRkxBTs6MEdCRDrx"
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(browserIntent)
}


fun sendFeedbackByEmail(context: Context) {

    val deviceModel = Build.MODEL
    val androidVersion = Build.VERSION.RELEASE

    val body =
        "\n\n\n\n\n\n\n\nDevice Model : $deviceModel \nApp Version : ${getAppVersion(context)} \nAndroid Version : $androidVersion"

    val emailUrl =
        "mailto:consolecrafttechnologies@gmail.com?subject=Mood Journal Feedback/Suggestions&body=$body"

    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse(emailUrl)
    }
    try {
        context.startActivity(Intent.createChooser(intent, "Send Email"))
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


fun shareApp(context: Context) {
    val appPackageName: String = context.packageName
    val sendIntent = Intent()
    sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    sendIntent.action = Intent.ACTION_SEND
    sendIntent.putExtra(
        Intent.EXTRA_TEXT,
        "Check out the App at:\n https://play.google.com/store/apps/details?id=$appPackageName"
    )
    sendIntent.type = "text/plain"
    context.startActivity(sendIntent)
}

fun rateApp(context: Context) {
    val appName = context.packageName
    try {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$appName")
            )
        )
    } catch (e: ActivityNotFoundException) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=$appName")
            )
        )
    }
}

fun requiredAudioPermission(context: ComponentActivity) {
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) != PackageManager.PERMISSION_GRANTED
    )
        ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.RECORD_AUDIO), 0)
}