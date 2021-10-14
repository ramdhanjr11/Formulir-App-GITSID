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

    private var _userId = sharedPreferences
    val userId get() = _userId.userId

    fun setSession(session: Boolean) {
        _loginSession.loginSession = session
    }

    fun setUserId(userId: String) {
        _userId.userId = userId
    }

    fun login(email: String, password: String) = useCase.login(email, password).asLiveData()

    fun loginAuth(deviceId: String) = useCase.loginAuth(deviceId).asLiveData()

    fun updateDeviceId(deviceId: String, userId: String) = useCase.updateDeviceId(deviceId, userId).asLiveData()
}