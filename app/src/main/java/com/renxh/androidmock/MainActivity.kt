package com.renxh.androidmock

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.renxh.mock.MockSdk.init


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init(this)

    }
}