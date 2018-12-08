package com.hust.ping.androidlover.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hust.ping.androidlover.R
import com.hust.ping.androidlover.ui.main.MainActivity
import com.hust.ping.androidlover.utils.AndroidLoverPreference
import com.hust.ping.androidlover.utils.isLogin
import com.hust.ping.androidlover.utils.name
import com.hust.ping.androidlover.utils.postDelayed

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initView()
    }

    private fun initView() {
        postDelayed(2000) {
            if (!AndroidLoverPreference.isLogin && AndroidLoverPreference.name.isEmpty()) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            finish()
        }
    }
}
