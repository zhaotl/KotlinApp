package com.ztl.kotlin.mvp.contract

import com.ztl.kotlin.base.mvp.IMode
import com.ztl.kotlin.base.mvp.IPresenter
import com.ztl.kotlin.base.mvp.IView
import com.ztl.kotlin.mvp.model.bean.HttpResult
import com.ztl.kotlin.mvp.model.bean.SharedArticles
import io.reactivex.Observable

class SharedArticlesContract {

    interface Model: IMode {
        fun getArticles(): Observable<HttpResult<MutableList<SharedArticles>>>
    }

    interface View: IView {
        fun showSharedArticles(articles: MutableList<SharedArticles>)
    }

    interface Presenter: IPresenter<View> {
        fun getSharedArticles()
    }
}