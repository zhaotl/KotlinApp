package com.ztl.kotlin.mvp.presenter

import com.ztl.kotlin.base.BasePresenter
import com.ztl.kotlin.mvp.contract.ProjectContract
import com.ztl.kotlin.mvp.model.ProjectModel
import com.ztl.kotlin.rx.result

class ProjectPresenter : BasePresenter<ProjectContract.Model, ProjectContract.View>(),
    ProjectContract.Presenter {

    override fun createMode(): ProjectContract.Model? = ProjectModel()

    override fun getProjects() {
        mMode?.getProjects()?.result(mMode, mView, {
            mView?.setProjects(it.data)
        }, isShowLoading = true)
    }
}