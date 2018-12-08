package com.hust.ping.androidlover.utils

import android.content.Context
import android.content.SharedPreferences
import com.hust.ping.androidlover.Application

object AndroidLoverPreference : HasSharedPreference {
    var ONE_DAY = 24 * 60 * 60 * 1000L
    override var preference: SharedPreferences = Application.application.getSharedPreferences("com.hust.ping.androidlover", Context.MODE_PRIVATE)
    override var preferenceEditor: SharedPreferences.Editor = preference.edit()
}

var AndroidLoverPreference.isLogin: Boolean by BooleanSPReadWriteDelegate("isLogin", false)
var AndroidLoverPreference.name: String by StringSPReadWriteDelegate("name", "")
var AndroidLoverPreference.pwd: String by StringSPReadWriteDelegate("pwd", "")