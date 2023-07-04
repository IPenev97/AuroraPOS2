package com.sistechnology.aurorapos2.core.utils

import android.content.Context
import android.content.Intent
import android.os.Environment
import mu.KotlinLogging
import java.io.File


class SendLogsUtil {
    fun sendLogsToEmail(context: Context) {
        // save logcat in file
        val logger = KotlinLogging.logger {}
        val outputFile: File = File(
            Environment.getExternalStorageDirectory(),
            "logcat.txt"
        );
        try {
            Runtime.getRuntime().exec(
                "logcat -f " + outputFile.getAbsolutePath()
            );
        } catch (e: Exception) {
            logger.error { e.message }
        }

        //send file using email
        val emailIntent = Intent(Intent.ACTION_SEND);
        // Set type to "email"
        emailIntent.type = "vnd.android.cursor.dir/email";
        val to: String = "I.Penev97@gmail.com";
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        // the attachment
        emailIntent.putExtra(Intent.EXTRA_STREAM, outputFile.getAbsolutePath());
        // the mail subject
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

}