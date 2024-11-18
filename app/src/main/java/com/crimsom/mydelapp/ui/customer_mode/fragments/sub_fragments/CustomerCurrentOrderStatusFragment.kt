package com.crimsom.mydelapp.ui.customer_mode.fragments.sub_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.crimsom.mydelapp.aux_interfaces.OnOrderConfirmationListener
import com.crimsom.mydelapp.databinding.FragmentCustomerCurrentOrderStatusBinding

class CustomerCurrentOrderStatusFragment : Fragment() {

    private lateinit var binding: FragmentCustomerCurrentOrderStatusBinding;

    private lateinit var OnOrderConfirmationListener : OnOrderConfirmationListener;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomerCurrentOrderStatusBinding.inflate(inflater, container, false)

        this.setupConfirmOrderLayout();

        return binding.root
    }

    private fun setupConfirmOrderLayout(){
        binding.custOrdConfirmOrderButton.setOnClickListener {
            OnOrderConfirmationListener.onOrderConfirmation();
        }
    }

    //to call in upper fragment
    public fun setOnOrderConfirmationListener(listener : OnOrderConfirmationListener){
        this.OnOrderConfirmationListener = listener;
    }
}