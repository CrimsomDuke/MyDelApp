package com.crimsom.mydelapp.ui.customer_mode.fragments.sub_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.crimsom.mydelapp.databinding.FragmentCustomerOrderDetailsBinding
import com.crimsom.mydelapp.models.OrderDetail
import com.crimsom.mydelapp.ui.customer_mode.adapters.OrderDetailAdapter
import com.crimsom.mydelapp.ui.customer_mode.viewmodels.OrderDetailViewModel
import com.crimsom.mydelapp.utilities.Auth

class CustomerOrderDetailsFragment : Fragment() {

    private lateinit var binding : FragmentCustomerOrderDetailsBinding;
    private var viewModel = OrderDetailViewModel();

    private var orderId : Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.orderId = arguments?.getInt("orderId") ?: 0;
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomerOrderDetailsBinding.inflate(inflater, container, false)

        if(this.orderId != 0){
            viewModel.getOrderData(Auth.access_token, this.orderId);
        }


        this.setupObservers();
        this.setupData();
        this.setupAdapter();

        return binding.root
    }

    private fun setupAdapter(){
        binding.ordDetRvDetail.apply {

            layoutManager = LinearLayoutManager(context);

            val orderDetails = listOf<OrderDetail>();
            adapter = OrderDetailAdapter(orderDetails)
        }
    }

    private fun setupObservers(){
        viewModel.order.observe(viewLifecycleOwner) {
            viewModel.getRestaurantData(Auth.access_token, it.restaurantId);
            (binding.ordDetRvDetail.adapter as OrderDetailAdapter).updateData(it.orderDetails);
        }

        viewModel.restaurant.observe(viewLifecycleOwner) {
            binding.ordDetRestaurantLabel.text = it.name;
            this.setupData();
        }
    }

    private fun setupData(){
        binding.ordDetTotalLabel.text = "Bs${viewModel.order.value?.total}";
        binding.ordDetDateLabel.text = viewModel.order.value?.createdAt.toString();
        binding.ordDetRestaurantLabel.text = viewModel.restaurant.value?.name;

    }

}