package com.muramsyah.gits.formulirapp.domain.usecase

import com.muramsyah.gits.formulirapp.data.Resource
import com.muramsyah.gits.formulirapp.domain.model.Formulir
import com.muramsyah.gits.formulirapp.domain.repository.IFormulirRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class FormulirInteractor @Inject constructor(private val formulirRepository: IFormulirRepository) : FormulirUseCase {

    override fun getAllPengguna(): Flow<Resource<List<Formulir>>> = formulirRepository.getAllPengguna()

    override fun uploadImage(file: MultipartBody.Part): Flow<Resource<String>> = formulirRepository.uploadImage(file)

    override fun insertPengguna(data: Formulir): Flow<Resource<String>> = formulirRepository.insertPengguna(data)

    override fun updatePengguna(id: Int, data: Formulir): Flow<Resource<String>> = formulirRepository.updatePengguna(id, data)

    override fun deletePengguna(id: Int): Flow<Resource<String>> = formulirRepository.deletePengguna(id)

    override fun login(email: String, password: String): Flow<Resource<Formulir>> = formulirRepository.login(email, password)

    override fun sendNotif(title: String, message: String): Flow<Resource<String>> = formulirRepository.sendNotif(title, message)

    override fun loginAuth(deviceId: String): Flow<Resource<String>> = formulirRepository.loginAuth(deviceId)

    override fun updateDeviceId(deviceId: String, userId: String): Flow<Resource<String>> = formulirRepository.updateDeviceId(deviceId, userId)
}