package com.crimsom.mydelapp.ui.customer_mode.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.crimsom.mydelapp.ui.customer_mode.fragments.CustomerMainFragment
import com.crimsom.mydelapp.ui.customer_mode.fragments.CustomerProfileFragment

class TabViewPagerAdapter(fragmentParent: Fragment) : FragmentStateAdapter(fragmentParent) {

    private val fragments = mutableListOf<Fragment>(
        CustomerMainFragment(),
        CustomerProfileFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size;
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> CustomerMainFragment()
            1 -> CustomerProfileFragment()
            else -> CustomerMainFragment()
        }
    }
}