package com.ztl.kotlin.mvp.presenter

import com.ztl.kotlin.base.BasePresenter
import com.ztl.kotlin.mvp.contract.KnowledgeListContract
import com.ztl.kotlin.mvp.model.KnowledgeListMode
import com.ztl.kotlin.rx.result

class KnowledgeListPresenter : BasePresenter<KnowledgeListContract.Model, KnowledgeListContract.View>(),
        KnowledgeListContract.Presenter {

    override fun createMode(): KnowledgeListContract.Model? = KnowledgeListMode()

    override fun requestKnowledges() {
        mMode?.getKnowledges()?.result(mMode, mView, {
            mView?.showKnowledges(it.data)
        }, isShowLoading = true)
    }
}