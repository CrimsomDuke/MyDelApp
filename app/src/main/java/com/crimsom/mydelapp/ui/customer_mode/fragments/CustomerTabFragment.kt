package com.crimsom.mydelapp.ui.customer_mode.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.databinding.FragmentCustomerTabBinding
import com.crimsom.mydelapp.ui.customer_mode.adapters.TabViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class CustomerTabFragment : Fragment() {

    private lateinit var binding : FragmentCustomerTabBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomerTabBinding.inflate(inflater, container, false)

        setupTabLayout();
        deactivateSwipeChange();

        return binding.root
    }

    private fun setupTabLayout(){
        val viewPagerAdapter = TabViewPagerAdapter(this)
        binding.tabViewPager.adapter = viewPagerAdapter

        val tabTitles = listOf<String>("Main", "Profile")

        TabLayoutMediator(binding.tabLayout, binding.tabViewPager){ tab, position ->
            tab.text = tabTitles[position];
        }.attach()
    }

    private fun deactivateSwipeChange(){
        binding.tabViewPager.isUserInputEnabled = false;
    }
}