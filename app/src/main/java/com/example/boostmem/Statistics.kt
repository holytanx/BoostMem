package com.example.boostmem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.boostmem.Adapter.TabStatisticAdapter
import com.example.boostmem.Notification.Notifications
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_statistics.*

class Statistics : AppCompatActivity() {
    private val tabIcon = listOf<Int>(R.drawable.ic_pie_chart,R.drawable.ic_font_download)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)




        val adapter = TabStatisticAdapter(this,supportFragmentManager)
        statistics_viewPager.adapter = adapter
        statistics_tabs.setupWithViewPager(statistics_viewPager)
        statistics_tabs.getTabAt(0)!!.setIcon(tabIcon[0])
        statistics_tabs.getTabAt(1)!!.setIcon(tabIcon[1])



        val bottomNav: BottomNavigationView = findViewById(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.statistics

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
    }


}


