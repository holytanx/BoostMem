package com.example.boostmem.Adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.boostmem.GradesFragment
import com.example.boostmem.OverallFragment
import com.example.boostmem.R


class TabStatisticAdapter(context : Context, supportFragmentManager: FragmentManager) : FragmentStatePagerAdapter(supportFragmentManager) {
    val mContext:Context = context
    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return when(position){
            0-> OverallFragment()
            1-> GradesFragment()
            else -> OverallFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0-> mContext.getString(R.string.overall)
            1-> mContext.getString(R.string.grades)
            else -> R.string.overall.toString()
        }
    }

    fun addFragment(fragment: Fragment) {
        mFragmentList.add(fragment)
    }


}