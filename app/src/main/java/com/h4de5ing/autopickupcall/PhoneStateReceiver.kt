package com.h4de5ing.autopickupcall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneStateReceiver extends BroadcastReceiver {
    private static final String TAG = "PhoneStateReceiver";

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        assert action != null;
        if (Intent.ACTION_NEW_OUTGOING_CALL.equals(action)) {// 去电
            String outNumber = this.getResultData();// 去电号码
            Log.i(TAG, "正在呼叫 " + outNumber);
        } else if ("android.intent.action.PHONE_STATE".equals(action)) {// 来电
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String inNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);// 来电号码
            Log.i(TAG, "有人来电话了 " + inNumber);
            if (TelephonyManager.EXTRA_STATE_RINGING.equalsIgnoreCase(state)) {// 电话正在响铃
                Log.i(TAG, "电话正在响铃 " + inNumber);
            } else if (TelephonyManager.EXTRA_STATE_IDLE.equalsIgnoreCase(state)) {// 挂断
                Log.i(TAG, "挂断 " + inNumber);
            } else if (TelephonyManager.EXTRA_STATE_OFFHOOK.equalsIgnoreCase(state)) {// 摘机，通话状态
                Log.i(TAG, "摘机 " + inNumber);
            }
        }
    }
}
