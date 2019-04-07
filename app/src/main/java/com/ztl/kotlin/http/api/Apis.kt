package com.ztl.kotlin.http.api

import com.ztl.kotlin.mvp.model.bean.HttpResult
import com.ztl.kotlin.mvp.model.bean.LoginData
import com.ztl.kotlin.utils.HTTPConstant
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Apis {

    object ErrorCode {
        val SUCCESS = 0
    }

    // Login
    @FormUrlEncoded
    @POST(value = HTTPConstant.LOGIN_PATH)
    fun loginWithName(@Field("username") name: String,
                      @Field("password") password: String): Observable<HttpResult<LoginData>>

    @FormUrlEncoded
    @POST(value = HTTPConstant.REGISTER_PATH)
    fun register(@Field("username") name: String,
                 @Field("password") pwd: String,
                 @Field("repassword") repwd: String): Observable<HttpResult<LoginData>>

}