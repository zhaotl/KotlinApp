package com.ztl.kotlin.mvp.contract

import com.ztl.kotlin.base.mvp.IMode
import com.ztl.kotlin.base.mvp.IPresenter
import com.ztl.kotlin.base.mvp.IView
import com.ztl.kotlin.mvp.model.bean.HttpResult
import io.reactivex.Observable

interface MainContract {

    interface Model: IMode {
        fun logout() : Observable<HttpResult<Any>>
    }

    interface View: IView {
        fun onLogout(success: Boolean)
    }

    interface Presenter: IPresenter<View> {
        fun logout()
    }
}