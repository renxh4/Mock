package com.renxh.mock

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowManager
import com.renxh.mock.db.DbUtils

object MockSdk {
    var application: Application? = null
    var db: DbUtils? = null
    fun init(application: Application) {
        this.application = application
        db = DbUtils(application)
        MockServer.init(application)
        val ipAddress = IPConfig.getIpAddress(application)
        Log.d("MockSdk", ipAddress + "/")


    }
    fun initService(activity: Activity) {
        Utils.checkSuspendedWindowPermission(activity) {
            activity.startService(Intent(activity, SuspendwindowService::class.java))
        }
    }


}