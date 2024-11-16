package com.crimsom.mydelapp.ui.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.crimsom.mydelapp.FakeDB
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.databinding.FragmentRegisterBinding
import com.crimsom.mydelapp.models.User

class RegisterFragment : Fragment() {

    private lateinit var binding : FragmentRegisterBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        this.setupButtons();

        return binding.root
    }

    private fun setupButtons(){
        binding.registerGoToLoginLabel.setOnClickListener {
            var navController = findNavController()
            //get back to login fragment with animation
            navController.popBackStack(R.id.loginFragment, false)
        }

        binding.registerButton.setOnClickListener{
            var username = binding.registerUsernameField.text.toString()
            var email = binding.registerEmailField.text.toString()
            var password = binding.registerPasswordField.text.toString()

            if(username.length < 4) binding.registerUsernameInputLayout.apply {
                error = "Usuario debe tener al menos 4 letras";
                return@setOnClickListener;
            }

            if(email.length < 4) binding.registerEmailInputLayout.apply {
                error = "Email debe tener al menos 4 letras";
                return@setOnClickListener;
            }

            if(password.length < 4) binding.registerPasswordInputLayout.apply {
                error = "Contraseña debe tener al menos 4 letras";
                return@setOnClickListener;
            }

            var userType = if(binding.registerIsDriverCheckbox.isChecked) 2 else 1
            val registerUser = User(0, username, email, password, userType)

            FakeDB.register(registerUser)

            Toast.makeText(context, "User registered", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack(R.id.loginFragment, false)
        }
    }
}