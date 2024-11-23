package com.crimsom.mydelapp.ui.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.crimsom.mydelapp.MainActivity
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.aux_interfaces.OnCurrentOrderItemListener
import com.crimsom.mydelapp.aux_interfaces.OnOrderDetailsListener
import com.crimsom.mydelapp.databinding.FragmentProfileBinding
import com.crimsom.mydelapp.tasks.SendDriverLocationTask
import com.crimsom.mydelapp.ui.common.viewmodels.ProfileViewModel
import com.crimsom.mydelapp.ui.customer_mode.adapters.HistoryOrderAdapter
import com.crimsom.mydelapp.utilities.Auth


class ProfileFragment : Fragment(), OnOrderDetailsListener, OnCurrentOrderItemListener {

    private lateinit var binding : FragmentProfileBinding;
    private var profileViewModel = ProfileViewModel();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        profileViewModel.getCurrentUserFromAuth();
        profileViewModel.getOrdersHistory(Auth.access_token);

        this.setupUserDetails();
        this.setupRecyclerView();
        this.setupObservers()

        binding.custLogoutButton.setOnClickListener{
            this.logout();
        }
        return binding.root
    }

    private fun setupUserDetails(){

        binding.custProfilePic.setImageResource(R.drawable.person);

        val user = profileViewModel.user.value;
        binding.custEmailLabel.text = user!!.email

        binding.custTypeUserLabel.text =  if(Auth.IS_CURRENT_USER_DRIVER) "Conductor" else "Cliente"

        if(Auth.IS_CURRENT_USER_DRIVER){
            binding.custHistoryLabel.visibility = View.GONE;
            binding.rvOrderHistory.visibility = View.GONE;
        }
    }

    override fun onResume() {
        super.onResume()

        //clear recycler view cache
        binding.rvOrderHistory.recycledViewPool.clear()
    }

    private fun setupRecyclerView(){
        binding.rvOrderHistory.apply {
            adapter = HistoryOrderAdapter(profileViewModel.orders.value!!, this@ProfileFragment, this@ProfileFragment)
            layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.VERTICAL }
        }
    }

    private fun setupObservers(){
        profileViewModel.user.observe(viewLifecycleOwner) {
            this.setupUserDetails();
        }

        profileViewModel.orders.observe(viewLifecycleOwner) {
            (binding.rvOrderHistory.adapter as HistoryOrderAdapter).updateData(it)
        }
    }

    private fun logout(){

        if(Auth.IS_CURRENT_USER_DRIVER){
            //if the user is a driver, we stop the task of sending the location to the server at logout
            SendDriverLocationTask.getInstance(requireActivity() as MainActivity).stopTask();
        }

        Auth.clearUserSession();
        findNavController().navigate(R.id.action_logout);
    }

    override fun onGoToOrderDetailsByAction() {
        println("Aca este papou no hace nada")
    }

    //si la order esta inactiva en el historial, pal detalle
    override fun onGoToOrderDetailsById(orderId: Int) {
        val bundle = Bundle()
        bundle.putInt("orderId", orderId)
        findNavController().navigate(R.id.action_customerTabFragment_to_customerOrderDetailsFragment, bundle)
    }

    //si la orden esta activa
    override fun onCurrentOrderItemClick(orderId: Int) {
        var bundle = Bundle()
        bundle.putInt("orderId", orderId)
        findNavController().navigate(R.id.action_customerProfileFragment_to_customerFullOrderMapFragment, bundle)
    }
}