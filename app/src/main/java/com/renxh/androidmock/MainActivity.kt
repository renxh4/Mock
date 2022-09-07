package com.renxh.androidmock

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.renxh.mock.MockSdk


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        findViewById<TextView>(R.id.textview).setOnClickListener {
//            MockSdk.initService(this)
//        }
    }
}