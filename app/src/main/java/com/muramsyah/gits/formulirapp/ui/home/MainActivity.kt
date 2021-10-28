package com.muramsyah.gits.formulirapp.ui.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging
import com.muramsyah.gits.formulirapp.R
import com.muramsyah.gits.formulirapp.service.AlarmReceiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TOPIC = "formulir-app-commonly"
    private lateinit var broadcastReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        broadcastReceiver = AlarmReceiver()

        if (!broadcastReceiver.isAlarmSet(this)) {
            broadcastReceiver.setRepeatingAlarm(this)
        }
    }

}