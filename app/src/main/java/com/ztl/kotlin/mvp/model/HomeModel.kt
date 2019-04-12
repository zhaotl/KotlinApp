package com.ztl.kotlin.mvp.model

import com.ztl.kotlin.base.BaseMode
import com.ztl.kotlin.http.retrofit.RetrofitHelper
import com.ztl.kotlin.mvp.contract.HomeContract
import com.ztl.kotlin.mvp.model.bean.Article
import com.ztl.kotlin.mvp.model.bean.ArticleList
import com.ztl.kotlin.mvp.model.bean.Banner
import com.ztl.kotlin.mvp.model.bean.HttpResult
import io.reactivex.Observable

class HomeModel: FavoriteModel(), HomeContract.Model {

    override fun getTopArticles(): Observable<HttpResult<MutableList<Article>>> {
        return RetrofitHelper.apis.getTopArticles()
    }

    override fun getNormalArticles(index: Int): Observable<HttpResult<ArticleList>> {
        return RetrofitHelper.apis.getHomeArticles(index)
    }

    override fun getBanner(): Observable<HttpResult<List<Banner>>> {
        return RetrofitHelper.apis.getHomeBanner()
    }
}