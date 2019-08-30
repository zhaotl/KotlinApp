package com.ztl.kotlin.mvp.presenter

import com.ztl.kotlin.mvp.contract.KnowledgeContract
import com.ztl.kotlin.mvp.model.KnowledgeModel
import com.ztl.kotlin.rx.result

class KnowledgePresenter : FavoritePresenter<KnowledgeContract.Model, KnowledgeContract.View>(),
    KnowledgeContract.Presenter {

    override fun createMode(): KnowledgeContract.Model? = KnowledgeModel()

    override fun getSharedArticleList(page: Int, cid: Int) {
       mMode?.getSharedArticleList(page, cid)?.result(mMode, mView, {
           mView?.setSharedArticleList(it.data)
       }, isShowLoading = true)
    }
}