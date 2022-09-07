package com.renxh.mock

import android.view.MotionEvent
import android.view.View
import android.view.WindowManager

class ItemViewTouchListener(val wl: WindowManager.LayoutParams, val windowManager: WindowManager,var screenHeight : Int,var screenWidth : Int,var click : (()->Unit)?) :
    View.OnTouchListener {
    private var pre: Long = 0
    private var x = 0
    private var y = 0
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                pre = System.currentTimeMillis()
                x = motionEvent.rawX.toInt()
                y = motionEvent.rawY.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                val nowX = motionEvent.rawX.toInt()
                val nowY = motionEvent.rawY.toInt()
                val movedX = nowX - x
                val movedY = nowY - y
                x = nowX
                y = nowY
                wl.apply {
                    x += movedX
                    y += movedY
                }
                //更新悬浮球控件位置
                windowManager?.updateViewLayout(view, wl)
            }

            MotionEvent.ACTION_UP -> {
                if (System.currentTimeMillis()-pre<100){
                    click?.invoke()
                }else{
                    if (x > screenWidth/2){
                        //向右贴边
                        wl.apply {
                            x = screenWidth
                        }
                        //更新悬浮球控件位置
                        windowManager?.updateViewLayout(view, wl)
                    }else{
                        //向左贴边
                        wl.apply {
                            x = 0
                        }
                        //更新悬浮球控件位置
                        windowManager?.updateViewLayout(view, wl)
                    }
                }
            }

            else -> {

            }
        }
        return false
    }
}