package com.ztl.kotlin.mvp.contract

import com.ztl.kotlin.mvp.model.bean.Article
import com.ztl.kotlin.mvp.model.bean.ArticleList
import com.ztl.kotlin.mvp.model.bean.Banner
import com.ztl.kotlin.mvp.model.bean.HttpResult
import io.reactivex.Observable

interface HomeContract {

    interface Model: FavoriteContract.Model {
        fun getTopArticles(): Observable<HttpResult<MutableList<Article>>>
        fun getNormalArticles(index: Int): Observable<HttpResult<ArticleList>>
        fun getBanner(): Observable<HttpResult<List<Banner>>>
    }

    interface View: FavoriteContract.View {
        fun showBanner(banners: List<Banner>)
        fun showTopArticles(articles: ArticleList)
        fun scrollToTop()
    }

    interface Presenter: FavoriteContract.Presenter<View> {
        fun getBanner()
        fun getArticles(index: Int)
        fun getHomeData()
    }
}