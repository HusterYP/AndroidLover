package com.hust.ping.androidlover.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.view.inputmethod.InputMethodManager

/**
 * @created by PingYuan at 12/6/18
 * @email: husteryp@gmail.com
 * @description:
 */
fun postDelayed(delayMillis: Long, task: () -> Unit) {
    Handler().postDelayed(task, delayMillis)
}

fun hideInput(activity: Activity) {
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (imm.isActive)
        imm.hideSoftInputFromWindow(activity.currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

fun Activity.immersiveStatusBar(color: Int) {
    if (Build.VERSION.SDK_INT >= 21) {
        val decorView = window.decorView
//            var option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        var option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            option = option or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        decorView.systemUiVisibility = option
        window.statusBarColor = color
    }
}

fun startShakeByViewAnim(view: View?, scaleSmall: Float, scaleLarge: Float, shakeDegrees: Float, duration: Long) {
    if (view == null) {
        return
    }
    //由小变大
    val scaleAnim = ScaleAnimation(scaleSmall, scaleLarge, scaleSmall, scaleLarge)
    //从左向右
    val rotateAnim = RotateAnimation(-shakeDegrees,
        shakeDegrees,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f)

    scaleAnim.duration = duration
    rotateAnim.duration = duration / 10
    rotateAnim.repeatMode = Animation.REVERSE
    rotateAnim.repeatCount = 10

    val smallAnimationSet = AnimationSet(false)
    smallAnimationSet.addAnimation(scaleAnim)
    smallAnimationSet.addAnimation(rotateAnim)

    view.startAnimation(smallAnimationSet)
}
