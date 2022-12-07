package com.atom.android.serverapp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.net.InetAddress

class MyService : Service()  {
    private  val TAG = "MyService"
    override fun onBind(p0: Intent?): IBinder {
        return mIBinder
    }

    val mIBinder = object : IAdd.Stub(){
        override fun sum(number1: Int, number2: Int): Int {
            Log.e(TAG, "sum: ${number1+number2}",  )
            return number1+number2
        }

    }
}