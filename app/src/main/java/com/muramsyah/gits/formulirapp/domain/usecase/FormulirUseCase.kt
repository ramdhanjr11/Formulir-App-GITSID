package com.muramsyah.gits.formulirapp.domain.usecase

import com.muramsyah.gits.formulirapp.data.Resource
import com.muramsyah.gits.formulirapp.domain.model.Formulir
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface FormulirUseCase {

    fun getAllPengguna() : Flow<Resource<List<Formulir>>>

    fun uploadImage(file: MultipartBody.Part) : Flow<Resource<String>>

    fun insertPengguna(data: Formulir) : Flow<Resource<String>>

    fun updatePengguna(id: Int, data: Formulir) : Flow<Resource<String>>

    fun deletePengguna(id: Int) : Flow<Resource<String>>

    fun login(email: String, password: String) : Flow<Resource<String>>

    fun sendNotif(title: String, message: String) : Flow<Resource<String>>

}