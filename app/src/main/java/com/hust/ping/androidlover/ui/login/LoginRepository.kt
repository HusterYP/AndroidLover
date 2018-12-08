package com.hust.ping.androidlover.ui.login

import android.util.Log
import com.hust.ping.androidlover.utils.postDelayed
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @created by PingYuan at 12/6/18
 * @email: husteryp@gmail.com
 * @description:
 */
class LoginRepository(private val loginState: LoginState) {
    interface LoginState {
        fun userNameNull()
        fun pwdNull()
        fun loginSuccess()
        fun loginError(error: String)
    }

    fun login(userName: String, pwd: String) {
        when {
            userName.isEmpty() -> loginState.userNameNull()
            pwd.isEmpty() -> loginState.pwdNull()
            else -> {
                LoginModel.loginModel.login(userName, pwd)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        if (it.errorCode < 0)
                            loginState.loginError(it.errorMsg)
                        else {
                            loginState.loginSuccess()
                            Log.d("@HusterYP",it.toString())
                        }
                    }
            }
        }
    }
}