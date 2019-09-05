package com.ztl.kotlin.ui.fragment

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.ztl.kotlin.R
import com.ztl.kotlin.base.BaseMvpFragment
import com.ztl.kotlin.mvp.contract.ProjectListContract
import com.ztl.kotlin.mvp.model.bean.Article
import com.ztl.kotlin.mvp.model.bean.ArticleList
import com.ztl.kotlin.mvp.presenter.ProjectListPresenter
import com.ztl.kotlin.ui.activity.DetailActivity
import com.ztl.kotlin.ui.activity.LoginActivity
import com.ztl.kotlin.ui.activity.MainActivity
import com.ztl.kotlin.ui.adapter.ProjectAdapter
import com.ztl.kotlin.utils.Constant
import com.ztl.kotlin.widget.RecyclerViewItemDectoration
import kotlinx.android.synthetic.main.fragment_recyclerview_layout.*

class ProjectListFragment :
    BaseMvpFragment<ProjectListContract.View, ProjectListContract.Presenter>(),
    ProjectListContract.View {

    companion object {
        fun getInstance(cid: Int): ProjectListFragment {

            val fragment = ProjectListFragment()
            val bundle = Bundle()
            bundle.putInt(Constant.CONTENT_CID_KEY, cid)
            fragment.arguments = bundle
            return fragment
        }

    }

    private var cid: Int = 0

    private val datas = mutableListOf<Article>()

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    private val recyclerViewItemDecoration by lazy {
        activity?.let {
            RecyclerViewItemDectoration(it)
        }
    }

    private val projectAdapter: ProjectAdapter by lazy {
        ProjectAdapter(activity, datas)
    }

    override fun createPresenter(): ProjectListContract.Presenter = ProjectListPresenter()

    override fun layoutRes(): Int = R.layout.fragment_recyclerview_layout

    override fun loadData() {
        mPresenter?.getProjectList(1, cid)
    }

    private  var isRefresh = false
    override fun initView(view: View) {
        super.initView(view)

        cid = arguments?.getInt(Constant.CONTENT_CID_KEY) ?: -1

        fragment_swipe_layout.setOnRefreshListener {
            isRefresh = true
            projectAdapter.setEnableLoadMore(false)
            mPresenter?.getProjectList(1, cid)
        }

        projectAdapter.run {
            setOnLoadMoreListener({
                isRefresh = false
                fragment_swipe_layout.isRefreshing = false
                val page = projectAdapter.data.size / 15 + 1
                mPresenter?.getProjectList(page, cid)
            }, fragment_recyclerview)

            setOnItemClickListener{
                _, _, position ->
                if (datas.size >= 0) {
                    val data = datas[position]
                    val bundle = Bundle()
                    bundle.putString(Constant.CONTENT_URL_KEY, data.link)
                    bundle.putString(Constant.CONTENT_TITLE_KEY, data.title)
                    bundle.putInt(Constant.CONTENT_ID_KEY, data.id)
                    bundle.putBoolean(Constant.CONTENT_FAVORITE_KEY, data.collect)

                    (activity as MainActivity).start<DetailActivity>(bundle)
                }
            }

            setOnItemChildClickListener {
                _, view, position ->
                if (datas.size > 0) {
                    val article = datas[position]

                    when(view.id) {
                        R.id.project_favorite -> {
                            if (isLogin) {
                                val collect = article.collect
                                article.collect = !collect
                                projectAdapter.setData(position, article)
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

        fragment_recyclerview.run {
            layoutManager = linearLayoutManager
            adapter = projectAdapter
            itemAnimator = DefaultItemAnimator()
            recyclerViewItemDecoration?.let {
                addItemDecoration(it)
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

    override fun setProjectList(articles: ArticleList) {
        articles.datas.let {
            projectAdapter.run {
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

    override fun hideLoading() {
        super.hideLoading()

        fragment_swipe_layout.isRefreshing = false
        if (isRefresh) {
            projectAdapter.setEnableLoadMore(true)
        }
    }

    override fun onAddFavorite(success: Boolean) {
        showMessage("收藏成功")
    }

    override fun onDelFavorite(success: Boolean) {
        showMessage("取消收藏")
    }
}