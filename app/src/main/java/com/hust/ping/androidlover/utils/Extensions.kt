package com.hust.ping.androidlover.utils

import android.os.Handler

/**
 * @created by PingYuan at 12/6/18
 * @email: husteryp@gmail.com
 * @description:
 */
fun postDelayed(delayMillis: Long, task: () -> Unit) {
    Handler().postDelayed(task, delayMillis)
}