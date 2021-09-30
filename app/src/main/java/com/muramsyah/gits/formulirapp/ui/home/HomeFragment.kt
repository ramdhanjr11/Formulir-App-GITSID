package com.muramsyah.gits.formulirapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.muramsyah.gits.formulirapp.R
import com.muramsyah.gits.formulirapp.adapter.HomeAdapter
import com.muramsyah.gits.formulirapp.data.Resource
import com.muramsyah.gits.formulirapp.databinding.FragmentHomeBinding
import com.muramsyah.gits.formulirapp.ui.add.AddUpdateFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val  binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        binding.fabTambahFormulir.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_addUpdateFragment)
        }

        binding.swipeRefresh.setOnRefreshListener {
            initViewModel()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun initViewModel() {
        viewModel.allPengguna.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> { binding.progressBar.visibility = View.VISIBLE }
                is Resource.Success -> {
                    val adapter = HomeAdapter(it.data!!)
                    binding.progressBar.visibility = View.GONE
                    binding.rvFormulir.adapter = adapter
                    binding.rvFormulir.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
                    binding.rvFormulir.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    binding.rvFormulir.setHasFixedSize(true)
                    adapter.onItemClicked = {
                        val bundle = Bundle().apply { putParcelable(AddUpdateFragment.EXTRA_DATA, it) }
                        binding.root.findNavController().navigate(R.id.action_homeFragment_to_addUpdateFragment, bundle)
                    }
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}