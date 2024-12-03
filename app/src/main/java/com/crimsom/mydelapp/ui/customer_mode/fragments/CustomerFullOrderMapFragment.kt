package com.crimsom.mydelapp.ui.customer_mode.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.crimsom.mydelapp.MainActivity
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.aux_interfaces.OnOrderCustomerConfirmationListener
import com.crimsom.mydelapp.aux_interfaces.OnOrderDetailsListener
import com.crimsom.mydelapp.aux_interfaces.OnOrderUpdateStatusListener
import com.crimsom.mydelapp.databinding.FragmentCustomerFullOrderMapBinding
import com.crimsom.mydelapp.repositories.OrderRepository
import com.crimsom.mydelapp.tasks.UpdateOrderStatusTask
import com.crimsom.mydelapp.ui.customer_mode.fragments.sub_fragments.CustomerCurrentOrderStatusFragment
import com.crimsom.mydelapp.ui.customer_mode.fragments.sub_fragments.CustomerMapFragment
import com.crimsom.mydelapp.ui.customer_mode.viewmodels.FullOrderMapViewModel
import com.crimsom.mydelapp.utilities.Auth
import com.crimsom.mydelapp.utilities.Constants
import com.crimsom.mydelapp.utilities.ProgressDialogBuilder
import com.crimsom.mydelapp.utilities.ShoppingCart
import com.google.android.gms.maps.model.LatLng


class CustomerFullOrderMapFragment : Fragment(), OnOrderCustomerConfirmationListener , OnOrderDetailsListener, OnOrderUpdateStatusListener{


    private lateinit var binding : FragmentCustomerFullOrderMapBinding;
    private var viewModel = FullOrderMapViewModel();

    private lateinit var updateOrderTask : UpdateOrderStatusTask;

    public var orderId : Int = 0;

    private lateinit var mapFragment : CustomerMapFragment;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.orderId = arguments?.getInt("orderId") ?: 0;
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomerFullOrderMapBinding.inflate(inflater, container, false)

        //we hide the fragment of status until we have the order data
        binding.fragmentContainerCustOrderStatus.visibility = View.GONE;

        setupObservers();

        if(this.orderId != 0){
            viewModel.getOrderData(Auth.access_token, this.orderId);
        }else{
            //it means we are just creating the order
            binding.fragmentContainerCustOrderStatus.visibility = View.VISIBLE;
        }

        updateOrderTask = UpdateOrderStatusTask.getInstance(requireActivity() as MainActivity)
        updateOrderTask.setOnOrderUpdateStatusListener(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFragmentsActions()

        mapFragment = (binding.fragmentContainerCustOrderMap.getFragment<CustomerMapFragment>())

    }

    private fun setupFragmentsActions(){

        val customerCurrentOrderStatusFragment = (binding.fragmentContainerCustOrderStatus.getFragment<CustomerCurrentOrderStatusFragment>())

        //set a listener
        customerCurrentOrderStatusFragment.setOnOrderDetailListener(this)
        //set up the current order status fragment actions
        if(orderId != 0){
            //iniciamos la task y configuramos el backButton
            updateOrderTask.startTask();
            this.setupBackButton();

            return;
        }
        customerCurrentOrderStatusFragment.setOnOrderCustomerConfirmationListener(this)
    }

    override fun onOrderConfirmation() {

        var progressDialog = ProgressDialogBuilder.startLoadingDialog(requireContext(),
            "Creando orden...",
            "Por favor espere"
        )

        var myOrder = ShoppingCart.createOrderFromCart(Auth.currentUserId, Auth.cust_selectedRestaurantId, "Pendiente", "Pendiente")
        myOrder.address = ShoppingCart.orderAddress; //We asign the address

        if(Auth.currentUserLatitude.isNotEmpty() && Auth.currentUserLongitude.isNotEmpty()){
            myOrder.latitude = Auth.currentUserLatitude
            myOrder.longitude = Auth.currentUserLongitude
        }
        Log.i("ORDER_DATA_PRE", "Order PRE_POST: $myOrder")

        OrderRepository.createOrder(Auth.access_token, myOrder, onSuccess = {

            //chao progress dialog
            progressDialog.dismiss()

            Log.i("ORDER_DATA", "Order created: $it")

            myOrder = it;

            //we kill the shopping cart
            ShoppingCart.reset();

            val bundle = Bundle()
            bundle.putInt("orderId", myOrder.id)
            findNavController().navigate(R.id.action_customerFullOrderMapFragment_self, bundle)

        }, onError = {
            Log.e("ORDER_DATA", "Error creating order: $it")
            progressDialog.dismiss()
        })

    }

    private fun loadOrderDataIntoSubFragments(){
        val customerCurrentOrderStatusFragment = (binding.fragmentContainerCustOrderStatus.getFragment<CustomerCurrentOrderStatusFragment>())
        customerCurrentOrderStatusFragment.setOrderStatus(viewModel.orderData.value!!.status)

        val mapFragment = binding.fragmentContainerCustOrderMap.getFragment<CustomerMapFragment>()

        val restaurant = viewModel.restaurantData.value!!
        val order = viewModel.orderData.value!!
        val driver = viewModel.orderData.value!!.driver;

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
        if(driver != null){
            var driverLocation = LatLng(
                driver.latitude.toDouble(),
                driver.longitude.toDouble()
            )
            mapFragment.setupMarkers(originLocation, restaurant.name, destinationLocation, order.address, driverLocation, driver.user.username)
        }else{
            mapFragment.setupOriginAndDestinyMarkers(originLocation, restaurant.name, destinationLocation, order.address)
        }
    }

    private fun setupObservers(){
        //first, this one will get triggered
        viewModel.orderData.observe(viewLifecycleOwner){
            Log.i("ORDER_DATA_IN_MAP", "Order data: $it")

            if(it.status == Constants.ORDER_STATUS_DELIVERED){
                findNavController().navigate(R.id.action_customerFullOrderMapFragment_to_orderCompletedFragment);
            }

            viewModel.getRestaurantData(Auth.access_token, it.restaurantId)
        }

        //and then, whn there is an order, there will be a fuckibng restaurant
        viewModel.restaurantData.observe(viewLifecycleOwner){
            Log.i("ORDER_DATA_IN_MAP", "Restaurant data: $it")
            loadOrderDataIntoSubFragments()

            //we show the fragment of status now that the fucking user won't cause bugs
            binding.fragmentContainerCustOrderStatus.visibility = View.VISIBLE;
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        updateOrderTask.stopTask()
    }

    override fun onGoToOrderDetailsByAction() {
        var bundle = Bundle()
        bundle.putInt("orderId", orderId)
        findNavController().navigate(R.id.action_customerFullOrderMapFragment_to_customerOrderDetailsFragmentFull, bundle)
    }

    override fun onGoToOrderDetailsById(orderId: Int) {
        println("No va a hacer nada xd")
    }

    override fun onOrderUpdateStatus() {
        Log.i("ORDER_UPDATE_STATUS", "Updating order status")
        //actualizamos el estado de la orden
        viewModel.getOrderData(Auth.access_token, this.orderId)
    }

    private fun setupBackButton(){
        //we set this behaviour due to the fact that the user shall not return to ShoppingCartFragment
        //and we stop the fucking task
        (this.requireActivity() as MainActivity).onBackPressedDispatcher.addCallback(this) {
            updateOrderTask.stopTask();
            findNavController().navigate(R.id.customerTabFragment);
        }
    }

}