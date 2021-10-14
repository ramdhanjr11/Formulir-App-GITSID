package com.muramsyah.gits.formulirapp.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.muramsyah.gits.formulirapp.R
import com.muramsyah.gits.formulirapp.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels()

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("Dashboard", viewModel.userId)

        binding.btnToPengguna.setOnClickListener {
            it.findNavController().navigate(R.id.action_dashboardFragment_to_homeFragment)
        }

        binding.btnToSendNotification.setOnClickListener {
            it.findNavController().navigate(R.id.action_dashboardFragment_to_sendNotificationFragment)
        }

        binding.ibLogout.setOnClickListener {
            viewModel.setSession(!viewModel.loginSession)
            viewModel.setUserId("")
            it.findNavController().navigate(R.id.action_dashboardFragment_to_loginFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}