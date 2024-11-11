package com.crimsom.mydelapp.ui.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.crimsom.mydelapp.FakeDB
import com.crimsom.mydelapp.MainActivity
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.databinding.FragmentLoginBinding
import com.crimsom.mydelapp.models.User

class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        setupButtons()

        return binding.root
    }

    private fun setupButtons(){
        binding.loginGoToRegisterLabel.setOnClickListener {
            var navController = findNavController()
            //to prevent backstack from getting too big
            navController.popBackStack(R.id.loginFragment, false)
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.loginButton.setOnClickListener {
            var email = binding.loginEmailField.text.toString()
            var password = binding.loginPasswordField.text.toString()

            var currentUser : User? = FakeDB.login(email, password)

            if(currentUser != null){
                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()

                MainActivity.currentUserId = currentUser.id;

                if(!FakeDB.isDriver(currentUser)){
                    this.goToCustomerMode();
                }else{
                    this.goToDriverMode()
                }
            }
        }

    }

    private fun goToCustomerMode(){
        var navController = findNavController()
        navController.navigate(R.id.action_loginFragment_to_customerTabFragment)
    }

    private fun goToDriverMode(){
        var navController = findNavController()
        navController.navigate(R.id.action_loginFragment_to_driverMainFragment)
    }
}