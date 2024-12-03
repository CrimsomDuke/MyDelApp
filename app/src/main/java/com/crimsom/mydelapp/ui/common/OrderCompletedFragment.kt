package com.crimsom.mydelapp.ui.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.crimsom.mydelapp.MainActivity
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.databinding.FragmentOrderCompletedBinding
import com.crimsom.mydelapp.utilities.Auth

class OrderCompletedFragment : Fragment() {

    private lateinit var binding : FragmentOrderCompletedBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderCompletedBinding.inflate(inflater, container, false)

        setupButtons();
        this.setupBackButton();

        return binding.root
    }

    private fun setupButtons() {
        binding.finishedButton.setOnClickListener {
            if(Auth.IS_CURRENT_USER_DRIVER){
                findNavController().navigate(R.id.driverTabFragment)
            } else {
                findNavController().navigate(R.id.customerTabFragment)
            }
        }
    }
    private fun setupBackButton(){
        (this.requireActivity() as MainActivity).onBackPressedDispatcher.addCallback(this) {
            if(Auth.IS_CURRENT_USER_DRIVER){
                findNavController().navigate(R.id.driverTabFragment)
            } else {
                findNavController().navigate(R.id.customerTabFragment)
            }
        }
    }
}