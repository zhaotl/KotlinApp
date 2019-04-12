package com.ztl.kotlin.mvp.model

import com.ztl.kotlin.base.BaseMode
import com.ztl.kotlin.http.retrofit.RetrofitHelper
import com.ztl.kotlin.mvp.contract.FavoriteContract
import com.ztl.kotlin.mvp.model.bean.HttpResult
import io.reactivex.Observable

open class FavoriteModel: BaseMode(), FavoriteContract.Model {
    override fun addFavorite(id: Int): Observable<HttpResult<Any>> {
        return RetrofitHelper.apis.addInSiteFavorite(id)
    }

    override fun delFavorite(id: Int): Observable<HttpResult<Any>> {
        return RetrofitHelper.apis.deleteFromArticles(id)
    }
}