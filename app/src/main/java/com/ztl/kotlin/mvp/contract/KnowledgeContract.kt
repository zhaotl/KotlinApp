package com.ztl.kotlin.mvp.contract

import com.ztl.kotlin.base.BaseMvpFragment
import com.ztl.kotlin.base.mvp.IMode
import com.ztl.kotlin.base.mvp.IPresenter
import com.ztl.kotlin.base.mvp.IView
import com.ztl.kotlin.mvp.model.bean.ArticleList
import com.ztl.kotlin.mvp.model.bean.HttpResult
import io.reactivex.Observable

interface KnowledgeContract {

    interface Model: FavoriteContract.Model {
        fun getSharedArticleList(page: Int, cid: Int): Observable<HttpResult<ArticleList>>
    }

    interface View: FavoriteContract.View {
        fun setSharedArticleList(articles: ArticleList)
        fun scrollToTop()
    }

    interface Presenter: FavoriteContract.Presenter<View> {
        fun getSharedArticleList(page: Int, cid: Int)
    }
}