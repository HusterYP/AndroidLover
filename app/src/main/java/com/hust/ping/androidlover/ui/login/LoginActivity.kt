package com.hust.ping.androidlover.ui.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hust.ping.androidlover.R
import com.hust.ping.androidlover.ui.main.MainActivity
import com.hust.ping.androidlover.utils.hideInput
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
    }

    private fun initView() {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        loginViewModel.loginState.observe(this, Observer<Int> {
            when (it) {
                LoginViewModel.LOGIN_ING -> {
                    login_progress_root.visibility = View.VISIBLE
                    login_progress_root.setOnClickListener {}
                    hideInput(this)
                }
                LoginViewModel.LOGIN_SUCCESS -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                LoginViewModel.USER_NAME_NULL -> {
                    login_progress_root.visibility = View.GONE
                    login_name.error = getString(R.string.input_name)
                }
                LoginViewModel.PWD_NULL -> {
                    login_progress_root.visibility = View.GONE
                    login_pwd.error = getString(R.string.input_pwd)
                }
                LoginViewModel.LOGIN_ERROR -> {
                    login_progress_root.visibility = View.GONE
                }
            }
        })
        login.setOnClickListener {
            loginViewModel.login(login_name.text.toString(), login_pwd.text.toString())
        }
        login_register.setOnClickListener {
            login_register.setTextColor(Color.parseColor("#FCCF31"))
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
        }
        login_forget_pwd.setOnClickListener {
            login_forget_pwd.setTextColor(Color.parseColor("#FCCF31"))
            val intent = Intent(this@LoginActivity, ResetPwdActivity::class.java)
            startActivity(intent)
        }
    }
}
