package com.ztl.kotlin.mvp.presenter

import com.ztl.kotlin.base.BasePresenter
import com.ztl.kotlin.mvp.contract.SharedArticlesContract
import com.ztl.kotlin.mvp.model.SharedArticlesModel

class SharedArticlesPresenter: BasePresenter<SharedArticlesContract.Model, SharedArticlesContract.View>(), SharedArticlesContract.Presenter {

    override fun createMode(): SharedArticlesContract.Model? = SharedArticlesModel()

    override fun getSharedArticles() {

    }
}