package com.h4de5ing.autopickupcall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            context.startService(new Intent(context, MyPhoneService.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
