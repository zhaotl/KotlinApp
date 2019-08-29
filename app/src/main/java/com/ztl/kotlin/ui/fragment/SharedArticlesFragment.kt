package com.ztl.kotlin.ui.fragment

import com.ztl.kotlin.R
import com.ztl.kotlin.base.BaseMvpFragment
import com.ztl.kotlin.mvp.contract.SharedArticlesContract
import com.ztl.kotlin.mvp.model.bean.SharedArticles
import com.ztl.kotlin.mvp.presenter.SharedArticlesPresenter

class SharedArticlesFragment: BaseMvpFragment<SharedArticlesContract.View, SharedArticlesContract.Presenter>(), SharedArticlesContract.View {

    companion object {
        fun getInstance(): SharedArticlesFragment = SharedArticlesFragment()
    }

    private val datas = mutableListOf<SharedArticles>()



    override fun createPresenter(): SharedArticlesContract.Presenter = SharedArticlesPresenter()

    override fun layoutRes(): Int = R.layout.shared_articles

    override fun loadData() {
        mPresenter?.getSharedArticles()
    }

    override fun showSharedArticles(articles: MutableList<SharedArticles>) {

    }
}