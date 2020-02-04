package com.example.boostmem.DataExample

import android.app.Notification
import com.example.boostmem.models.notification


class DataExample3{
    companion object {
        fun createNotifcation(): ArrayList<notification> {
            val multiNoti = ArrayList<notification>()

            multiNoti.add(
                notification("History II",true,timeList = arrayListOf("6.30 AM","7.00 PM"),dayList = arrayListOf("Daily","Mon"))
            )
            multiNoti.add(
                notification("Chemistry II",true,timeList = arrayListOf("9.30 AM"),dayList = arrayListOf("Daily"))
            )
            return multiNoti
        }
    }
}