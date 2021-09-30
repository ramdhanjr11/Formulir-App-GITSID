package com.muramsyah.gits.formulirapp.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Formulir(
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