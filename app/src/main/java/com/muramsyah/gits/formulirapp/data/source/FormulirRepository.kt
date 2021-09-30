package com.muramsyah.gits.formulirapp.data.source

import android.util.Log
import com.muramsyah.gits.formulirapp.data.Resource
import com.muramsyah.gits.formulirapp.data.source.remote.RemoteDataSource
import com.muramsyah.gits.formulirapp.data.source.remote.network.ApiResponse
import com.muramsyah.gits.formulirapp.domain.model.Formulir
import com.muramsyah.gits.formulirapp.domain.repository.IFormulirRepository
import com.muramsyah.gits.formulirapp.utils.MappingHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class FormulirRepository @Inject constructor(private val remoteDataSource: RemoteDataSource): IFormulirRepository {

    private val TAG = FormulirRepository::class.java.simpleName

    override fun getAllPengguna(): Flow<Resource<List<Formulir>>> {

        return flow<Resource<List<Formulir>>> {
            emit(Resource.Loading(null))
            when (val apiResponse = remoteDataSource.getAllPengguna().first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(MappingHelper.entitiesToDomain(apiResponse.data!!)))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.message!!))
                }
                is ApiResponse.Empty -> {
                    Log.d(TAG, "getAllPengguna: Empty Data")
                }
            }
        }

    }

    override fun uploadImage(file: MultipartBody.Part): Flow<Resource<String>> {

        return flow<Resource<String>> {
            emit(Resource.Loading(null))
            when (val apiResponse = remoteDataSource.uploadImage(file).first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success("Sukses mengupload gambar"))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.message!!))
                }
                is ApiResponse.Empty -> {
                    Log.d(TAG, "uploadImage: Tidak ada gambar")
                }
            }
        }

    }

    override fun insertPengguna(data: Formulir): Flow<Resource<String>> {
        val entity = MappingHelper.domainToEntity(data)

        return flow<Resource<String>> {
            emit(Resource.Loading(null))
            when (val apiResponse = remoteDataSource.insertPengguna(entity).first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(null,"Sukses memasukan data pengguna"))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.message!!))
                }
                is ApiResponse.Empty -> {
                    Log.d(TAG, "insertPengguna: formulir kosong")
                }
            }
        }

    }

    override fun updatePengguna(id: Int, data: Formulir): Flow<Resource<String>> {
        val entity = MappingHelper.domainToEntity(data)

        return flow <Resource<String>>{
            emit(Resource.Loading(null))
            when (val apiResponse = remoteDataSource.updatePengguna(id, entity).first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(null, "Sukses mengupdate data ${data.nama}"))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.message!!))
                }
                is ApiResponse.Empty -> {
                    Log.d(TAG, "updatePengguna: update kosong")
                }
            }
        }
    }

    override fun deletePengguna(id: Int): Flow<Resource<String>> {
        return flow<Resource<String>>{
            emit(Resource.Loading(null))
            when(val apiResponse = remoteDataSource.deletePengguna(id).first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(null, "Sukses menghapus data $id"))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.message!!))
                }
                is ApiResponse.Empty -> {
                    Log.d(TAG, "deletePengguna: delete kosong")
                }
            }
        }
    }

}
