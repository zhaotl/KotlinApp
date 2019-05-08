package com.ztl.kotlin.mvp.presenter

import com.ztl.kotlin.base.BasePresenter
import com.ztl.kotlin.mvp.contract.LoginContract
import com.ztl.kotlin.mvp.model.LoginModel
import com.ztl.kotlin.mvp.model.bean.HttpResult
import com.ztl.kotlin.mvp.model.bean.LoginData
import com.ztl.kotlin.rx.result
import com.ztl.kotlin.utils.KLogger

class LoginPresenter: BasePresenter<LoginContract.Model, LoginContract.View>(), LoginContract.Presenter {

    override fun createMode(): LoginContract.Model? = LoginModel()

    override fun login(username: String, password: String) {
        KLogger.d("Presenser Login")

        mMode?.login(username, password)?.result(mMode, mView, {
            mView?.onLoginSuccess(it.data)
        }, {
            mView?.onLoginFailed(it)
        }, isShowLoading = true)
    }

}