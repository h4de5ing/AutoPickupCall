package com.h4de5ing.autopickupcall

import android.app.Instrumentation
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.text.TextUtils
import android.view.KeyEvent
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class MyPhoneService : LifecycleService() {
    //android.telecom.TelecomManager#acceptRingingCall()
    private val scope = MainScope()
    private var sharedPreferences: SharedPreferences? = null
    override fun onCreate() {
        super.onCreate()
        startForeground()
        println("电话服务开启中")
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE)
        isAutoPickupCall = sharedPreferences?.getBoolean("auto_pickup", false) ?: false
        title22.asLiveData().observe(this) {
            if (!TextUtils.isEmpty(it)) {
                println("有人来电话了 $it")
                //当有人来电话的时候，就自动接电话，调用TelephoneManager的acceptRingingCall方法
                if (isAutoPickupCall) {
                    scope.launch(Dispatchers.IO) {
                        delay(1000)
                        Instrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_CALL)
                    }
                }
            }
        }
    }

    private fun startForeground() {
        val chan = NotificationChannel(
            "settingC", getString(R.string.app_name), NotificationManager.IMPORTANCE_NONE
        )
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        val notification = NotificationCompat.Builder(this, "settingC").setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher).setPriority(NotificationCompat.PRIORITY_MIN)
            .setCategory(Notification.CATEGORY_SERVICE).build()
        startForeground(1000, notification)
    }
}