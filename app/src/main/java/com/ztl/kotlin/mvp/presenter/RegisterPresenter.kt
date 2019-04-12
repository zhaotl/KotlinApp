package com.ztl.kotlin.mvp.presenter

import com.ztl.kotlin.base.BasePresenter
import com.ztl.kotlin.mvp.contract.RegisterContract
import com.ztl.kotlin.mvp.model.RegisterModel
import com.ztl.kotlin.rx.result

class RegisterPresenter: BasePresenter<RegisterContract.Model, RegisterContract.View>(), RegisterContract.Presenter {

    override fun createMode(): RegisterContract.Model? = RegisterModel()

    override fun register(username: String, pwd: String, repeatPwd: String) {
        mMode?.register(username, pwd, repeatPwd)?.result(mMode, mView, {
            mView?.onRegisterSuccess(it.data)
        }, {
            mView?.showError(it)
        })
    }
}