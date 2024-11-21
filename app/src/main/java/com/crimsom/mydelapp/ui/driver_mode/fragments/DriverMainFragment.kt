package com.crimsom.mydelapp.ui.driver_mode.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.crimsom.mydelapp.FakeDB
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.aux_interfaces.OnUntakenOrderClickListener
import com.crimsom.mydelapp.databinding.FragmentDriverMainBinding
import com.crimsom.mydelapp.models.Order
import com.crimsom.mydelapp.repositories.RestaurantRepository
import com.crimsom.mydelapp.ui.driver_mode.adapters.UntakenOrderAdapter
import com.crimsom.mydelapp.ui.driver_mode.viewmodels.DriverMainViewModel
import com.crimsom.mydelapp.utilities.Auth
import com.techiness.progressdialoglibrary.ProgressDialog


class DriverMainFragment : Fragment(), OnUntakenOrderClickListener {

    private lateinit var binding : FragmentDriverMainBinding;
    private val mainViewModel  = DriverMainViewModel();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDriverMainBinding.inflate(inflater, container, false)

        mainViewModel.getFreeOrders(Auth.access_token)

        this.setupDriverDetails();
        this.setupRecyclerViews();
        this.setupObservers()

        return binding.root
    }

    private fun setupRecyclerViews(){
        binding.rvUntakenOrders.apply {
            adapter = UntakenOrderAdapter(mainViewModel.freeOrders.value!!, this@DriverMainFragment)
            layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.VERTICAL }
        }
    }

    private fun setupObservers(){
        mainViewModel.freeOrders.observe(viewLifecycleOwner, {
            (binding.rvUntakenOrders.adapter as UntakenOrderAdapter).updateData(it)
        })
    }

    private fun setupDriverDetails(){
        binding.driverWelcomeLabel.text = "Bienvenido, ${Auth.currentUser.username}. " +
                "Estas son las ordenes disponibles"
    }

    override fun onResume() {
        super.onResume()
        Auth.clearCompleteOrderData();
    }

    override fun onUntakenOrderClick(order: Order) {

        var bundle = Bundle();
        bundle.putInt("orderId", order.id);
        findNavController().navigate(R.id.action_driverTabFragment_to_driverFullOrderFragment, bundle);

    }

    private fun startLoadingDialog() : ProgressDialog {
        var progressDialog = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ProgressDialog(requireContext(), ProgressDialog.THEME_FOLLOW_SYSTEM)
        } else {
            ProgressDialog(requireContext())
        }

        with(progressDialog){
            setMessage("Espere un momento...")
            setTitle("Obteniendo información de la orden")
            show()
        }

        return progressDialog;
    }
}