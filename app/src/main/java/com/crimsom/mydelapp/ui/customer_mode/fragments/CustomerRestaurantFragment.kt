package com.crimsom.mydelapp.ui.customer_mode.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.crimsom.mydelapp.FakeDB
import com.crimsom.mydelapp.MainActivity
import com.crimsom.mydelapp.databinding.FragmentCustomerRestaurantBinding
import com.crimsom.mydelapp.ui.customer_mode.adapters.ProductAdapter
import com.crimsom.mydelapp.utilities.ShoppingCart


class CustomerRestaurantFragment : Fragment() {

    private lateinit var binding : FragmentCustomerRestaurantBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomerRestaurantBinding.inflate(inflater, container, false)

        this.setupRecyclerViews();

        return binding.root
    }

    private fun setupRecyclerViews(){
        binding.rvProducts.apply {
            adapter = ProductAdapter(FakeDB.getProductsByRestaurantId(MainActivity.selectedRestaurantId))
            layoutManager = LinearLayoutManager(context)
        }
    }
}