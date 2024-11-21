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
import com.crimsom.mydelapp.aux_interfaces.OnCurrentOrderItemListener
import com.crimsom.mydelapp.aux_interfaces.OnRestaurantClickListener
import com.crimsom.mydelapp.databinding.FragmentCustomerMainBinding
import com.crimsom.mydelapp.ui.customer_mode.adapters.OrderAdapter
import com.crimsom.mydelapp.ui.customer_mode.adapters.RestaurantAdapter
import com.crimsom.mydelapp.ui.customer_mode.viewmodels.MainCustomerViewModel
import com.crimsom.mydelapp.utilities.Auth
import com.crimsom.mydelapp.utilities.Constants
import com.crimsom.mydelapp.utilities.ShoppingCart

class CustomerMainFragment : Fragment(), OnRestaurantClickListener, OnCurrentOrderItemListener {

    private lateinit var binding : FragmentCustomerMainBinding;

    private var mainViewModel = MainCustomerViewModel();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomerMainBinding.inflate(inflater, container, false)

        this.mainViewModel.getCurrentUser(Auth.access_token);
        this.mainViewModel.getRestaurants(Auth.access_token);
        this.mainViewModel.getOrderOfUser(Auth.access_token);

        this.binding.custWelcomeLabel.text = "Bienvenido ${mainViewModel.currentUser.value?.username} ¿Que pedirás hoy?"

        this.setupRecyclerViews()
        this.setupObservers()

        return binding.root
    }

    private fun setupRecyclerViews(){

        binding.rvRestaurants.apply {
            adapter = RestaurantAdapter(mainViewModel.restaurantsList.value!!, this@CustomerMainFragment)
            layoutManager = LinearLayoutManager(context)
        }

        binding.rvPedidos.apply {
            //We only wan the orders that are not delivered here
            adapter = OrderAdapter(
                mainViewModel.ordersList.value!!,
                this@CustomerMainFragment
            )
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //reset all values
        clearData()

    }

    private fun setupObservers(){
        mainViewModel.restaurantsList.observe(viewLifecycleOwner) {
            binding.rvRestaurants.adapter.apply {
                (this as RestaurantAdapter).updateData(it)
            }
        }

        mainViewModel.currentUser.observe(viewLifecycleOwner) {
            Auth.currentUser = it;
        }

        mainViewModel.ordersList.observe(viewLifecycleOwner) {
            binding.rvPedidos.adapter.apply {
                (this as OrderAdapter).updateData(it)
            }
        }
    }

    override fun onRestaurantClick(restaurantId: Int) {
        Auth.cust_selectedRestaurantId = restaurantId
        findNavController().navigate(R.id.action_customerTabFragment_to_customerRestaurantFragment)
    }

    private fun clearData(){
        Auth.cust_selectedRestaurantId = 0;
        ShoppingCart.reset();
    }

    override fun onCurrentOrderItemClick(orderId: Int) {
        val navController = findNavController()
        var bundle = Bundle()
        bundle.putInt("orderId", orderId)
        navController.navigate(R.id.action_customerTabFragment_to_customerFullOrderMapFragment, bundle)
    }

}