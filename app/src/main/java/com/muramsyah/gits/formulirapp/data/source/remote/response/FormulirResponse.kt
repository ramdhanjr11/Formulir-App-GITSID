package com.muramsyah.gits.formulirapp.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FormulirResponse(
    @field:SerializedName("status")
    val status: Int,
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("data")
    val data: List<Data>? = null
) : Parcelable

@Parcelize
data class InsertResponse(
    @field:SerializedName("status")
    val status: Int,
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("data")
    val data: Data? = null
): Parcelable

@Parcelize
data class Data(
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("nama")
    val nama: String,
    @field:SerializedName("alamat")
    val alamat: String,
    @field:SerializedName("email")
    val email: String,
    @field:SerializedName("image")
    val image: String,
    @field:SerializedName("password")
    val password: String
) : Parcelable
