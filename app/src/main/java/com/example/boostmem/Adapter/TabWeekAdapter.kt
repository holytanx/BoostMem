package com.example.boostmem.Adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.boostmem.GradesFragment
import com.example.boostmem.Notification.*
import com.example.boostmem.OverallFragment
import com.example.boostmem.R

class TabWeekAdapter(context : Context, supportFragmentManager: FragmentManager) : FragmentStatePagerAdapter(supportFragmentManager) {
    val mContext: Context = context
    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return when(position){
            0-> MonFragment()
            1-> TueFragment()
            2-> WedFragment()
            3-> ThuFragment()
            4-> FriFragment()
            5-> SatFragment()
            6-> SunFragment()
            else -> MonFragment()
        }
    }

    override fun getCount(): Int {
        return 7
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0-> mContext.getString(R.string.mon)
            1-> mContext.getString(R.string.tue)
            2-> mContext.getString(R.string.wed)
            3-> mContext.getString(R.string.thu)
            4-> mContext.getString(R.string.fri)
            5-> mContext.getString(R.string.sat)
            6-> mContext.getString(R.string.sun)

            else -> R.string.overall.toString()
        }
    }

    fun addFragment(fragment: Fragment) {
        mFragmentList.add(fragment)
    }


}