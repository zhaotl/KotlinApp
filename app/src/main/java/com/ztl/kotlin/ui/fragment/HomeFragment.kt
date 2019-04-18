package com.ztl.kotlin.ui.fragment

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import com.ztl.kotlin.R
import com.ztl.kotlin.base.BaseMvpFragment
import com.ztl.kotlin.mvp.contract.HomeContract
import com.ztl.kotlin.mvp.model.bean.Article
import com.ztl.kotlin.mvp.model.bean.ArticleList
import com.ztl.kotlin.mvp.model.bean.Banner
import com.ztl.kotlin.mvp.presenter.HomePresenter
import com.ztl.kotlin.ui.adapter.HomeAdapter
import com.ztl.kotlin.utils.GlideLoader
import com.ztl.kotlin.widget.decoration.CommonDecoration
import kotlinx.android.synthetic.main.fragment_home_layout.*

class HomeFragment: BaseMvpFragment<HomeContract.View, HomeContract.Presenter>(), HomeContract.View {

    private var articles = mutableListOf<Article>()
    private lateinit var banners: ArrayList<Banner>
    private var bannerView: View? = null
    private var isRefresh = true

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
        mPresenter?.getHomeData()
    }

    override fun initView(view: View) {
        super.initView(view)

        fragment_recyclerview.run {
            activity?.let {
                // 默认自定义的divider
//                val itemDectoration = CommonDecoration(it)
                // 制定divider drawerble
                val itemDectoration = CommonDecoration(it, CommonDecoration.VERTICAL, R.drawable.line_divider)
                // support 提供的divider
//                val itemDectoration = DividerItemDecoration(it, RecyclerView.VERTICAL)
                addItemDecoration(itemDectoration)
            }

            layoutManager = linearLayoutManager
            adapter = homeAdapter
            itemAnimator = DefaultItemAnimator()
        }


        homeAdapter.run {
            bindToRecyclerView(fragment_recyclerview)

        }

    }

    override fun showBanner(banners: List<Banner>) {
    }

    override fun showArticles(articles: ArticleList) {
        articles.datas?.let {
            homeAdapter.run {
                if (isRefresh) {
                    replaceData(it)
                } else {
                    addData(it)
                }

                val size = it.size
                if (size < articles.size) {
                    loadMoreEnd(isRefresh)
                } else {
                    loadMoreComplete()
                }
            }
        }
    }

    override fun scrollToTop() {

        fragment_recyclerview.run {
            if (linearLayoutManager.findFirstVisibleItemPosition() > 20) {
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
        }
    }

    override fun onAddFavorite(success: Boolean) {
    }

    override fun onDelFavorite(success: Boolean) {

    }
}