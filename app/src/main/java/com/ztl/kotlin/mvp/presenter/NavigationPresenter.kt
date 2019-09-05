package com.ztl.kotlin.mvp.presenter

import com.ztl.kotlin.base.BasePresenter
import com.ztl.kotlin.mvp.contract.NavigationContract
import com.ztl.kotlin.mvp.model.NavigationModel
import com.ztl.kotlin.rx.result

class NavigationPresenter : BasePresenter<NavigationContract.Model, NavigationContract.View>(),
    NavigationContract.Presenter {

    override fun createMode(): NavigationContract.Model? = NavigationModel()

    override fun getNavis() {
        mMode?.getNavis()?.result(mMode, mView, {
            mView?.setNaviView(it.data)
        }, isShowLoading = true)
    }
}