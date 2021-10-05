package com.muramsyah.gits.formulirapp.ui.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.muramsyah.gits.formulirapp.domain.usecase.FormulirUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SendNotificationViewModel @Inject constructor(val useCase: FormulirUseCase) : ViewModel() {

    fun sendNotif(title: String, message: String) = useCase.sendNotif(title, message).asLiveData()

}