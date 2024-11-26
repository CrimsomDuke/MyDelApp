package com.crimsom.mydelapp.ui.driver_mode.fragments.sub_fragments

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.aux_interfaces.OnDriverTakesOrderListener
import com.crimsom.mydelapp.databinding.FragmentDriverStatusBinding
import com.crimsom.mydelapp.models.Order
import com.crimsom.mydelapp.repositories.MediaRepository
import com.crimsom.mydelapp.repositories.OrderRepository
import com.crimsom.mydelapp.ui.driver_mode.fragments.DriverFullOrderFragment
import com.crimsom.mydelapp.utilities.Auth
import com.crimsom.mydelapp.utilities.CameraUtil
import com.crimsom.mydelapp.utilities.Constants


class DriverStatusFragment : Fragment() {

    private lateinit var binding : FragmentDriverStatusBinding;
    private lateinit var onDriverTakesOrderListener : OnDriverTakesOrderListener;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDriverStatusBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //We gave actions to the buttons
        setupFragmentsActions();
    }

    private fun setupFragmentsActions(){
        binding.driverOrdAcceptButton.setOnClickListener {
            onDriverTakesOrderListener.onDriverTakesOrder();
            binding.driverOrdConfirmationLayout.visibility = View.GONE;
            this.changeStatusUI(Constants.ORDER_STATUS_ACCEPTED);
        }

        binding.driverOrdCancelButton.setOnClickListener {
            findNavController().popBackStack();
        }

        binding.driverOrdNextStatusButton.setOnClickListener {
            var status = this@DriverStatusFragment.getOrderStatusFromParentFragment();

            //if the driver is on the way, the next status is arrived
            if(status == Constants.ORDER_STATUS_ACCEPTED){
                onDriverTakesOrderListener.onDriverIsOnTheWay()
                this.changeStatusUI(status);
            }
            //if the driver is on the way, the next status is arrived
            if(status == Constants.ORDER_STATUS_ON_WAY){
                onDriverTakesOrderListener.onDriverArrived()
            }

            //por si hay algun error, mandar al driver a la pantalla principal
            if(status > 4 && status < 0){
                //open camera
                findNavController().navigate(R.id.driverTabFragment);
            }

        }
    }

    public fun loadDataFromOrder(order : Order) {
        if(order.status != Constants.ORDER_STATUS_REQUESTED){
            binding.driverOrdConfirmationLayout.visibility = View.GONE;
            changeStatusUI(order.status);
        }
    }

    private fun changeStatusUI(status : Int){
        binding.driverOrdStatusLayout.visibility = View.VISIBLE;

        //toma el siguiente estado al actual
        binding.driverOrdNextStatusButton.text = Auth.getOrderStatusDescription(status + 1);
    }

    public fun setOnDriverTakesOrderListener(listener : OnDriverTakesOrderListener){
        this.onDriverTakesOrderListener = listener;
    }

    public fun getOrderStatusFromParentFragment() : Int{
        val parentFragment = parentFragment as DriverFullOrderFragment;
        return parentFragment.currenOrderStatus;
    }
}