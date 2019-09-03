package com.ztl.kotlin.mvp.presenter

import com.ztl.kotlin.base.BasePresenter
import com.ztl.kotlin.mvp.contract.SharedArticlesContract
import com.ztl.kotlin.mvp.model.SharedArticlesModel
import com.ztl.kotlin.rx.result
import com.ztl.kotlin.utils.KLogger

class SharedArticlesPresenter: BasePresenter<SharedArticlesContract.Model, SharedArticlesContract.View>(), SharedArticlesContract.Presenter {

    override fun createMode(): SharedArticlesContract.Model? = SharedArticlesModel()

    override fun getSharedArticles() {
        KLogger.d("Subcribe Presenter")
        mMode?.getArticles()?.result(mMode, mView, {
            KLogger.d("retrofit result")
            mView?.showSharedArticles(it.data) ?: KLogger.d("mView is null")
        }, isShowLoading = true)
    }
}