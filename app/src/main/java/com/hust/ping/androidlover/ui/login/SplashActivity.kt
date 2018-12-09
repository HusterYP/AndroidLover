package com.hust.ping.androidlover.ui.login

import android.animation.Animator
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.animation.BounceInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.hust.ping.androidlover.R
import com.hust.ping.androidlover.ui.main.MainActivity
import com.hust.ping.androidlover.utils.*
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initView()
    }

    private fun initView() {
        val typeface = Typeface.createFromAsset(assets, "fonts/brushscriptstd.otf")
        splash_txt.typeface = typeface
        splash_txt.animate().setDuration(2000)
            .translationYBy(ScreenUtil.getDeviceHeight() / 2f)
            .alphaBy(1f)
            .setInterpolator(BounceInterpolator())
            .setListener(object: Animator.AnimatorListener{
                override fun onAnimationRepeat(animation: Animator?) {
                }
                override fun onAnimationEnd(animation: Animator?) {
                    postDelayed(1000) {
                        if (!AndroidLoverPreference.isLogin && AndroidLoverPreference.name.isEmpty()) {
                            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            val intent = Intent(this@SplashActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                        finish()
                    }
                }
                override fun onAnimationCancel(animation: Animator?) {
                }
                override fun onAnimationStart(animation: Animator?) {
                }
            })
    }
}
