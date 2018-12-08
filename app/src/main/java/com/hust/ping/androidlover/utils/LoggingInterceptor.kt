package com.hust.ping.androidlover.utils

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @created by PingYuan at 12/8/18
 * @email: husteryp@gmail.com
 * @description:
 */
class LoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.d("@HusterYP", request.url().toString())
        return chain.proceed(request)
    }
}