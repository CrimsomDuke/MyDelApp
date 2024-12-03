package com.crimsom.mydelapp.ui.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.crimsom.mydelapp.MainActivity
import com.crimsom.mydelapp.databinding.FragmentTabBinding
import com.crimsom.mydelapp.ui.customer_mode.adapters.CustomerTabViewPagerAdapter
import com.crimsom.mydelapp.ui.driver_mode.adapters.DriverTabViewPagerAdapter
import com.crimsom.mydelapp.utilities.Auth
import com.google.android.material.tabs.TabLayoutMediator

class TabFragment : Fragment() {

    private lateinit var binding : FragmentTabBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabBinding.inflate(inflater, container, false)

        setupTabLayout();
        deactivateSwipeChange();
        this.setupBackButton();

        return binding.root
    }

    private fun setupTabLayout(){
        val viewPagerAdapter : FragmentStateAdapter;
        if(Auth.IS_CURRENT_USER_DRIVER){
            viewPagerAdapter = DriverTabViewPagerAdapter(this)
        }else{
            viewPagerAdapter = CustomerTabViewPagerAdapter(this)
        }

        binding.tabViewPager.adapter = viewPagerAdapter

        val tabTitles = listOf<String>("Main", "Profile")

        TabLayoutMediator(binding.tabLayout, binding.tabViewPager){ tab, position ->
            tab.text = tabTitles[position];
        }.attach()
    }

    private fun deactivateSwipeChange(){
        binding.tabViewPager.isUserInputEnabled = false;
    }

    private fun setupBackButton(){
        (this.requireActivity() as MainActivity).onBackPressedDispatcher.addCallback(this) {
            Toast.makeText(context, "Cierra sesión en la pantalla de perfil para salir", Toast.LENGTH_SHORT).show()
        }
    }

}