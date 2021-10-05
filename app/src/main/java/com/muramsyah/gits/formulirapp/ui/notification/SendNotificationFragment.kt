package com.muramsyah.gits.formulirapp.ui.notification

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.muramsyah.gits.formulirapp.data.Resource
import com.muramsyah.gits.formulirapp.databinding.FragmentSendNotificationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SendNotificationFragment : Fragment() {

    private val viewModel: SendNotificationViewModel by viewModels()

    private var _binding: FragmentSendNotificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSendNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnKirim.setOnClickListener {
            val title   = binding.edtTitle.text.toString().trim()
            val message = binding.edtMessage.text.toString().trim()
            var isError = false

            if (TextUtils.isEmpty(title)) {
                binding.edtTitle.error = "Isi title terlebih dahulu"
                isError = true
            }

            if (TextUtils.isEmpty(message)) {
                binding.edtMessage.error = "Isi message terlebih dahulu"
                isError = true
            }

            if (!isError) {
                viewModel.sendNotif(title, message).observe(viewLifecycleOwner, {
                    when (it) {
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        }
                    }
                })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}