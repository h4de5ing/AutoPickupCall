package com.h4de5ing.autopickupcall

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.CheckBox
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var checkBox: CheckBox? = null
    private var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        checkBox = findViewById<CheckBox>(R.id.checkbox)
        checkBox!!.isChecked = sharedPreferences?.getBoolean("auto_pickup", false) ?: false
        checkBox!!.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences?.edit()?.putBoolean("auto_pickup", isChecked)?.apply()
            isAutoPickupCall = isChecked
        }

        try {
            startService(Intent(this, MyPhoneService::class.java))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}