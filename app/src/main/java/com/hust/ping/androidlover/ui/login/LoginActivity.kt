package com.hust.ping.androidlover.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hust.ping.androidlover.R
import com.hust.ping.androidlover.ui.main.MainActivity
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
                    login_progress_root.setOnClickListener{}
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    if (imm.isActive)
                        imm.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                }
                LoginViewModel.LOGIN_SUCCESS -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                LoginViewModel.USER_NAME_NULL -> {
                    login_progress_root.visibility = View.GONE
                    login_name.error = "please input your name !"
                }
                LoginViewModel.PWD_NULL -> {
                    login_progress_root.visibility = View.GONE
                    login_pwd.error = "please input the password"
                }
            }
        })
        login.setOnClickListener {
            loginViewModel.login(login_name.text.toString(), login_pwd.text.toString())
        }
    }
}
