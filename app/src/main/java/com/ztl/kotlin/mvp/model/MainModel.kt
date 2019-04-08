package com.ztl.kotlin.mvp.model

import com.ztl.kotlin.base.BaseMode
import com.ztl.kotlin.http.retrofit.RetrofitHelper
import com.ztl.kotlin.mvp.contract.MainContract
import com.ztl.kotlin.mvp.model.bean.HttpResult
import io.reactivex.Observable

class MainModel: BaseMode(), MainContract.Model {

    override fun logout(): Observable<HttpResult<Any>> {
        return RetrofitHelper.apis.logout()
    }
}