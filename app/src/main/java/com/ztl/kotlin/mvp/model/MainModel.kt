package com.ztl.kotlin.mvp.model

import com.ztl.kotlin.base.BaseMode
import com.ztl.kotlin.http.retrofit.RetrofitHelper
import com.ztl.kotlin.mvp.contract.MainContract
import com.ztl.kotlin.mvp.model.bean.HttpResult
import com.ztl.kotlin.utils.KLogger
import io.reactivex.Observable

class MainModel: BaseMode(), MainContract.Model {
    override fun logout(): Observable<HttpResult<Any>> {
        KLogger.d("MainModel")
        return RetrofitHelper.apis.logout()
    }
}