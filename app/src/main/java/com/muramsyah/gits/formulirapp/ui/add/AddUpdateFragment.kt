package com.muramsyah.gits.formulirapp.ui.add

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.muramsyah.gits.formulirapp.R
import com.muramsyah.gits.formulirapp.data.Resource
import com.muramsyah.gits.formulirapp.databinding.FragmentAddUpdateBinding
import com.muramsyah.gits.formulirapp.domain.model.Formulir
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@AndroidEntryPoint
class AddUpdateFragment : Fragment() {

    private val viewModel: AddUpdateViewModel by viewModels()

    private val setPicture = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val resultCode = it.resultCode
        val data = it.data

        when (resultCode) {
            Activity.RESULT_OK -> {
                val image = data?.data!!
                binding.imgUser.setImageURI(image)
                initiateFile(image)
            }
            ImagePicker.RESULT_ERROR ->{ Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show() }
            else -> Toast.makeText(context, "Gagal mengubah gambar", Toast.LENGTH_SHORT).show()
        }
    }

    private val TAG = AddUpdateFragment::class.java.simpleName
    private var _binding: FragmentAddUpdateBinding? = null
    private val binding get() = _binding!!

    private var imageName = ""
    private var file: File? = null
    private lateinit var dataFormulir: Formulir

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Melakukan setup toolbar
         */
        val reqActivity = requireActivity() as AppCompatActivity
        reqActivity.setSupportActionBar(binding.toolbar)

        if (arguments != null) {
            dataFormulir = arguments?.getParcelable(EXTRA_DATA)!!
            reqActivity.title = "Edit ${dataFormulir.nama}"

            with(binding) {
                btnSimpan.text = context?.getString(R.string.update)
                edtAlamat.setText(dataFormulir.alamat)
                edtNama.setText(dataFormulir.nama)
                edtEmail.setText(dataFormulir.email)

                Glide.with(requireContext()).load("http://192.168.1.8/todoAPI/gambar/${dataFormulir.image}").into(binding.imgUser)

                btnSimpan.setOnClickListener { doAction(true) }
                btnDelete.setOnClickListener { observerDeletePengguna() }
            }
        } else {
            reqActivity.title = "Tambah data pengguna"
            binding.btnDelete.visibility = View.GONE
            binding.btnSimpan.setOnClickListener { doAction(false) }
        }

        binding.fabEditImage.setOnClickListener {
            ImagePicker.with(requireActivity())
                .crop()
                .compress(1024)
                .createIntent { intent ->
                    setPicture.launch(intent)
                }
        }
    }

    private fun initiateFile(uri: Uri) {
        file = File(uri.path!!)
        imageName = file!!.name
    }

    private fun doAction(isUpdate: Boolean) {
        val nama     = binding.edtNama.text.toString().trim()
        val alamat   = binding.edtAlamat.text.toString().trim()
        val email    = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

        var isError = false

        if (TextUtils.isEmpty(nama)) {
            binding.edtNama.error = "Isi nama terlebih dahulu!"
            isError = true
        }

        if (TextUtils.isEmpty(alamat)) {
            binding.edtAlamat.error = "Isi alamat terlebih dahulu"
            isError = true
        }

        if (TextUtils.isEmpty(email)) {
            binding.edtEmail.error = "Isi email terlebih dahulu"
            isError = true
        }

        if (TextUtils.isEmpty(password)) {
            binding.edtPassword.error = "Isi password terlebih dahulu"
            isError = true
        }

        /**
         * Memvalidasi form apakah masih ada error, jika tidak maka lakukan langkah didalam blok if
         */
        if (!isError) {
            lateinit var formulir: Formulir
            lateinit var mFile: RequestBody
            lateinit var imageBody: MultipartBody.Part
            val isFile: Boolean

            /**
             * Memvalidasi file pada fungsi initiateFile() untuk menentukan data file yang akan dikirim ke server
             */
            if (file != null) {
                mFile       = file!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                imageBody   = MultipartBody.Part.createFormData("file", imageName, mFile)
                formulir    = Formulir("0", nama, alamat, email, imageName, password)
                isFile      = true
            } else {
                formulir    = Formulir("0", nama, alamat, email, "user_default.png", password)
                isFile      = false
            }

            /**
             * Memvalidasi apakah form ini untuk update atau insert
             */
            if (isUpdate) {
                if (isFile) {
                    viewModel.uploadImage(imageBody).observe(viewLifecycleOwner, {
                        when (it) {
                            is Resource.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            is Resource.Success -> {
                                binding.progressBar.visibility = View.GONE
                                observeUpdatePengguna(formulir)
                            }
                            is Resource.Error -> {
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                } else {
                    formulir = Formulir("0", nama, alamat, email, dataFormulir.image, password)
                    observeUpdatePengguna(formulir)
                }

            } else {
                if (isFile) {
                    viewModel.uploadImage(imageBody).observe(viewLifecycleOwner, {
                        when (it) {
                            is Resource.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            is Resource.Success -> {
                                binding.progressBar.visibility = View.GONE
                                observeInsertPengguna(formulir)
                            }
                            is Resource.Error -> {
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                } else {
                    observeInsertPengguna(formulir)
                }
            }
        }
    }

    private fun observeInsertPengguna(formulir: Formulir) {
        viewModel.insertPengguna(formulir).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, "Berhasil menambahkan pengguna", Toast.LENGTH_SHORT).show()
                    binding.root.findNavController().popBackStack()
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun observeUpdatePengguna(formulir: Formulir) {
        viewModel.updatePengguna(dataFormulir.id.toInt(), formulir)
            .observe(viewLifecycleOwner, {
                when (it) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        binding.root.findNavController().popBackStack()
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            })
    }

    private fun observerDeletePengguna() {
        viewModel.deletePengguna(dataFormulir.id.toInt()).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    binding.root.findNavController().popBackStack()
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}