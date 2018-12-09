package com.hust.ping.androidlover.utils


import android.content.Context
import android.net.ConnectivityManager
import com.hust.ping.androidlover.Application
import com.hust.ping.androidlover.R
import org.jetbrains.anko.toast

/**
 * @created by PingYuan at 11/4/18
 * @email: husteryp@gmail.com
 * @description: 设备状态检测, 如网络状态等
 */

object StateUtil {
    fun isNetworkConnected(): Boolean {
        val manager = Application.application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        val state = networkInfo?.isAvailable ?: false
        if (!state)
            Application.application.toast(R.string.no_network)
        return state
    }
}