package com.ztl.kotlin.mvp.contract

import com.ztl.kotlin.base.mvp.IMode
import com.ztl.kotlin.base.mvp.IPresenter
import com.ztl.kotlin.base.mvp.IView
import com.ztl.kotlin.mvp.model.bean.HttpResult
import com.ztl.kotlin.mvp.model.bean.NaviData
import io.reactivex.Observable

interface NavigationContract {

    interface Model: IMode {
        fun getNavis(): Observable<HttpResult<MutableList<NaviData>>>
    }

    interface View: IView {
        fun setNaviView(navis: MutableList<NaviData>)
    }

    interface Presenter: IPresenter<View> {
        fun getNavis()
    }
}