package com.crimsom.mydelapp.ui.driver_mode.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.crimsom.mydelapp.ui.common.ProfileFragment
import com.crimsom.mydelapp.ui.driver_mode.fragments.DriverMainFragment

class DriverTabViewPagerAdapter(fragmentParent: Fragment) : FragmentStateAdapter(fragmentParent){

    private val fragments = mutableListOf<Fragment>(
        DriverMainFragment(),
        ProfileFragment()
    )
    override fun getItemCount(): Int {
        return fragments.size;
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> DriverMainFragment()
            1 -> ProfileFragment()
            else -> DriverMainFragment()
        }
    }

}