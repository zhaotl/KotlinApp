package com.ztl.kotlin.mvp.presenter

import com.ztl.kotlin.mvp.contract.HomeContract
import com.ztl.kotlin.mvp.model.HomeModel
import com.ztl.kotlin.mvp.model.bean.Article
import com.ztl.kotlin.mvp.model.bean.ArticleList
import com.ztl.kotlin.mvp.model.bean.HttpResult
import com.ztl.kotlin.rx.result
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class HomePresenter: FavoritePresenter<HomeContract.Model, HomeContract.View>(), HomeContract.Presenter{

    override fun createMode(): HomeContract.Model? = HomeModel()

    override fun getBanner() {
        mMode?.getBanner()?.result(mMode, mView, {
            mView?.showBanner(it.data)
        })
    }

    override fun getArticles(index: Int) {
        mMode?.getNormalArticles(index)?.result(mMode, mView, {
            mView?.showArticles(it.data)
        })
    }

    override fun getHomeData() {

        getBanner()

        Observable.zip(mMode?.getTopArticles(), mMode?.getNormalArticles(0),
            BiFunction<HttpResult<MutableList<Article>>, HttpResult<ArticleList>, 
                    HttpResult<ArticleList>> {tops, normals ->
                tops.data.forEach{
                    it.top = "1"
                }

                normals.data.datas.addAll(0, tops.data)

                normals

            })
            .result(mMode, mView, {
                mView?.showArticles(it.data)
            })

    }

}