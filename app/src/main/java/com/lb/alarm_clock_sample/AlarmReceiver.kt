package com.lb.alarm_clock_sample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = "AlarmReceiver alarm triggered"
        Log.d("AppLog", message)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}

