package com.ztl.kotlin.mvp.contract

import com.ztl.kotlin.mvp.model.bean.ArticleList
import com.ztl.kotlin.mvp.model.bean.HttpResult
import io.reactivex.Observable

interface ProjectListContract {

    interface Model: FavoriteContract.Model {
        fun getProjectList(page: Int, cid: Int): Observable<HttpResult<ArticleList>>
    }

    interface View: FavoriteContract.View {
        fun scrollToTop()
        fun setProjectList(articles: ArticleList)
    }

    interface Presenter: FavoriteContract.Presenter<View> {
        fun getProjectList(page: Int, cid: Int)
    }
}