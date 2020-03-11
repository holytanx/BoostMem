package com.example.boostmem.Notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.boostmem.R

@RequiresApi(Build.VERSION_CODES.O)
open class NotificationHelper(var base: Context?) : ContextWrapper(base) {
   companion object
   {val chanel1ID = "ID1"}
    var context :Context = this.base!!

    fun getManager() = getSystemService(NOTIFICATION_SERVICE) as NotificationManager?


    init {
        base = this.context
        createChannels()

    }

    private fun createChannels() {
        val channel1 = NotificationChannel(
            NotificationHelper.chanel1ID,
            "channel1",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel1.enableLights(true)
        channel1.enableVibration(true)
        channel1.lightColor = R.color.colorPrimary
        getManager()!!.createNotificationChannel(channel1)
    }

    open fun getChanel1(
        title: String,
        message: String?
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(applicationContext, chanel1ID)
            .setContentTitle("Reminder : $title")
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_device_hub)
//            .setAutoCancel(true)
    }



}