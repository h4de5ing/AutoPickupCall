package com.h4de5ing.autopickupcall

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log

class PhoneStateReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "PhoneStateReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if ("android.intent.action.PHONE_STATE" == action) { // 来电
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            if (TelephonyManager.EXTRA_STATE_RINGING.equals(state, ignoreCase = true)) { // 电话正在响铃
                Log.i(TAG, "电话正在响铃")
                //延迟1秒接听电话
                onSPChange("${System.currentTimeMillis()}")
            } else if (TelephonyManager.EXTRA_STATE_IDLE.equals(state, ignoreCase = true)) { // 挂断
                Log.i(TAG, "挂断")
            } else if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(
                    state,
                    ignoreCase = true
                )
            ) { // 摘机，通话状态
                Log.i(TAG, "摘机")
            }
        }
    }
}
