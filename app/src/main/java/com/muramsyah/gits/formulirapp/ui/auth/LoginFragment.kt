package com.muramsyah.gits.formulirapp.ui.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.muramsyah.gits.formulirapp.R
import com.muramsyah.gits.formulirapp.data.Resource
import com.muramsyah.gits.formulirapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

@SuppressLint("HardwareIds")
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private var session: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        executor = ContextCompat.getMainExecutor(context)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(context, "Authentication Error: $errString", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    val deviceId = Settings.Secure.getString(context?.contentResolver, Settings.Secure.ANDROID_ID)
                    loginAuth(deviceId)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(context, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Login with fingerprint")
            .setSubtitle("Please tap your finger!")
            .setNegativeButtonText("Use Account password")
            .build()

        session = viewModel.loginSession

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
                                viewModel.setUserId(it.data?.id!!)

                                lifecycleScope.launch {
                                    updateDeviceId(it.data.id)
                                }

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

            binding.btnLoginAuth.setOnClickListener {
                biometricPrompt.authenticate(promptInfo)
            }
        }
    }

    private fun updateDeviceId(userId: String) {
        val deviceId = Settings.Secure.getString(context?.contentResolver, Settings.Secure.ANDROID_ID)
        viewModel.updateDeviceId(deviceId, userId).observe(viewLifecycleOwner, {
            when(it) {
                is Resource.Success -> {
                    Log.d("LoginFragment", it.data.toString())
                }
                is Resource.Error -> {
                    Log.d("LoginFragment", it.data.toString())
                }
            }
        })
    }

    private fun loginAuth(deviceId: String) {
        viewModel.loginAuth(deviceId).observe(viewLifecycleOwner, {
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
                    Snackbar.make(binding.root, "Login terlebih dahulu!", Snackbar.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}