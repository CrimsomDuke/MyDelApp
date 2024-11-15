package com.crimsom.mydelapp.ui.customer_mode.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.databinding.FragmentShoppingCartBinding
import com.crimsom.mydelapp.ui.customer_mode.adapters.ProductsInCartAdapter
import com.crimsom.mydelapp.utilities.ShoppingCart

class ShoppingCartFragment : Fragment() {

    private lateinit var binding : FragmentShoppingCartBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentShoppingCartBinding.inflate(inflater, container, false)

        this.setupRecyclerViews();

        return binding.root
    }

    private fun setupRecyclerViews(){
        binding.custRvShoppingCart.apply {
            adapter = ProductsInCartAdapter(ShoppingCart.getProductsInCartList())
            layoutManager = LinearLayoutManager(context)
        }
    }
}