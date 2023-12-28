package com.bitcodetech.broadcastreceiversdemo

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bitcodetech.broadcastreceiversdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var myBR: MyBR
    private lateinit var newBr: NewBr
    private lateinit var binding : ActivityMainBinding

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val filters = IntentFilter(Intent.ACTION_WALLPAPER_CHANGED)
        filters.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        filters.addAction(Intent.ACTION_BATTERY_LOW)
        filters.priority = 1000

        myBR = MyBR()
        registerReceiver(myBR, filters)

        binding.btnRegister.setOnClickListener {
            newBr = NewBr()
            val filters = IntentFilter("in.bitcode.download.COMPLETE")

            registerReceiver(newBr, filters, RECEIVER_EXPORTED)
        }

        binding.btnUnregister.setOnClickListener {
            unregisterReceiver(newBr)
        }

        binding.btnSendBroadcast.setOnClickListener {
            val intent = Intent()
            intent.action = "in.bitcode.download.COMPLETE"
            //intent.action = Intent.ACTION_BATTERY_LOW
            intent.putExtra("path", binding.edtPath.text.toString())

            sendStickyBroadcast(intent)
            //sendBroadcast(intent)
        }
    }

    override fun onDestroy() {
        unregisterReceiver(myBR)
        super.onDestroy()
    }
}