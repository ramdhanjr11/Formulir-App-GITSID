package com.muramsyah.gits.formulirapp.ui.auth

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.muramsyah.gits.formulirapp.R
import com.muramsyah.gits.formulirapp.data.Resource
import com.muramsyah.gits.formulirapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val session = viewModel.loginSession

        if (session) {
            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
        } else {
            binding.btnLogin.setOnClickListener {
                val email     = binding.edtEmail.text.toString().trim()
                val password  = binding.edtPassword.text.toString().trim()
                var isBoolean = false

                if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.edtEmail.error = "Masukan email terlebih dahulu"
                    isBoolean = true
                }

                if (TextUtils.isEmpty(password)) {
                    binding.edtPassword.error = "Masukan password terlebih dahulu"
                    isBoolean = true
                }

                if (!isBoolean) {
                    viewModel.login(email, password).observe(viewLifecycleOwner, {
                        when (it) {
                            is Resource.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            is Resource.Success -> {
                                binding.progressBar.visibility = View.GONE
                                Snackbar.make(binding.root, "Login Sukses", Snackbar.LENGTH_SHORT).show()
                                viewModel.setSession(!session)
                                findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                            }
                            is Resource.Error -> {
                                binding.progressBar.visibility = View.GONE
                                Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_SHORT).show()
                            }
                        }
                    })
                }
            }

            binding.tvBuatAkun.setOnClickListener {
                Snackbar.make(binding.root, "Fitur mendatang", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}