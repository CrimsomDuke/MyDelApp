package com.crimsom.mydelapp.ui.customer_mode.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.databinding.FragmentShoppingCartBinding
import com.crimsom.mydelapp.ui.customer_mode.adapters.ProductsInCartAdapter
import com.crimsom.mydelapp.utilities.ShoppingCart
import com.techiness.progressdialoglibrary.ProgressDialog

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
        this.setupButtons();

        return binding.root
    }

    private fun setupRecyclerViews(){
        binding.custRvShoppingCart.apply {
            adapter = ProductsInCartAdapter(ShoppingCart.getProductsInCartList())
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupButtons(){

        binding.custScTotalLabel.text = "Total a pagar: Bs${ShoppingCart.getTotalToPay()}"

        binding.custConfirmOrderButton.setOnClickListener {
            //redirect to order confirmation fragment
            findNavController().navigate(R.id.action_shoppingCartFragment_to_customerFullOrderMapFragment)
        }
    }

    private fun startLoadingDialog() : ProgressDialog{
        var progressDialog = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ProgressDialog(requireContext(), ProgressDialog.THEME_FOLLOW_SYSTEM)
        } else {
            ProgressDialog(requireContext())
        }

        with(progressDialog){
            setMessage("Espere un momento...")
            setTitle("Procesando pedido")
            show()
        }

        return progressDialog;
    }


}