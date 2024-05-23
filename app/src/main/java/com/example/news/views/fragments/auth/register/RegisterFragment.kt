package com.example.news.views.fragments.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.news.databinding.FragmentRegisterBinding
import com.example.news.utilities.gone
import com.example.news.utilities.visible
import com.example.news.viewmodels.AuthViewModel
import com.example.news.views.fragments.auth.login.LoginFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding: FragmentRegisterBinding get() =  _binding!!
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()

        binding.buttonRegister.setOnClickListener {

            registerUser()

        }

        binding.textview2.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }
    }

    private fun observeData() {
        viewModel.isLogin.observe(viewLifecycleOwner) {
            if (it) {

                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) binding.progressBar3.visible() else binding.progressBar3.gone()
        }
    }

    private fun registerUser() {

        lifecycleScope.launch(Dispatchers.Main) {

            val email = binding.textInputEditText.text.toString().trim()
            val password = binding.textInputEditText2.text.toString().trim()
            val password2 = binding.textInputEditText3.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty() && password2.isNotEmpty()) {

                if (password == password2) {
                    viewModel.register(email, password)
                } else {
                    Toast.makeText(context, "Password is false", Toast.LENGTH_LONG).show()
                }


            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
