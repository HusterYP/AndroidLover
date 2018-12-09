package com.hust.ping.androidlover.utils

import com.hust.ping.androidlover.Application
import org.jetbrains.anko.displayMetrics

/**
 * @created by PingYuan at 11/13/18
 * @email: husteryp@gmail.com
 * @description: 设备信息
 */
object ScreenUtil {
    fun dp2px(dpValue: Float): Float {
        return Application.application.resources.displayMetrics.density * dpValue + 0.5f
    }

    fun px2dp(pxValue: Float): Float {
        return pxValue / Application.application.resources.displayMetrics.density + 0.5f
    }

    fun getDeviceWidth() : Int {
        return Application.application.displayMetrics.widthPixels
    }

    fun getDeviceHeight() : Int {
        return Application.application.displayMetrics.heightPixels
    }
}