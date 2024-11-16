package com.crimsom.mydelapp.ui.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.crimsom.mydelapp.FakeDB
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.databinding.FragmentProfileBinding
import com.crimsom.mydelapp.ui.common.viewmodels.ProfileViewModel
import com.crimsom.mydelapp.ui.customer_mode.adapters.HistoryOrderAdapter
import com.crimsom.mydelapp.utilities.Auth


class ProfileFragment : Fragment() {

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
            adapter = HistoryOrderAdapter(FakeDB.getOrdersByUserId(Auth.currentUserId))
            layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.VERTICAL }
        }
    }

    private fun setupObservers(){
        profileViewModel.user.observe(viewLifecycleOwner) {
            this.setupUserDetails();
        }
    }

    private fun logout(){
        Auth.clearUserSession();
        findNavController().navigate(R.id.action_logout);
    }
}