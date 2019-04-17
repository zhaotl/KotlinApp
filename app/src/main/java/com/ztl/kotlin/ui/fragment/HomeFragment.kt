package com.ztl.kotlin.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import com.bumptech.glide.Glide
import com.ztl.kotlin.R
import com.ztl.kotlin.base.BaseMvpFragment
import com.ztl.kotlin.mvp.contract.HomeContract
import com.ztl.kotlin.mvp.model.bean.Article
import com.ztl.kotlin.mvp.model.bean.ArticleList
import com.ztl.kotlin.mvp.model.bean.Banner
import com.ztl.kotlin.mvp.presenter.HomePresenter
import com.ztl.kotlin.ui.adapter.HomeAdapter
import com.ztl.kotlin.utils.GlideLoader

class HomeFragment: BaseMvpFragment<HomeContract.View, HomeContract.Presenter>(), HomeContract.View {

    private var articles = mutableListOf<Article>()
    private lateinit var banners: ArrayList<Banner>
    private var bannerView: View? = null

    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(activity, articles)
    }

    private val bannerAdaper: BGABanner.Adapter<ImageView, String> by lazy {
        BGABanner.Adapter<ImageView, String> { bgaBanner, imageView, feedImageUrl, position ->
            GlideLoader.load(activity, feedImageUrl, imageView)
        }
    }

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

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