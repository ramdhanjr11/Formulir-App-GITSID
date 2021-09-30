package com.muramsyah.gits.formulirapp.di

import com.muramsyah.gits.formulirapp.data.source.FormulirRepository
import com.muramsyah.gits.formulirapp.domain.repository.IFormulirRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(formulirRepository: FormulirRepository): IFormulirRepository

}