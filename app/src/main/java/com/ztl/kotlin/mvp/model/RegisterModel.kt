package com.ztl.kotlin.mvp.model

import com.ztl.kotlin.base.BaseMode
import com.ztl.kotlin.http.retrofit.RetrofitHelper
import com.ztl.kotlin.mvp.contract.RegisterContract
import com.ztl.kotlin.mvp.model.bean.HttpResult
import com.ztl.kotlin.mvp.model.bean.LoginData
import io.reactivex.Observable

class RegisterModel: BaseMode(), RegisterContract.Model {
    override fun register(username: String, pwd: String, repeatPwd: String): Observable<HttpResult<LoginData>> {
        return RetrofitHelper.apis.register(username, pwd, repeatPwd)
    }

}