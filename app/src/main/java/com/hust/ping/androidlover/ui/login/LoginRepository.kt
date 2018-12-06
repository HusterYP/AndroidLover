package com.hust.ping.androidlover.ui.login

import com.hust.ping.androidlover.utils.postDelayed

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
    }

    fun login(userName: String, pwd: String) {
        when {
            userName.isEmpty() -> loginState.userNameNull()
            pwd.isEmpty() -> loginState.pwdNull()
            else -> postDelayed(3000) { loginState.loginSuccess() }
        }
    }
}