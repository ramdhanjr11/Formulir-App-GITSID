package com.muramsyah.gits.formulirapp.data.source.remote.network

import com.muramsyah.gits.formulirapp.data.source.remote.response.FormulirResponse
import com.muramsyah.gits.formulirapp.data.source.remote.response.ImageResponse
import com.muramsyah.gits.formulirapp.data.source.remote.response.InsertResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {

    @GET("todoAPI.php?fun=get_all_pengguna")
    suspend fun getAllPengguna(): FormulirResponse

    @Multipart
    @POST("todoAPI.php?fun=insertImage")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): ImageResponse

    @FormUrlEncoded
    @POST("todoAPI.php?fun=insert_pengguna")
    suspend fun insertPengguna(
        @FieldMap parameters: HashMap<String, String>
    ): InsertResponse

    @FormUrlEncoded
    @POST("todoAPI.php?fun=update_pengguna")
    suspend fun updatePengguna(
        @Query("id") id: Int,
        @FieldMap parameters: HashMap<String, String>
    ): InsertResponse

    @DELETE("todoAPI.php?fun=delete_pengguna")
    suspend fun deletePengguna(
        @Query("id") id: Int
    ): ImageResponse

    @FormUrlEncoded
    @POST("todoAPI.php?fun=login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): InsertResponse
}
