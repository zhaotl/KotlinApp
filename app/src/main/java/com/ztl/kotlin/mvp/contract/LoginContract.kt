package com.ztl.kotlin.mvp.contract

import com.ztl.kotlin.base.mvp.IMode
import com.ztl.kotlin.base.mvp.IPresenter
import com.ztl.kotlin.base.mvp.IView
import com.ztl.kotlin.mvp.model.bean.HttpResult
import com.ztl.kotlin.mvp.model.bean.LoginData
import io.reactivex.Observable

interface LoginContract {

    interface View: IView {
        fun onLoginSuccess(data: LoginData)
        fun onLoginFailed(errMsg: String)
    }

    interface Presenter: IPresenter<View> {
        fun login(username: String, password: String)
    }

    interface Model: IMode {
        fun login(username: String, password: String): Observable<HttpResult<LoginData>>
    }

}