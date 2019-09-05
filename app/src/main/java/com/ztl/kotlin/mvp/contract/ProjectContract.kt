package com.ztl.kotlin.mvp.contract

import com.ztl.kotlin.base.mvp.IMode
import com.ztl.kotlin.base.mvp.IPresenter
import com.ztl.kotlin.base.mvp.IView
import com.ztl.kotlin.mvp.model.bean.HttpResult
import com.ztl.kotlin.mvp.model.bean.NaviData
import com.ztl.kotlin.mvp.model.bean.ProjectData
import io.reactivex.Observable

interface ProjectContract {

    interface Model: IMode{
        fun getProjects(): Observable<HttpResult<MutableList<ProjectData>>>
    }

    interface View: IView {
        fun scroll2Top()
        fun setProjects(datas: MutableList<ProjectData>)
    }

    interface Presenter: IPresenter<View> {
        fun getProjects()
    }
}