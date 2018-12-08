package com.hust.ping.androidlover.ui.login

import com.hust.ping.androidlover.bean.UserBean
import io.reactivex.Observable
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * @created by PingYuan at 12/8/18
 * @email: husteryp@gmail.com
 * @description:
 */
interface LoginManager {
    @POST("user/register")
    fun register(@Query("username") username: String, @Query("password") password: String,
                 @Query("repassword") repassword: String): Observable<UserBean>

    @POST("user/login")
    fun login(@Query("username") username: String, @Query("password") password: String): Observable<UserBean>
}