package com.crimsom.mydelapp.ui.driver_mode.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.crimsom.mydelapp.FakeDB
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.databinding.FragmentDriverMainBinding
import com.crimsom.mydelapp.ui.driver_mode.adapters.UntakenOrderAdapter


class DriverMainFragment : Fragment() {

    private lateinit var binding : FragmentDriverMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDriverMainBinding.inflate(inflater, container, false)

        this.setupRecyclerViews();

        return binding.root
    }

    private fun setupRecyclerViews(){
        binding.rvUntakenOrders.apply {
            adapter = UntakenOrderAdapter(FakeDB.getUntakenOrders())
            layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.VERTICAL }
        }
    }
}