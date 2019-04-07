package com.ztl.kotlin.mvp.contract

import com.ztl.kotlin.base.mvp.IMode
import com.ztl.kotlin.base.mvp.IPresenter
import com.ztl.kotlin.base.mvp.IView
import com.ztl.kotlin.mvp.model.bean.HttpResult
import com.ztl.kotlin.mvp.model.bean.LoginData
import io.reactivex.Observable

interface RegisterContract {

    interface View: IView {
        fun onRegisterSuccess(data: LoginData)
    }

    interface Model: IMode {
        fun register(username:String, pwd:String, repeatPwd: String): Observable<HttpResult<LoginData>>
    }

    interface Presenter: IPresenter<View> {
        fun register(username: String, pwd: String, repeatPwd: String)
    }
}