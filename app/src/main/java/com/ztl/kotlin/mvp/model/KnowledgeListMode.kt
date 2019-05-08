package com.ztl.kotlin.mvp.model

import com.ztl.kotlin.base.BaseMode
import com.ztl.kotlin.http.retrofit.RetrofitHelper
import com.ztl.kotlin.mvp.contract.KnowledgeListContract
import com.ztl.kotlin.mvp.model.bean.HttpResult
import com.ztl.kotlin.mvp.model.bean.KnowledgeTree
import io.reactivex.Observable

class KnowledgeListMode : BaseMode(), KnowledgeListContract.Model {

    override fun getKnowledges(): Observable<HttpResult<List<KnowledgeTree>>> {
        return RetrofitHelper.apis.getKnowledgeList()
    }
}