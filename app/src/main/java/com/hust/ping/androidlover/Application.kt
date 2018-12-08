package com.hust.ping.androidlover

import android.app.Application
import android.content.Context

/**
 * @created by PingYuan at 12/5/18
 * @email: husteryp@gmail.com
 * @description:
 */
class Application : Application() {

    companion object {
        lateinit var application: Context
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }
}