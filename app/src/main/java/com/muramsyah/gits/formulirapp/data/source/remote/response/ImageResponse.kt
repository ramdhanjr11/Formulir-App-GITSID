package com.muramsyah.gits.formulirapp.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageResponse(
    @field:SerializedName("status")
    val status: Int,
    @field:SerializedName("message")
    val message: String
) : Parcelable
