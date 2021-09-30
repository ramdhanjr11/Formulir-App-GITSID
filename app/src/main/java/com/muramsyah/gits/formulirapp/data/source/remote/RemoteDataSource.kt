package com.muramsyah.gits.formulirapp.data.source.remote

import android.util.Log
import com.muramsyah.gits.formulirapp.data.source.remote.network.ApiResponse
import com.muramsyah.gits.formulirapp.data.source.remote.network.ApiService
import com.muramsyah.gits.formulirapp.data.source.remote.response.Data
import com.muramsyah.gits.formulirapp.data.source.remote.response.ImageResponse
import com.muramsyah.gits.formulirapp.data.source.remote.response.InsertResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    private val TAG = RemoteDataSource::class.java.simpleName

    suspend fun getAllPengguna(): Flow<ApiResponse<List<Data>>> {

        return flow {
            try {
                val response = apiService.getAllPengguna()
                val data = response.data
                if (data != null && response.status == 200) {
                    emit(ApiResponse.Success(data))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message))
                Log.d(TAG, "getAllPengguna: ${e.message}")
            }
        }.flowOn(Dispatchers.IO)

    }

    suspend fun uploadImage(file: MultipartBody.Part): Flow<ApiResponse<ImageResponse>> {

        return flow {
            val response = apiService.uploadImage(file)
            when (response.status) {
                200 -> emit(ApiResponse.Success(null))
                400 -> emit(ApiResponse.Error("Gambar tidak ditemukan"))
                else -> emit(ApiResponse.Empty)
            }
        }

    }

    suspend fun insertPengguna(data: Data): Flow<ApiResponse<InsertResponse>> {

        return flow {
            val queries         = HashMap<String, String>()
            queries["nama"]     = data.nama
            queries["alamat"]   = data.alamat
            queries["email"]    = data.email
            queries["image"]    = data.image
            queries["password"] = data.password

            Log.d(TAG, "insertPengguna:" + queries["nama"] + queries["alamat"] + queries["email"] + queries["image"] + queries["password"])

            val response = apiService.insertPengguna(queries)
            when (response.status) {
                200 -> emit(ApiResponse.Success(response))
                400 -> emit(ApiResponse.Error("Parameter tidak sesuai"))
                404 -> emit(ApiResponse.Error("Terjadi Kesalahan"))
                else -> emit(ApiResponse.Empty)
            }
        }

    }

    suspend fun updatePengguna(id: Int, data: Data): Flow<ApiResponse<InsertResponse>> {

        return flow {
            val queries         = HashMap<String, String>()
            queries["nama"]     = data.nama
            queries["alamat"]   = data.alamat
            queries["email"]    = data.email
            queries["image"]    = data.image
            queries["password"] = data.password

            val response = apiService.updatePengguna(id, queries)
            when (response.status) {
                200 -> emit(ApiResponse.Success(response))
                400 -> emit(ApiResponse.Error("Masukan parameter id"))
                401 -> emit(ApiResponse.Error("Parameter tidak sesuai"))
                404 -> emit(ApiResponse.Error("Terjadi kesalahan"))
                else -> emit(ApiResponse.Empty)
            }
        }

    }

    suspend fun deletePengguna(id: Int): Flow<ApiResponse<ImageResponse>> {

        return flow {
            val response = apiService.deletePengguna(id)
            when(response.status) {
                200 -> emit(ApiResponse.Success(response))
                400 -> emit(ApiResponse.Error("Masukan parameter id"))
                404 -> emit(ApiResponse.Error("Terjadi kesalahan"))
                else -> emit(ApiResponse.Empty)
            }
        }

    }

}