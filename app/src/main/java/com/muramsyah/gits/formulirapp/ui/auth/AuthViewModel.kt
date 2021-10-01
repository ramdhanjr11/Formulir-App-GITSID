package com.muramsyah.gits.formulirapp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.muramsyah.gits.formulirapp.domain.usecase.FormulirUseCase
import com.muramsyah.gits.formulirapp.sf.AppSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(val useCase: FormulirUseCase, val sharedPreferences: AppSharedPreferences) : ViewModel() {

    private var _loginSession = sharedPreferences
    val loginSession get() = _loginSession.loginSession

    fun setSession(session: Boolean) {
        _loginSession.loginSession = session
    }

    fun login(email: String, password: String) = useCase.login(email, password).asLiveData()
}