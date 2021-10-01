package com.muramsyah.gits.formulirapp.di

import android.content.Context
import com.muramsyah.gits.formulirapp.sf.AppSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class SharedPreferenceModule {

    @Provides
    fun provideSharedPrefenrece(@ApplicationContext context: Context) = AppSharedPreferences(context)

}