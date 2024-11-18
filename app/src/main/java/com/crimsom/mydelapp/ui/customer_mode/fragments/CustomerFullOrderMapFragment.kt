package com.crimsom.mydelapp.ui.customer_mode.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.aux_interfaces.OnOrderConfirmationListener
import com.crimsom.mydelapp.databinding.FragmentCustomerFullOrderMapBinding
import com.crimsom.mydelapp.repositories.OrderRepository
import com.crimsom.mydelapp.repositories.RestaurantRepository
import com.crimsom.mydelapp.ui.customer_mode.fragments.sub_fragments.CustomerCurrentOrderStatusFragment
import com.crimsom.mydelapp.ui.customer_mode.fragments.sub_fragments.CustomerMapFragment
import com.crimsom.mydelapp.utilities.Auth
import com.crimsom.mydelapp.utilities.ShoppingCart
import com.google.android.gms.maps.model.LatLng


class CustomerFullOrderMapFragment : Fragment(), OnOrderConfirmationListener {


    private lateinit var binding : FragmentCustomerFullOrderMapBinding;

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



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFragmentsActions()
    }

    private fun setupFragmentsActions(){
        //set up the current order status fragment actions
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

            RestaurantRepository.getRestaurantById(Auth.access_token, myOrder.restaurantId, onSuccess = {

                val mapFragment = binding.fragmentContainerCustOrderMap.getFragment<CustomerMapFragment>()
                mapFragment.addMarker(LatLng(it.latitude.toDouble(), it.longitude.toDouble()), "Restaurante")

            }, onError = {
                Log.e("ORDER_DATA", "Error getting restaurant data: $it")
            })
        }, onError = {
            Log.e("ORDER_DATA", "Error creating order: $it")
        })

    }
}