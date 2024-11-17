package com.crimsom.mydelapp.ui.driver_mode.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.databinding.FragmentDriverFullOrderBinding

class DriverFullOrderFragment : Fragment() {

    private lateinit var binding : FragmentDriverFullOrderBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDriverFullOrderBinding.inflate(inflater, container, false)

        return binding.root;
    }
}