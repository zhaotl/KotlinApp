package com.ztl.kotlin.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import com.chad.library.adapter.base.BaseQuickAdapter
import com.ztl.kotlin.R
import com.ztl.kotlin.base.BaseActivity
import com.ztl.kotlin.base.BaseMvpFragment
import com.ztl.kotlin.mvp.contract.HomeContract
import com.ztl.kotlin.mvp.model.bean.Article
import com.ztl.kotlin.mvp.model.bean.ArticleList
import com.ztl.kotlin.mvp.model.bean.Banner
import com.ztl.kotlin.mvp.presenter.HomePresenter
import com.ztl.kotlin.ui.activity.DetailActivity
import com.ztl.kotlin.ui.activity.LoginActivity
import com.ztl.kotlin.ui.activity.MainActivity
import com.ztl.kotlin.ui.adapter.HomeAdapter
import com.ztl.kotlin.utils.Constant
import com.ztl.kotlin.utils.GlideLoader
import com.ztl.kotlin.utils.KLogger
import com.ztl.kotlin.widget.decoration.CommonDecoration
import io.reactivex.Observable
import kotlinx.android.synthetic.main.banner_item.*
import kotlinx.android.synthetic.main.fragment_recyclerview_layout.*

class HomeFragment: BaseMvpFragment<HomeContract.View, HomeContract.Presenter>(), HomeContract.View {

    private val articles = mutableListOf<Article>()
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

    override fun layoutRes(): Int = R.layout.fragment_recyclerview_layout

    override fun loadData() {
        mPresenter?.getHomeData()
    }

    override fun initView(view: View) {
        super.initView(view)

        fragment_swipe_layout?.run {
            setOnRefreshListener {
                isRefresh = true
                homeAdapter.setEnableLoadMore(false)
                mPresenter?.getHomeData()
            }
        }

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

        bannerView = layoutInflater.inflate(R.layout.banner_item, null)
        bgabanner?.setDelegate {
            banner, itemView, model, position ->
                KLogger.d("banner clicked on position $position")
        }

        homeAdapter.run {
            bindToRecyclerView(fragment_recyclerview)
            addHeaderView(bannerView)

            setOnLoadMoreListener(requestMoreListener, fragment_recyclerview)
            onItemClickListener = recyclerViewItemClick
            onItemChildClickListener = childItemClick
        }

    }

    override fun showBanner(lists: List<Banner>) {
        banners = lists as ArrayList<Banner>
        val bannerPahs = ArrayList<String>()
        val bannerTitles = ArrayList<String>()

        // RXjava
        Observable.fromIterable(lists)
            .subscribe { banner ->
                bannerPahs.add(banner.imagePath)
                bannerTitles.add(banner.title)
            }

        bgabanner?.run {
            setAutoPlayAble(bannerPahs.size > 1)
            setData(bannerPahs, bannerTitles)
            setAdapter(bannerAdaper)
        }

    }

    override fun showArticles(articles: ArticleList) {
        articles.datas?.let {
            homeAdapter.run {
                if (isRefresh) {
                    replaceData(it)
                } else {
                    addData(it)
                }

                KLogger.d("it size = ${it.size}")
                KLogger.d("articles size = ${articles.size}")
                KLogger.d("adapter size = ${homeAdapter.data.size}")

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
        if (success) {
            showMessage("收藏成功")
        }
    }

    override fun onDelFavorite(success: Boolean) {
        if (success) {
            showMessage("取消收藏")
        }
    }

    override fun hideLoading() {
        super.hideLoading()

        fragment_swipe_layout?.isRefreshing = false
        if (isRefresh) {
            homeAdapter.run {
                setEnableLoadMore(true)
            }
        }
    }

    private val requestMoreListener = BaseQuickAdapter.RequestLoadMoreListener {
        isRefresh = false
        fragment_swipe_layout.isRefreshing = false

        val index = homeAdapter.data.size / 20
        mPresenter?.getArticles(index)
    }

    private val recyclerViewItemClick = BaseQuickAdapter.OnItemClickListener {
        adapter, view, position ->
        if (articles.size > 0) {
            val article = articles[position]
            KLogger.d("article url = ${article.link}")
            KLogger.d("article title = ${article.title}")
            KLogger.d("article id = ${article.id}")
            KLogger.d("article collect = ${article.collect}")

            val bundle = Bundle()
            bundle.putInt(Constant.CONTENT_ID_KEY, article.id)
            bundle.putString(Constant.CONTENT_URL_KEY, article.link)
            bundle.putString(Constant.CONTENT_TITLE_KEY, article.title)
            bundle.putBoolean(Constant.CONTENT_FAVORITE_KEY, article.collect)
            (activity as MainActivity).start<DetailActivity>(bundle)
        }
    }

    private val childItemClick = BaseQuickAdapter.OnItemChildClickListener {
        adapter, view, position ->

        if (articles.size >= 0) {
            val article = articles[position]
            when(view.id) {
                R.id.fragment_iv_like -> {
                    if (isLogin) {
                        val collect = article.collect
                        article.collect = !collect
                        homeAdapter.setData(position, article)
                        if (collect) {
                            mPresenter?.delFavorite(article.id)
                        } else {
                            mPresenter?.addFavorite(article.id)
                        }
                    } else {
                        with(activity as MainActivity) {
                            start<LoginActivity>()
                            showMessage("请先登录")
                        }
                    }
                }
            }

        }
    }
}