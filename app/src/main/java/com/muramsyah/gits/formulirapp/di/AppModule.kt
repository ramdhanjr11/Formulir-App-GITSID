package com.muramsyah.gits.formulirapp.di

import com.muramsyah.gits.formulirapp.domain.usecase.FormulirInteractor
import com.muramsyah.gits.formulirapp.domain.usecase.FormulirUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideFormulirUseCase(formulirInteractor: FormulirInteractor): FormulirUseCase

}