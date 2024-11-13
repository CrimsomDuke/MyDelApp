package com.crimsom.mydelapp.ui.customer_mode.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.crimsom.mydelapp.FakeDB
import com.crimsom.mydelapp.MainActivity
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.aux_interfaces.OnRestaurantClickListener
import com.crimsom.mydelapp.databinding.FragmentCustomerMainBinding
import com.crimsom.mydelapp.models.Restaurant
import com.crimsom.mydelapp.ui.customer_mode.adapters.OrderAdapter
import com.crimsom.mydelapp.ui.customer_mode.adapters.RestaurantAdapter
import com.crimsom.mydelapp.utilities.ShoppingCart

class CustomerMainFragment : Fragment(), OnRestaurantClickListener {

    private lateinit var binding : FragmentCustomerMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomerMainBinding.inflate(inflater, container, false)

        this.setupRecyclerViews()

        return binding.root
    }

    private fun setupRecyclerViews(){

        binding.rvRestaurants.apply {
            adapter = RestaurantAdapter(FakeDB.restaurants, this@CustomerMainFragment)
            layoutManager = LinearLayoutManager(context)
        }

        binding.rvPedidos.apply {
            adapter = OrderAdapter(FakeDB.getOrdersByUserId(MainActivity.currentUserId))
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //reset all values
        MainActivity.selectedRestaurantId = 0;
        ShoppingCart.reset();
    }

    override fun onRestaurantClick(restaurantId: Int) {
        MainActivity.selectedRestaurantId = restaurantId
        findNavController().navigate(R.id.action_customerTabFragment_to_customerRestaurantFragment)
    }

}