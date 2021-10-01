package com.muramsyah.gits.formulirapp.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muramsyah.gits.formulirapp.data.Resource
import com.muramsyah.gits.formulirapp.domain.model.Formulir
import com.muramsyah.gits.formulirapp.domain.usecase.FormulirUseCase
import com.muramsyah.gits.formulirapp.sf.AppSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val useCase: FormulirUseCase, val sharedPreferences: AppSharedPreferences) : ViewModel() {

    private var _allPengguna = MutableLiveData<Resource<List<Formulir>>>()
    val allPengguna get() = _allPengguna

    private var _loginSession = sharedPreferences
    val loginSession get() = _loginSession.loginSession

    init {
        getAllPengguna()
    }

    private fun getAllPengguna() {
        viewModelScope.launch {
            useCase.getAllPengguna().collect {
                when (it) {
                    is Resource.Loading -> {
                        _allPengguna.value = it
                    }
                    is Resource.Success -> {
                        _allPengguna.value = it
                    }
                    is Resource.Error -> {
                        Log.d("HomeViewModel", "getAllPengguna: ${it.message}")
                    }
                }
            }
        }
    }

    fun setSession(session: Boolean) {
        _loginSession.loginSession = session
    }

}