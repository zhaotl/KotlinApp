package com.ztl.kotlin.mvp.model

import com.ztl.kotlin.base.BaseMode
import com.ztl.kotlin.http.retrofit.RetrofitHelper
import com.ztl.kotlin.mvp.contract.ProjectListContract
import com.ztl.kotlin.mvp.model.bean.ArticleList
import com.ztl.kotlin.mvp.model.bean.HttpResult
import io.reactivex.Observable

class ProjectListModel : FavoriteModel(), ProjectListContract.Model {

    override fun getProjectList(page: Int, cid: Int): Observable<HttpResult<ArticleList>> {
        return RetrofitHelper.apis.getProjectList(page, cid)
    }
}