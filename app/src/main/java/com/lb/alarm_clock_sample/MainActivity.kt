package com.lb.alarm_clock_sample

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var manager: AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        scheduleButton.setOnClickListener {
            val timeToTrigger = System.currentTimeMillis() + 30 * 1000
            val pendingIntent = PendingIntent.getBroadcast(this, 1, Intent(this, AlarmReceiver::class.java), PendingIntent.FLAG_UPDATE_CURRENT)
//            https://developer.android.com/reference/android/app/AlarmManager#setAlarmClock(android.app.AlarmManager.AlarmClockInfo,%20android.app.PendingIntent)
            manager.setAlarmClock(AlarmManager.AlarmClockInfo(timeToTrigger, pendingIntent), pendingIntent)
            checkSchedule()
        }
        checkSchedule.setOnClickListener {
            checkSchedule()
        }
    }

    private fun checkSchedule() {
//https://developer.android.com/reference/android/app/AlarmManager#getNextAlarmClock()
        val triggerTime = manager.nextAlarmClock?.triggerTime
        val currentTime = System.currentTimeMillis()
        val millisLeft = if (triggerTime == null) -1 else triggerTime - currentTime
        val message = if (millisLeft < 0) {
            "no pending alarm or missed it"
        } else {
            val hms = String.format("%02d:%02d:%02d", millisLeft / (3600 * 1000), millisLeft / (60 * 1000) % 60, millisLeft / 1000 % 60)
            "next alarm should be triggered in $millisLeft ms - $hms"
        }
        Log.d("AppLog", message)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
