package com.ztl.kotlin.mvp.model

import com.ztl.kotlin.base.BaseMode
import com.ztl.kotlin.http.retrofit.RetrofitHelper
import com.ztl.kotlin.mvp.contract.NavigationContract
import com.ztl.kotlin.mvp.model.bean.HttpResult
import com.ztl.kotlin.mvp.model.bean.NaviData
import io.reactivex.Observable

class NavigationModel: BaseMode(), NavigationContract.Model {

    override fun getNavis():Observable<HttpResult<MutableList<NaviData>>> {
        return RetrofitHelper.apis.getNavis()
    }
}