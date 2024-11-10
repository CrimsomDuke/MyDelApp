package com.crimsom.mydelapp.ui.customer_mode.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.crimsom.mydelapp.FakeDB
import com.crimsom.mydelapp.MainActivity
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.databinding.FragmentCustomerProfileBinding
import com.crimsom.mydelapp.ui.customer_mode.adapters.OrderAdapter


class CustomerProfileFragment : Fragment() {

    private lateinit var binding : FragmentCustomerProfileBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomerProfileBinding.inflate(inflater, container, false)

        this.setupUserDetails();
        this.setupRecyclerView();

        return binding.root
    }

    private fun setupUserDetails(){

        binding.custProfilePic.setImageResource(R.drawable.person);

        val user = FakeDB.getUserById(MainActivity.currentUserId)
        binding.custEmailLabel.text = user!!.email
        binding.custTypeUserLabel.text = if(FakeDB.isDriver(user)) "Driver" else "Customer"
    }

    private fun setupRecyclerView(){
        binding.rvOrderHistory.apply {
            adapter = OrderAdapter(FakeDB.getOrdersByUserId(MainActivity.currentUserId))
            layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.VERTICAL }
        }
    }
}