package com.hust.ping.androidlover.ui.login

import com.hust.ping.androidlover.Constant
import com.hust.ping.androidlover.bean.UserBean
import com.hust.ping.androidlover.utils.LoggingInterceptor
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @created by PingYuan at 12/8/18
 * @email: husteryp@gmail.com
 * @description:
 */
class LoginModel private constructor() {

    companion object {
        val loginModel = LoginModel()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private val loginManager by lazy {
        retrofit.create(LoginManager::class.java)
    }

    private val client by lazy {
        OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(LoggingInterceptor())
    }

    fun register(name: String, pwd: String, confirmPwd: String): Observable<UserBean> {
        return loginManager.register(name, pwd, confirmPwd)
    }

    fun login(name: String, pwd: String): Observable<UserBean> {
        return loginManager.login(name, pwd)
    }
}