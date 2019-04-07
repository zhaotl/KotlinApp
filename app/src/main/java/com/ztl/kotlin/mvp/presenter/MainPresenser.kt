package com.ztl.kotlin.mvp.presenter

import com.ztl.kotlin.base.BasePresenter
import com.ztl.kotlin.mvp.contract.MainContract
import com.ztl.kotlin.mvp.model.MainModel
import com.ztl.kotlin.rx.result

class MainPresenser: BasePresenter<MainContract.Model, MainContract.View>(), MainContract.Presenser {
    override fun createMode(): MainContract.Model? = MainModel()

    override fun logout() {
        mMode?.logout()?.result(mMode, mView, false, {
            mView?.onLogout(true)
        }, {
            mView?.onLogout(false)
        })
    }
}