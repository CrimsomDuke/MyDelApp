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
import com.crimsom.mydelapp.databinding.FragmentCustomerRestaurantBinding
import com.crimsom.mydelapp.ui.customer_mode.adapters.ProductAdapter


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

        this.setupRestaurant();
        this.setupRecyclerViews();
        this.setupGoToCartButton();

        return binding.root
    }

    private fun setupRestaurant(){
        var restaurant = FakeDB.restaurants.find { it.id == MainActivity.selectedRestaurantId }
        binding.custRestNameLabel.text = restaurant?.nombre
    }

    private fun setupRecyclerViews(){
        binding.rvProducts.apply {
            adapter = ProductAdapter(FakeDB.getProductsByRestaurantId(MainActivity.selectedRestaurantId))
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupGoToCartButton(){
        binding.custRestGoToCartButton.setOnClickListener{
            var navController = findNavController();
            navController.navigate(R.id.action_customerRestaurantFragment_to_shoppingCartFragment)
        }
    }
}