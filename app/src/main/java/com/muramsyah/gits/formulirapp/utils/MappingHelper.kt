package com.muramsyah.gits.formulirapp.utils

import com.muramsyah.gits.formulirapp.data.source.remote.response.Data
import com.muramsyah.gits.formulirapp.domain.model.Formulir

object MappingHelper {

    fun entitiesToDomain(data: List<Data>): List<Formulir> =
        data.map {
            Formulir(
                id = it.id,
                nama = it.nama,
                alamat = it.alamat,
                email = it.email,
                image = it.image,
                password = it.password
            )
        }

    fun domainToEntity(data: Formulir): Data =
        Data(
            id = data.id,
            nama = data.nama,
            alamat = data.alamat,
            email = data.email,
            image = data.image,
            password = data.password
        )

}