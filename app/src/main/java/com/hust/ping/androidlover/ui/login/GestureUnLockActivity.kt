package com.hust.ping.androidlover.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hust.ping.androidlover.R
import com.hust.ping.androidlover.ui.main.MainActivity
import com.hust.ping.androidlover.utils.AndroidLoverPreference
import com.hust.ping.androidlover.utils.startShakeByViewAnim
import com.hust.ping.androidlover.utils.unLock
import com.hust.ping.androidlover.widget.GestureUnLock
import kotlinx.android.synthetic.main.activity_gesture_un_lock.*
import org.jetbrains.anko.toast

class GestureUnLockActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gesture_un_lock)
        initView()
    }

    private fun initView() {
        unLock.setUnLockListener(object : GestureUnLock.UnLockState {
            override fun unLockSuccess() {
            }

            override fun unLockError() {
                startShakeByViewAnim(unLock, 1f, 1f, 5f, 800)
            }

            override fun confirmError() {
                startShakeByViewAnim(unLock, 1f, 1f, 5f, 800)
                AndroidLoverPreference.unLock = ""
                gesture_set.text = getString(R.string.set_gesture)
                toast("手势密码确认错误")
            }

            override fun confirmLockSuccess() {
                val intent = Intent(this@GestureUnLockActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun confirmLock() {
                gesture_set.text = getString(R.string.confirm_lock)
            }
        })
    }
}
