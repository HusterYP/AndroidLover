package com.hust.ping.androidlover.ui.login

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

/**
 * @created by PingYuan at 12/5/18
 * @email: husteryp@gmail.com
 * @description:
 */
class LoginViewModel(@NonNull application: Application) : AndroidViewModel(application), LoginRepository.LoginState {

    companion object {
        const val LOGIN_SUCCESS = 0
        const val LOGIN_ING = 1
        const val USER_NAME_NULL = 2
        const val PWD_NULL = 4
    }

    var loginState: MutableLiveData<Int> = MutableLiveData()
    var loginRepository: LoginRepository = LoginRepository(this)

    fun login(userName: String, pwd: String) {
        loginState.value = LOGIN_ING
        loginRepository.login(userName, pwd)
    }

    override fun userNameNull() {
        loginState.value = USER_NAME_NULL
    }

    override fun pwdNull() {
        loginState.value = PWD_NULL
    }

    override fun loginSuccess() {
        loginState.value = LOGIN_SUCCESS

    }
}