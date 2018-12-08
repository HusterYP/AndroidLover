package com.hust.ping.androidlover.ui.login

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import org.jetbrains.anko.toast

/**
 * @created by PingYuan at 12/8/18
 * @email: husteryp@gmail.com
 * @description:
 */
class RegisterViewModel(@NonNull application: Application) : AndroidViewModel(application), RegisterRepository.RegisterState {

    companion object {
        const val NAME_NULL = 0
        const val PWD_NULL = 1
        const val CONFIRM_PWD_NULL = 2
        const val PWD_CONFIRM_ERROR = 3
        const val NAME_EXIST = 4
        const val REGISTER_ING = 5
        const val REGISTER_SUCCESS = 6
        const val REGISTER_ERROR = 7
    }

    var registerLiveData = MutableLiveData<Int>()
    private val registerRepository = RegisterRepository(this)

    fun register(name: String, pwd: String, confirmPwd: String) {
        registerRepository.register(name, pwd, confirmPwd)
    }

    override fun registering() {
        registerLiveData.value = REGISTER_ING
    }

    override fun nameNull() {
        registerLiveData.value = NAME_NULL
    }

    override fun pwdNull() {
        registerLiveData.value = PWD_NULL
    }

    override fun confirmPwdNull() {
        registerLiveData.value = CONFIRM_PWD_NULL
    }

    override fun confirmPwdError() {
        registerLiveData.value = PWD_CONFIRM_ERROR
    }

    override fun nameExist() {
        registerLiveData.value = NAME_EXIST
    }

    override fun registerSuccess() {
        registerLiveData.value = REGISTER_SUCCESS
    }

    override fun registerError(error: String) {
        registerLiveData.value = REGISTER_ERROR
        com.hust.ping.androidlover.Application.application.toast(error)
    }
}