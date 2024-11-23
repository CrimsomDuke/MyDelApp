package com.crimsom.mydelapp.ui.driver_mode.fragments

import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.crimsom.mydelapp.FakeDB
import com.crimsom.mydelapp.MainActivity
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.aux_interfaces.OnDriverLocationUpdateListener
import com.crimsom.mydelapp.aux_interfaces.OnUntakenOrderClickListener
import com.crimsom.mydelapp.databinding.FragmentDriverMainBinding
import com.crimsom.mydelapp.models.Order
import com.crimsom.mydelapp.models.aux_models.DriverLocation
import com.crimsom.mydelapp.repositories.DriverRepository
import com.crimsom.mydelapp.repositories.RestaurantRepository
import com.crimsom.mydelapp.tasks.SendDriverLocationTask
import com.crimsom.mydelapp.ui.driver_mode.adapters.UntakenOrderAdapter
import com.crimsom.mydelapp.ui.driver_mode.viewmodels.DriverMainViewModel
import com.crimsom.mydelapp.utilities.Auth
import com.techiness.progressdialoglibrary.ProgressDialog


class DriverMainFragment : Fragment(), OnUntakenOrderClickListener, OnDriverLocationUpdateListener {

    private lateinit var binding : FragmentDriverMainBinding;
    private val mainViewModel  = DriverMainViewModel();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDriverMainBinding.inflate(inflater, container, false)

        mainViewModel.getFreeOrders(Auth.access_token)

        this.setupDriverDetails();
        this.setupRecyclerViews();
        this.setupObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(Auth.IS_CURRENT_USER_DRIVER){
            Log.i("DRIVER_LOCATION_UPDATES", "Starting driver location updates")
            val driverLocationTask = SendDriverLocationTask.getInstance((activity as MainActivity));
            driverLocationTask.setOnDriverLocationUpdateListener(this)

            //we start the task specified in the MainActivity
            (activity as MainActivity).startSendDriverLocationTask();
        }
    }

    private fun setupRecyclerViews(){
        binding.rvUntakenOrders.apply {
            adapter = UntakenOrderAdapter(mainViewModel.freeOrders.value!!, this@DriverMainFragment)
            layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.VERTICAL }
        }
    }

    private fun setupObservers(){
        mainViewModel.freeOrders.observe(viewLifecycleOwner, {
            (binding.rvUntakenOrders.adapter as UntakenOrderAdapter).updateData(it)
        })
    }

    private fun setupDriverDetails(){
        binding.driverWelcomeLabel.text = "Bienvenido, ${Auth.currentUser.username}. " +
                "Estas son las ordenes disponibles"
    }

    override fun onResume() {
        super.onResume()
        Auth.clearCompleteOrderData();
    }

    override fun onUntakenOrderClick(order: Order) {

        var bundle = Bundle();
        bundle.putInt("orderId", order.id);
        findNavController().navigate(R.id.action_driverTabFragment_to_driverFullOrderFragment, bundle);

    }

    override fun onDriverLocationUpdate(location: Location) {
        DriverRepository.sendDriverLocation(Auth.access_token,
            DriverLocation(location.latitude.toString(), location.longitude.toString()), onSuccess = {
                Log.i("DRIVER_LOCATION_UPDATES", "Driver location sent")
            }, onError = {
                Log.e("DRIVER_LOCATION_UPDATES", "Error sending driver location")
            })
    }
}