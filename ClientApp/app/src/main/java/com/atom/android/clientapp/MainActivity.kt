package com.atom.android.clientapp

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.atom.android.clientapp.databinding.ActivityMainBinding
import com.atom.android.serverapp.IAdd
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity(){
    private val TAG = "MainActivity"

    private var addService: IAdd? = null

    private var textView: TextView? = null

    private lateinit var binding:ActivityMainBinding

    private val user = User("Hung", 123)

    val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder) {
            Log.e(TAG, "onServiceConnected: ")
            addService = IAdd.Stub.asInterface(p1)
            addService?.let {

                textView?.text = it.sum(1, 2).toString()
            }
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.e(TAG, "onServiceDisconnected: ")
            textView?.text = "Not connected"
            addService = null
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        textView = findViewById(R.id.text_view)

        binding.user = user
        
       // initConnection()

        runBlocking {
            a()
            a()
        }
    }

    suspend fun a(){
        delay(4000);
        Log.e(TAG, "a: ", )
    }

    fun onClick(view : View){
        Log.e(TAG, "onClick: ${binding.textViewUser.text}", )
    }

    fun onClickSet(view : View){
        val newName = binding.user?.name.toString()
        Log.e(TAG, "onClickSet: ${newName}", )
    }

    fun initConnection() {
        if (addService == null) {
            val intent = Intent(IAdd::class.simpleName);
            intent.action = "android.intent.action.IAdd"
            intent.`package` = "com.atom.android.serverapp"
            bindService(intent, connection, BIND_AUTO_CREATE)
            Log.e(TAG, "initConnection: ")
        }
    }
}