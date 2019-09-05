package com.ztl.kotlin.mvp.model

import com.ztl.kotlin.base.BaseMode
import com.ztl.kotlin.http.retrofit.RetrofitHelper
import com.ztl.kotlin.mvp.contract.ProjectContract
import com.ztl.kotlin.mvp.model.bean.HttpResult
import com.ztl.kotlin.mvp.model.bean.ProjectData
import io.reactivex.Observable

class ProjectModel: BaseMode(), ProjectContract.Model {

    override fun getProjects(): Observable<HttpResult<MutableList<ProjectData>>> {
        return RetrofitHelper.apis.getProjects()
    }
}