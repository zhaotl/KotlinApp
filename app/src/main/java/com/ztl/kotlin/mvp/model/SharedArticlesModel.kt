package com.ztl.kotlin.mvp.model

import com.ztl.kotlin.base.BaseMode
import com.ztl.kotlin.http.retrofit.RetrofitHelper
import com.ztl.kotlin.mvp.contract.SharedArticlesContract
import com.ztl.kotlin.mvp.model.bean.HttpResult
import com.ztl.kotlin.mvp.model.bean.SharedArticles
import io.reactivex.Observable

class SharedArticlesModel: BaseMode(), SharedArticlesContract.Model {

    override fun getArticles():Observable<HttpResult<MutableList<SharedArticles>>> {
        return RetrofitHelper.apis.getSubscribeArticles()
    }
}