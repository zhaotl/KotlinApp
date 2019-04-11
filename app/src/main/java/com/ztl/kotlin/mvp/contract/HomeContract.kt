package com.ztl.kotlin.mvp.contract

import com.ztl.kotlin.mvp.model.bean.ArticleList
import com.ztl.kotlin.mvp.model.bean.Banner

interface HomeContract {

    interface Model: FavoriteContract.Model {
        fun getTopArticles()
        fun getNormalArticles()
        fun getBanner()
    }

    interface View: FavoriteContract.View {
        fun showBanner(banners: List<Banner>)
        fun showTopArticles(articles: ArticleList)
        fun scrollToTop()
    }

    interface Presenter: FavoriteContract.Presenter {
        fun getBanner()
        fun getArticles()
    }
}