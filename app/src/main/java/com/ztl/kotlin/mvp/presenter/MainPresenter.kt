package com.ztl.kotlin.mvp.presenter

import com.ztl.kotlin.base.BasePresenter
import com.ztl.kotlin.mvp.contract.MainContract
import com.ztl.kotlin.mvp.model.MainModel
import com.ztl.kotlin.rx.result

class MainPresenter: BasePresenter<MainContract.Model, MainContract.View>(), MainContract.Presenter {

    override fun createMode(): MainContract.Model? = MainModel()

    override fun logout() {
        mMode?.logout()?.result(mMode, mView, {
            mView?.onLogout(true)
        }, {
            mView?.onLogout(false)
        })
    }
}