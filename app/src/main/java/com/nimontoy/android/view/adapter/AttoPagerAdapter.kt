package com.nimontoy.android.view.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

class AttoPagerAdapter(
    fm: FragmentManager,
    private val fragmentList: List<Fragment>,
    private val tabTitleStrList: List<String>
) : FragmentStatePagerAdapter(fm), ViewPager.OnPageChangeListener {

    private val TAG = "AttoPagerAdapter"

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getItemPosition(any: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return super.instantiateItem(container, position)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {}

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitleStrList[position]
    }

}