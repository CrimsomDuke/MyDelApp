package com.crimsom.mydelapp.ui.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.databinding.FragmentLoginBinding
import com.crimsom.mydelapp.models.aux_models.LoginRequest
import com.crimsom.mydelapp.repositories.UserRepository
import com.crimsom.mydelapp.utilities.Auth

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

            if(email.length < 4) binding.loginEmailInputLayout.apply {
                error = "Al menos 4 letras"
                return@setOnClickListener
            }

            if(password.length < 4) binding.loginPasswordInputLayout.apply {
                error = "Al menos 4 letras"
                return@setOnClickListener
            }

            var loginRequest = LoginRequest(email, password)
            UserRepository.login(loginRequest, onSuccess = {
                Auth.access_token = it.access_token;
                processToken(it.access_token)
            }, onError = {
                Toast.makeText(context, "Error al login papu", Toast.LENGTH_SHORT).show()
            })
        }

    }

    private fun processToken(token : String){

        UserRepository.getMe(token, onSuccess = {
            Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()


            //we set the values of the user in the Auth object
            Auth.currentUser = it;
            Auth.currentUserId = it.id;

            println("User id: ${it.id} con tipo ${it.tipoUsuario} y username ${it.username} y email ${it.email}")

            if(!Auth.isDriver(it)){
                Auth.IS_CURRENT_USER_DRIVER = false;
                this.goToCustomerMode();
            }else{
                Auth.IS_CURRENT_USER_DRIVER = true;
                this.goToDriverMode()
            }

        }, onError = {
            Toast.makeText(context, "Error al obtener usuario", Toast.LENGTH_SHORT).show()
        })
    }

    private fun goToCustomerMode(){
        var navController = findNavController()
        navController.navigate(R.id.action_loginFragment_to_customerTabFragment)
    }

    private fun goToDriverMode(){
        var navController = findNavController()
        navController.navigate(R.id.action_loginFragment_to_driverTabFragment);
    }
}