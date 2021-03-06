package com.hust.ping.androidlover.ui.login

import com.hust.ping.androidlover.utils.AndroidLoverPreference
import com.hust.ping.androidlover.utils.isLogin
import com.hust.ping.androidlover.utils.name
import com.hust.ping.androidlover.utils.pwd
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @created by PingYuan at 12/8/18
 * @email: husteryp@gmail.com
 * @description:
 */
class RegisterRepository(private val registerState: RegisterState) {

    interface RegisterState {
        fun registering()
        fun nameNull()
        fun pwdNull()
        fun confirmPwdNull()
        fun confirmPwdError()
        fun nameExist()
        fun registerSuccess()
        fun registerError(error: String)
    }

    fun register(name: String, pwd: String, confirmPwd: String) {
        registerState.registering()
        when {
            name.isEmpty() -> registerState.nameNull()
            pwd.isEmpty() -> registerState.pwdNull()
            confirmPwd.isEmpty() -> registerState.confirmPwdNull()
            pwd != confirmPwd -> registerState.confirmPwdError()
            else -> {
                LoginModel.loginModel.register(name, pwd, confirmPwd)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        if (it.errorCode < 0)
                            registerState.registerError(it.errorMsg)
                        else {
                            registerState.registerSuccess()
                            AndroidLoverPreference.isLogin = true
                            AndroidLoverPreference.name = name
                            AndroidLoverPreference.pwd = pwd
                        }
                    }
            }
        }
    }
}