package com.renxh.mock

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleService

/**
 * @功能:应用外打开Service 有局限性 特殊界面无法显示
 * @User Lmy
 * @Creat 4/15/21 5:28 PM
 * @Compony 永远相信美好的事情即将发生
 */
class SuspendwindowService : Service() {
    private var screenHeight: Int = 0
    private var screenWidth: Int = 0
    private var mockIpRoot: View? = null
    private var mockRoot: View? = null
    private lateinit var windowManager: WindowManager
    private var floatRootView: View? = null//悬浮窗View


    override fun onCreate() {
        super.onCreate()
        showWindow()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun showWindow() {
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val display: Display = windowManager.defaultDisplay
        screenWidth = display.width
        screenHeight = display.height
        val outMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(outMetrics)
        var layoutParam = WindowManager.LayoutParams().apply {
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_PHONE
            }
            format = PixelFormat.RGBA_8888
            flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            //位置大小设置
            width = WindowManager.LayoutParams.WRAP_CONTENT;
            height = WindowManager.LayoutParams.WRAP_CONTENT;
            gravity = Gravity.LEFT or Gravity.TOP
            //设置剧中屏幕显示
            x = 0
            y = outMetrics.heightPixels / 2 - height / 2
        }
        // 新建悬浮窗控件
        floatRootView = LayoutInflater.from(this).inflate(R.layout.activity_float_item, null)
        floatRootView?.setOnTouchListener(ItemViewTouchListener(layoutParam, windowManager,screenHeight,screenWidth) {
            mockRoot?.visibility = View.GONE
            mockIpRoot?.visibility = View.VISIBLE
        })
        // 将悬浮窗控件添加到WindowManager
        windowManager.addView(floatRootView, layoutParam)

        initView(floatRootView)
    }

    private fun initView(floatRootView: View?) {
        mockRoot = floatRootView?.findViewById<View>(R.id.mock_root)
        mockIpRoot = floatRootView?.findViewById<View>(R.id.mock_ip_root)
        var mockIpImg = floatRootView?.findViewById<ImageView>(R.id.mock_ip_img)
        var mockIpText = floatRootView?.findViewById<TextView>(R.id.mock_ip_text)

        mockIpImg?.setOnClickListener {
            mockRoot?.visibility = View.VISIBLE
            mockIpRoot?.visibility = View.GONE
        }

        val ipAddress = IPConfig.getIpAddress(application)

        mockIpText?.text = "请在浏览器输入 ${ipAddress}:5566 进行mock数据"

    }

}

