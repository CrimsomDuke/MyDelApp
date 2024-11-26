package com.crimsom.mydelapp.ui.driver_mode.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Camera
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.crimsom.mydelapp.MainActivity
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.aux_interfaces.OnDriverTakesOrderListener
import com.crimsom.mydelapp.databinding.FragmentDriverFullOrderBinding
import com.crimsom.mydelapp.repositories.MediaRepository
import com.crimsom.mydelapp.repositories.OrderRepository
import com.crimsom.mydelapp.ui.driver_mode.fragments.sub_fragments.DriverMapFragment
import com.crimsom.mydelapp.ui.driver_mode.fragments.sub_fragments.DriverStatusFragment
import com.crimsom.mydelapp.ui.driver_mode.viewmodels.DriverFullOrderViewModel
import com.crimsom.mydelapp.utilities.Auth
import com.crimsom.mydelapp.utilities.CameraUtil
import com.crimsom.mydelapp.utilities.ProgressDialogBuilder
import com.google.android.gms.maps.model.LatLng

class DriverFullOrderFragment : Fragment(), OnDriverTakesOrderListener {

    lateinit var cameraLauncher: ActivityResultLauncher<Intent>

    private lateinit var binding : FragmentDriverFullOrderBinding;
    private var viewModel : DriverFullOrderViewModel = DriverFullOrderViewModel();

    private var orderId : Int = 0;

    public var currenOrderStatus : Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.orderId = arguments?.getInt("orderId") ?: 0;
        Log.i("DRIVER_FULL_ORDER","DriverFullOrderFragment: Order id: $orderId");

        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d("DriverFullOrderFragment", "Camera result: $result")
            if(result.resultCode == Activity.RESULT_OK){
                // Get the bitmap from the result
                val bitmap = CameraUtil.getBitmapFromActivityResult(result)

                try {
                    MediaRepository.sendDeliveryProof(
                        Auth.access_token,
                        orderId,
                        bitmap!!,
                        onSuccess = {
                            Log.i("DriverFullOrderFragment", "Proof sent")
                            findNavController().navigate(R.id.action_driverFullOrderFragment_to_orderCompletedFragment3)
                        }, onError = {
                            Log.e("DriverFullOrderFragment", "Error sending proof: $it")
                            Toast.makeText(context, "Error al enviar la prueba", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_driverFullOrderFragment_to_orderCompletedFragment3)
                        }
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDriverFullOrderBinding.inflate(inflater, container, false)

        setupObservers();

        binding.driverStatusFragmentContainer.visibility = View.GONE;

        if(this.orderId != 0){
            viewModel.getOrderData(Auth.access_token, this.orderId);
        }else{
            Log.e("DriverFullOrderFragment", "No order id was passed to the fragment");
            //it means we are just creating the order
            binding.driverStatusFragmentContainer.visibility = View.VISIBLE;
        }

        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //the fragment needs to be created before
        setupFragmentActions();
    }

    private fun setupFragmentActions(){
        val driverStatusFragment = (binding.driverStatusFragmentContainer.getFragment<DriverStatusFragment>())
        driverStatusFragment.setOnDriverTakesOrderListener(this);
    }

    private fun loadOrderDataIntoSubFragments(){
        val driverStatusFragment = (binding.driverStatusFragmentContainer.getFragment<DriverStatusFragment>())
        driverStatusFragment.loadDataFromOrder(viewModel.order.value!!);

        val mapFragment = (binding.driverMapFragmentContainer.getFragment<DriverMapFragment>())

        val order = viewModel.order.value!!;
        val restaurant = viewModel.restaurant.value!!;

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

        mapFragment.setupOriginAndDestinyMarkers(originLocation, restaurant.name, destinationLocation, "Actual");

    }

    override fun onDriverTakesOrder() {

        if(viewModel.order.value == null){
            Log.e("DriverFullOrderFragment", "No order data was found");
            return;
        }

        val progressDialog = ProgressDialogBuilder.startLoadingDialog(requireContext(), "Aceptando orden...", "Por favor espere");

        OrderRepository.acceptOrder(Auth.access_token, viewModel.order.value!!.id, onSuccess = {
            viewModel.order.value = it;
            progressDialog.dismiss();
        }, onError = {
            Log.e("DriverFullOrderFragment", "Error accepting order: $it")
            Toast.makeText(context, "Error al aceptar la orden", Toast.LENGTH_SHORT).show()
            progressDialog.dismiss();
            findNavController().popBackStack();
        })
    }

    override fun onDriverIsOnTheWay() {
        OrderRepository.onMyWayOrder(Auth.access_token, viewModel.order.value!!.id, onSuccess = {
            viewModel.order.value = it;
        }, onError = {
            Log.e("DriverFullOrderFragment", "Error on my way order: $it")
            Toast.makeText(context, "Error al aceptar la orden", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDriverArrived() {
        OrderRepository.deliveredOrder(Auth.access_token, viewModel.order.value!!.id, onSuccess = {
            viewModel.order.value = it;

            //we show the camera to take the proof
            CameraUtil.launchCamera(cameraLauncher);

        }, onError = {
            Log.e("DriverFullOrderFragment", "Error arrived order: $it")
            Toast.makeText(context, "Error al aceptar la orden", Toast.LENGTH_SHORT).show()
        })
    }

    private fun setupObservers(){
        viewModel.order.observe(viewLifecycleOwner) {
            //we save the order status
            currenOrderStatus = it.status;
            viewModel.getRestaurantData(Auth.access_token, it.restaurantId);
        }

        viewModel.restaurant.observe(viewLifecycleOwner) {
            //Once loaded the data we need, we update the sub fragments
            loadOrderDataIntoSubFragments();

            //we show the status fragment once the data is fetched, fucking users
            binding.driverStatusFragmentContainer.visibility = View.VISIBLE;
        }
    }
}