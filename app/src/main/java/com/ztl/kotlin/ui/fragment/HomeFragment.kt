package com.ztl.kotlin.ui.fragment

import com.ztl.kotlin.base.BaseMvpFragment
import com.ztl.kotlin.mvp.contract.HomeContract
import com.ztl.kotlin.mvp.model.bean.ArticleList
import com.ztl.kotlin.mvp.model.bean.Banner

class HomeFragment: BaseMvpFragment<HomeContract.View, HomeContract.Presenter>(), HomeContract.View {

    override fun createPresenter(): HomeContract.Presenter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun layoutRes(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showBanner(banners: List<Banner>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showTopArticles(articles: ArticleList) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun scrollToTop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAddFavorite(success: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDelFavorite(success: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}