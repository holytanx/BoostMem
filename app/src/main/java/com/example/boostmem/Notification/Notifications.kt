package com.example.boostmem.Notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.boostmem.*
import com.example.boostmem.Adapter.TabWeekAdapter
import com.example.boostmem.Database.Models.NotificationModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_notifications.*
import java.io.Serializable
import java.time.DayOfWeek
import java.util.*


class Notifications : AppCompatActivity() {
    lateinit var deckViewModel:DeckViewModel
    lateinit var mContext : Context
    lateinit var mAlarmManager : AlarmManager
    var dayList  = listOf<String>("monday","tuesday","wednesday","thursday","friday","saturday","sunday")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
        mContext = this
        val tabWeekAdapter:TabWeekAdapter = TabWeekAdapter(this,supportFragmentManager)
        tabWeekAdapter.addFragment(MonFragment())
        tabWeekAdapter.addFragment(TueFragment())
        tabWeekAdapter.addFragment(WedFragment())
        tabWeekAdapter.addFragment(ThuFragment())
        tabWeekAdapter.addFragment(FriFragment())
        tabWeekAdapter.addFragment(SatFragment())
        tabWeekAdapter.addFragment(SunFragment())

        notificationPager.adapter = tabWeekAdapter
        tab_daily.setupWithViewPager(notificationPager)

        val bottomNav: BottomNavigationView = findViewById(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.notification

        bottomNav.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.Home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.statistics -> {
                    startActivity(Intent(this, Statistics::class.java))
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.notification -> {
                    startActivity(Intent(this, Notifications::class.java))
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.settings -> {
                    startActivity(Intent(this, Settings::class.java))
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }


                else -> false
            }
        }
        deckViewModel = DeckViewModel(application)
        val checkListObserver =deckViewModel.allNoti.observe(this, Observer {

             var list = it.filter { it.isActive.equals(true) }

            for ((index,value) in list.withIndex()){
                setAlarm(value)
            }
        })



    }

    private fun setAlarm(noti : NotificationModel) {
        var times = noti.time.split(":")
        var hr = times[0].trim().toInt()
        var min = times[1].trim().toInt()
        for (i in noti.dayList!!){
            var time : Long =0

            when(i){
                "monday" -> time += scheduleAlarm(Calendar.MONDAY,hr,min)
                "tuesday" -> time += scheduleAlarm(Calendar.TUESDAY,hr,min)
                "wednesday" -> time += scheduleAlarm(Calendar.WEDNESDAY,hr,min)
                "thursday" -> time += scheduleAlarm(Calendar.THURSDAY,hr,min)
                "friday" -> time += scheduleAlarm(Calendar.FRIDAY,hr,min)
                "saturday" -> time += scheduleAlarm(Calendar.SATURDAY,hr,min)
                "sunday" -> time += scheduleAlarm(Calendar.SUNDAY,hr,min)
            }
            val senderNotification: PendingIntent

            mAlarmManager = mContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            // send to AlarmReceiver
            intent = Intent(this@Notifications, NotificationBroadcast::class.java)
            val bundle = Bundle()
            bundle.putSerializable("alarmobj", noti as Serializable?)
            intent.putExtra("item", bundle)

            // for notification
            senderNotification = PendingIntent.getBroadcast(
                applicationContext,
                noti.notificationID.toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            mAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, time, 24*60*60*1000, senderNotification);

            Log.i(
                "Send Notification",
                "Alarm set : " + noti.toString() + " Success "
            )
        }



    }

    fun createnewNotification(view: View) {

        val intent = Intent(this,CreateNotification::class.java)
        startActivity(intent)
    }

    fun scheduleAlarm(dayOfWeek: Int ,hour:Int, min :Int): Long{
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK,dayOfWeek)
        calendar.set(Calendar.HOUR_OF_DAY,hour)
        calendar.set(Calendar.MINUTE,min)
//        if(calendar.timeInMillis < System.currentTimeMillis()) {
//            calendar.add(Calendar.DAY_OF_YEAR, 7);
//        }
        return calendar.timeInMillis

    }




}
