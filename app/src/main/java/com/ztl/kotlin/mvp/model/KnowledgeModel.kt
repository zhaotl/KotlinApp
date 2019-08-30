package com.ztl.kotlin.mvp.model

import com.ztl.kotlin.http.retrofit.RetrofitHelper
import com.ztl.kotlin.mvp.contract.KnowledgeContract
import com.ztl.kotlin.mvp.model.bean.ArticleList
import com.ztl.kotlin.mvp.model.bean.HttpResult
import io.reactivex.Observable

class KnowledgeModel : FavoriteModel(), KnowledgeContract.Model {

    override fun getSharedArticleList(page: Int, cid: Int): Observable<HttpResult<ArticleList>> {
        return RetrofitHelper.apis.getKnoledgeCategory(page, cid)
    }
}