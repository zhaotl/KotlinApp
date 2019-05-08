package com.ztl.kotlin.mvp.contract

import com.ztl.kotlin.base.mvp.IMode
import com.ztl.kotlin.base.mvp.IPresenter
import com.ztl.kotlin.base.mvp.IView
import com.ztl.kotlin.mvp.model.bean.HttpResult
import com.ztl.kotlin.mvp.model.bean.KnowledgeTree
import io.reactivex.Observable

interface KnowledgeListContract {

    interface Model: IMode {
        fun getKnowledges(): Observable<HttpResult<List<KnowledgeTree>>>
    }

    interface View: IView {
        fun scrollToTop()
        fun showKnowledges(list: List<KnowledgeTree>)
    }

    interface Presenter: IPresenter<View> {
        fun requestKnowledges()
    }
}