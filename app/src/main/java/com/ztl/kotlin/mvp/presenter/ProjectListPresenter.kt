package com.ztl.kotlin.mvp.presenter

import com.ztl.kotlin.mvp.contract.ProjectListContract
import com.ztl.kotlin.mvp.model.ProjectListModel
import com.ztl.kotlin.rx.result

class ProjectListPresenter : FavoritePresenter<ProjectListContract.Model, ProjectListContract.View>(),
    ProjectListContract.Presenter {

    override fun createMode(): ProjectListContract.Model? = ProjectListModel()

    override fun getProjectList(page: Int, cid: Int) {
        mMode?.getProjectList(page, cid)?.result(mMode, mView, {
            mView?.setProjectList(it.data)
        }, isShowLoading = true)
    }
}