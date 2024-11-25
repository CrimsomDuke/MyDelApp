package com.crimsom.mydelapp.ui.customer_mode.fragments.sub_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.crimsom.mydelapp.aux_interfaces.OnOrderCustomerConfirmationListener
import com.crimsom.mydelapp.aux_interfaces.OnOrderDetailsListener
import com.crimsom.mydelapp.databinding.FragmentCustomerCurrentOrderStatusBinding
import com.crimsom.mydelapp.utilities.Auth

class CustomerCurrentOrderStatusFragment : Fragment() {

    private lateinit var binding: FragmentCustomerCurrentOrderStatusBinding;

    private lateinit var onOrderCustomerConfirmationListener : OnOrderCustomerConfirmationListener;
    private lateinit var onOrderDetailListener: OnOrderDetailsListener;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomerCurrentOrderStatusBinding.inflate(inflater, container, false)

        this.setupConfirmOrderLayout();
        this.setupButtonsActions();

        return binding.root
    }

    private fun setupConfirmOrderLayout(){
        binding.custOrdConfirmOrderButton.setOnClickListener {
            onOrderCustomerConfirmationListener.onOrderConfirmation();
        }
    }

    //to call in upper fragment
    public fun setOnOrderCustomerConfirmationListener(listener : OnOrderCustomerConfirmationListener){
        this.onOrderCustomerConfirmationListener = listener;
    }

    public fun setOnOrderDetailListener(listener : OnOrderDetailsListener){
        this.onOrderDetailListener = listener;
    }

    public fun setOrderStatus(status : Int){
        binding.custOrdConfirmOrderLayout.visibility = View.GONE;
        binding.custOrdOrderStatusLayout.visibility = View.VISIBLE;
        binding.custOrdOrderStatusLabel.text = Auth.getOrderStatusDescription(status);
    }

    private fun setupButtonsActions(){
        binding.custOrdGoToOrderDetailsButton.setOnClickListener {
            onOrderDetailListener.onGoToOrderDetailsByAction();
        }
    }
}