package com.example.boostmem.Notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.boostmem.Database.Models.NotificationModel


class NotificationBroadcast : BroadcastReceiver() {
    companion object {
        private const val TAG = "AlarmReceiver"

    }


    override fun onReceive(context: Context, intent: Intent) {

        //get object
        val bundleReceived = intent.getBundleExtra("item")
        val notification: NotificationModel = bundleReceived.getSerializable("alarmobj") as NotificationModel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotification(context, notification)
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotification(
        context: Context,
        notification: NotificationModel
    ) {
        val notificationHelper = NotificationHelper(context)
        val nb: NotificationCompat.Builder = notificationHelper.getChanel1(notification.deckName, "Time to boost your memory !! Let's get it ")
        notificationHelper.getManager()?.notify(notification.notificationID.toInt(), nb.build())
    }
}