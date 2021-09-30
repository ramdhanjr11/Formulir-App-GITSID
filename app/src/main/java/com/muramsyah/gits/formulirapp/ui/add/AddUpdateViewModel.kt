package com.muramsyah.gits.formulirapp.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.muramsyah.gits.formulirapp.domain.model.Formulir
import com.muramsyah.gits.formulirapp.domain.usecase.FormulirUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class AddUpdateViewModel @Inject constructor(val useCase: FormulirUseCase) : ViewModel(){

    fun uploadImage(file: MultipartBody.Part) = useCase.uploadImage(file).asLiveData()

    fun insertPengguna(data: Formulir) = useCase.insertPengguna(data).asLiveData()

    fun updatePengguna(id: Int, data: Formulir) = useCase.updatePengguna(id, data).asLiveData()

    fun deletePengguna(id: Int) = useCase.deletePengguna(id).asLiveData()
}