package com.muramsyah.gits.formulirapp.ui.dashboard

import androidx.lifecycle.ViewModel
import com.muramsyah.gits.formulirapp.sf.AppSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(val sharedPreferences: AppSharedPreferences) : ViewModel() {

    private var _loginSession = sharedPreferences
    val loginSession get() = _loginSession.loginSession

    fun setSession(session: Boolean) {
        _loginSession.loginSession = session
    }
}