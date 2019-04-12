package com.ztl.kotlin.ui.fragment

import com.ztl.kotlin.R
import com.ztl.kotlin.base.BaseMvpFragment
import com.ztl.kotlin.mvp.contract.HomeContract
import com.ztl.kotlin.mvp.model.bean.ArticleList
import com.ztl.kotlin.mvp.model.bean.Banner
import com.ztl.kotlin.mvp.presenter.HomePresenter

class HomeFragment: BaseMvpFragment<HomeContract.View, HomeContract.Presenter>(), HomeContract.View {

    override fun createPresenter(): HomeContract.Presenter = HomePresenter()

    override fun layoutRes(): Int = R.layout.fragment_home_layout

    override fun loadData() {
    }

    override fun showBanner(banners: List<Banner>) {
    }

    override fun showTopArticles(articles: ArticleList) {
    }

    override fun scrollToTop() {
    }

    override fun onAddFavorite(success: Boolean) {
    }

    override fun onDelFavorite(success: Boolean) {

    }
}