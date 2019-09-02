package com.ztl.kotlin.ui.fragment

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.ztl.kotlin.R
import com.ztl.kotlin.base.BaseMvpFragment
import com.ztl.kotlin.mvp.contract.KnowledgeContract
import com.ztl.kotlin.mvp.model.bean.Article
import com.ztl.kotlin.mvp.model.bean.ArticleList
import com.ztl.kotlin.mvp.presenter.KnowledgePresenter
import com.ztl.kotlin.ui.activity.DetailActivity
import com.ztl.kotlin.ui.activity.LoginActivity
import com.ztl.kotlin.ui.activity.MainActivity
import com.ztl.kotlin.ui.adapter.KnowledgeAdapter
import com.ztl.kotlin.ui.adapter.KnowledgeListAdapter
import com.ztl.kotlin.utils.Constant
import com.ztl.kotlin.widget.decoration.CommonDecoration
import kotlinx.android.synthetic.main.fragment_recyclerview_layout.*

class KnowledgeFragment : BaseMvpFragment<KnowledgeContract.View, KnowledgeContract.Presenter>()
    , KnowledgeContract.View {

    companion object {
        fun getInstance(cid: Int): KnowledgeFragment {
            val frament = KnowledgeFragment()
            val bundle = Bundle()
            bundle.putInt(Constant.CONTENT_CID_KEY, cid)
            frament.arguments = bundle
            return frament
        }
    }

    private var cid: Int = 0
    private var isRefresh = true

    private val articles = mutableListOf<Article>()

    private val recyclerViewDecoration by lazy {
        activity?.let {
            CommonDecoration(it, CommonDecoration.VERTICAL, R.drawable.line_divider)
        }
    }

    private val knowledgeAdapter: KnowledgeAdapter by lazy {
        KnowledgeAdapter(activity, articles)
    }

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    override fun createPresenter(): KnowledgeContract.Presenter = KnowledgePresenter()

    override fun layoutRes(): Int = R.layout.fragment_recyclerview_layout

    override fun loadData() {
        mPresenter?.getSharedArticleList(0, cid)
    }

    override fun initView(view: View) {
        super.initView(view)

        cid = arguments?.getInt(Constant.CONTENT_CID_KEY) ?: 0

        fragment_swipe_layout.setOnRefreshListener {
            isRefresh = true
            knowledgeAdapter.setEnableLoadMore(false)
            mPresenter?.getSharedArticleList(0, cid)
        }

        fragment_recyclerview.run {
            adapter = knowledgeAdapter
            layoutManager = linearLayoutManager
            itemAnimator = DefaultItemAnimator()
            recyclerViewDecoration?.let {
                addItemDecoration(it)
            }
        }

        knowledgeAdapter.run {
            onItemClickListener = itemClickListener
            onItemChildClickListener = childClickListener
            setOnLoadMoreListener(refreshListener, fragment_recyclerview)
        }
    }

    override fun setSharedArticleList(articles: ArticleList) {
        articles.datas.let {
            knowledgeAdapter.run {
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

        fragment_swipe_layout?.isRefreshing = false
        knowledgeAdapter.run {
            if (isRefresh) {
                setEnableLoadMore(true)
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

    private val refreshListener = BaseQuickAdapter.RequestLoadMoreListener {
        isRefresh = false
        fragment_swipe_layout.isRefreshing = false
        val page = knowledgeAdapter.data.size / 20
        mPresenter?.getSharedArticleList(page, cid)
    }

    private val itemClickListener = BaseQuickAdapter.OnItemClickListener {
        _, _, position ->
        if (articles.size > 0) {
            val article = articles[position]
            var bundle = Bundle()
            bundle.putString(Constant.CONTENT_URL_KEY, article.link)
            bundle.putString(Constant.CONTENT_TITLE_KEY, article.title)
            bundle.putInt(Constant.CONTENT_ID_KEY, article.id)
            bundle.putBoolean(Constant.CONTENT_FAVORITE_KEY, article.collect)

            (activity as MainActivity).start<DetailActivity>(bundle)
        }
    }

    private val childClickListener = BaseQuickAdapter.OnItemChildClickListener {
        _, view, position ->
        if (articles.size > 0) {
            val article = articles[position]

            when(view.id) {
                R.id.fragment_iv_like -> {
                    if (isLogin) {
                        val collect = article.collect
                        article.collect = !collect
                        knowledgeAdapter.setData(position, article)
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