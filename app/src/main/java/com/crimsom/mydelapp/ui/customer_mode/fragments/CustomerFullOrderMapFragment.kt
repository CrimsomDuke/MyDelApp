package com.crimsom.mydelapp.ui.customer_mode.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.replace
import androidx.navigation.fragment.findNavController
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.aux_interfaces.OnOrderConfirmationListener
import com.crimsom.mydelapp.databinding.FragmentCustomerFullOrderMapBinding
import com.crimsom.mydelapp.repositories.OrderRepository
import com.crimsom.mydelapp.repositories.RestaurantRepository
import com.crimsom.mydelapp.ui.common.viewmodels.ProfileViewModel
import com.crimsom.mydelapp.ui.customer_mode.fragments.sub_fragments.CustomerCurrentOrderStatusFragment
import com.crimsom.mydelapp.ui.customer_mode.fragments.sub_fragments.CustomerMapFragment
import com.crimsom.mydelapp.ui.customer_mode.viewmodels.FullOrderMapViewModel
import com.crimsom.mydelapp.utilities.Auth
import com.crimsom.mydelapp.utilities.ShoppingCart
import com.google.android.gms.maps.model.LatLng


class CustomerFullOrderMapFragment : Fragment(), OnOrderConfirmationListener {


    private lateinit var binding : FragmentCustomerFullOrderMapBinding;
    private var viewModel = FullOrderMapViewModel();

    private var orderId : Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.orderId = arguments?.getInt("orderId") ?: 0;
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomerFullOrderMapBinding.inflate(inflater, container, false)

        setupObservers();

        if(this.orderId != 0){
            viewModel.getOrderData(Auth.access_token, this.orderId);
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFragmentsActions()
    }

    private fun setupFragmentsActions(){
        //set up the current order status fragment actions
        if(orderId != 0){
            return;
        }
        val customerCurrentOrderStatusFragment = (binding.fragmentContainerCustOrderStatus.getFragment<CustomerCurrentOrderStatusFragment>())
        customerCurrentOrderStatusFragment.setOnOrderConfirmationListener(this)
    }

    override fun onOrderConfirmation() {

        var myOrder = ShoppingCart.createOrderFromCart(Auth.currentUserId, Auth.cust_selectedRestaurantId, "Pendiente", "Pendiente")

        if(Auth.currentUserLatitude.isNotEmpty() && Auth.currentUserLongitude.isNotEmpty()){
            myOrder.latitude = Auth.currentUserLatitude
            myOrder.longitude = Auth.currentUserLongitude
        }
        Log.i("ORDER_DATA_PRE", "Order PRE_POST: $myOrder")

        OrderRepository.createOrder(Auth.access_token, myOrder, onSuccess = {
            Log.i("ORDER_DATA", "Order created: $it")
            myOrder = it;

            //bundle the order id to the next fragment
            val bundle = Bundle()
            bundle.putInt("orderId", myOrder.id)
            findNavController().navigate(R.id.action_customerFullOrderMapFragment_self, bundle)

        }, onError = {
            Log.e("ORDER_DATA", "Error creating order: $it")
        })

    }

    private fun loadOrderDataIntoSubFragments(){
        val customerCurrentOrderStatusFragment = (binding.fragmentContainerCustOrderStatus.getFragment<CustomerCurrentOrderStatusFragment>())
        customerCurrentOrderStatusFragment.setOrderStatus(viewModel.orderData.value!!.status)

        val mapFragment = binding.fragmentContainerCustOrderMap.getFragment<CustomerMapFragment>()

        val restaurant = viewModel.restaurantData.value!!
        val order = viewModel.orderData.value!!

        //Restaurant lcoation
        var originLocation = LatLng(
            restaurant.latitude.toDouble(),
            restaurant.longitude.toDouble()
        )

        //User location
        var destinationLocation = LatLng(
            order.latitude.toDouble(),
            order.longitude.toDouble()
        )

        //we set the markers with their titles
        mapFragment.setupOriginAndDestinyMarkers(originLocation, restaurant.name, destinationLocation, order.address)
    }

    private fun setupObservers(){
        //first, this one will get triggered
        viewModel.orderData.observe(viewLifecycleOwner){
            Log.i("ORDER_DATA_IN_MAP", "Order data: $it")
            viewModel.getRestaurantData(Auth.access_token, it.restaurantId)
        }

        //and then, whn there is an order, there will be a fuckibng restaurant
        viewModel.restaurantData.observe(viewLifecycleOwner){
            Log.i("ORDER_DATA_IN_MAP", "Restaurant data: $it")
            loadOrderDataIntoSubFragments()
        }
    }
}