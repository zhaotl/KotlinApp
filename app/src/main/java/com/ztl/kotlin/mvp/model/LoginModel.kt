package com.ztl.kotlin.mvp.model

import com.ztl.kotlin.base.BaseMode
import com.ztl.kotlin.http.retrofit.RetrofitHelper
import com.ztl.kotlin.mvp.contract.LoginContract
import com.ztl.kotlin.mvp.model.bean.HttpResult
import com.ztl.kotlin.mvp.model.bean.LoginData
import com.ztl.kotlin.utils.KLogger
import io.reactivex.Observable

class LoginModel:BaseMode(), LoginContract.Model {
    override fun login(username: String, password: String): Observable<HttpResult<LoginData>> {
        KLogger.d("Login Model Login")
        return RetrofitHelper.apis.loginWithName(username, password)
    }
}