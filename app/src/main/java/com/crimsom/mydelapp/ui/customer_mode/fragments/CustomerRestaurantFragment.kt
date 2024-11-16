package com.crimsom.mydelapp.ui.customer_mode.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.crimsom.mydelapp.FakeDB
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.databinding.FragmentCustomerRestaurantBinding
import com.crimsom.mydelapp.ui.customer_mode.adapters.ProductAdapter
import com.crimsom.mydelapp.ui.customer_mode.viewmodels.RestaurantCustomerViewModel
import com.crimsom.mydelapp.utilities.Auth


class CustomerRestaurantFragment : Fragment() {

    private lateinit var binding : FragmentCustomerRestaurantBinding;
    private var restaurantViewModel = RestaurantCustomerViewModel();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomerRestaurantBinding.inflate(inflater, container, false)

        this.restaurantViewModel.getProductsByRestaurantId(Auth.access_token, Auth.selectedRestaurantId);

        this.setupRestaurant();
        this.setupRecyclerViews();
        this.setupGoToCartButton();
        this.setupObservers();

        return binding.root
    }

    private fun setupRestaurant(){
        var restaurant = restaurantViewModel.restaurant.value
        binding.custRestNameLabel.text = restaurant?.name
    }

    private fun setupRecyclerViews(){
        binding.rvProducts.apply {
            adapter = ProductAdapter(restaurantViewModel.restaurant.value!!.productsList)
            layoutManager = LinearLayoutManager(context)
        }
    }
    private fun setupObservers(){
        this.restaurantViewModel.restaurant.observe(viewLifecycleOwner) {
            this.setupRestaurant()
            binding.rvProducts.adapter.apply {
                (this as ProductAdapter).updateData(it.productsList)
            }
        }
    }

    private fun setupGoToCartButton(){
        binding.custRestGoToCartButton.setOnClickListener{
            var navController = findNavController();
            navController.navigate(R.id.action_customerRestaurantFragment_to_shoppingCartFragment)
        }
    }
}