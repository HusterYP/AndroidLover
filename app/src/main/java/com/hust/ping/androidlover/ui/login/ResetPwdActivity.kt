package com.hust.ping.androidlover.ui.login

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hust.ping.androidlover.R
import com.hust.ping.androidlover.utils.*
import com.hust.ping.androidlover.widget.GestureUnLock
import kotlinx.android.synthetic.main.activity_gesture_un_lock.*
import org.jetbrains.anko.toast

class ResetPwdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pwd)
        initView()
    }

    private fun initView() {
        unLock.setUnLockListener(object : GestureUnLock.UnLockState {
            override fun unLockSuccess() {
            }

            override fun unLockError() {
                startShakeByViewAnim(unLock, 1f, 1f, 5f, 800)
                toast("手势密码错误")
            }

            override fun confirmError() {
            }

            override fun confirmLockSuccess() {
            }

            override fun confirmLock() {
                val dialog = AlertDialog.Builder(this@ResetPwdActivity)
                if (AndroidLoverPreference.isLogin && !AndroidLoverPreference.pwd.isEmpty()) {
                    dialog.setTitle("你的密码是: ")
                    dialog.setMessage(AndroidLoverPreference.pwd)
                } else {
                    dialog.setTitle("对不起: ")
                    dialog.setMessage("你还未登录")
                    AndroidLoverPreference.unLock = ""
                }
                dialog.setPositiveButton("确定") { dia, _ ->
                    dia.dismiss()
                    finish()
                }
                dialog.show()
            }
        })
    }
}
