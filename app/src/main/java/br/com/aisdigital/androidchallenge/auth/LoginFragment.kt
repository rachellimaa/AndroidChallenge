package br.com.aisdigital.androidchallenge.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import br.com.aisdigital.androidchallenge.databinding.FragmentLoginBinding
import br.com.aisdigital.androidchallenge.network.AuthApi
import br.com.aisdigital.androidchallenge.network.Resource
import br.com.aisdigital.androidchallenge.repository.AuthRepository
import br.com.aisdigital.androidchallenge.ui.BaseFragment


class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Login Failure", Toast.LENGTH_LONG).show()
                }
            }
        })

        binding.buttonLogin.setOnClickListener {
            val email = binding.nameText.text.toString().trim()
            val password = binding.emailText.text.toString().trim()

            viewModel.login(email, password)
        }
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        AuthRepository(remoteDataSource.buildApi(AuthApi::class.java))
}