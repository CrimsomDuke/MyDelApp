package com.crimsom.mydelapp.ui.customer_mode.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.crimsom.mydelapp.ui.customer_mode.fragments.CustomerMainFragment
import com.crimsom.mydelapp.ui.common.ProfileFragment

class CustomerTabViewPagerAdapter(fragmentParent: Fragment) : FragmentStateAdapter(fragmentParent) {

    private val fragments = mutableListOf<Fragment>(
        CustomerMainFragment(),
        ProfileFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size;
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> CustomerMainFragment()
            1 -> ProfileFragment()
            else -> CustomerMainFragment()
        }
    }
}