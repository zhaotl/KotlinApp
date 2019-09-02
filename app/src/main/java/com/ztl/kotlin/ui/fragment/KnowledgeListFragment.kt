package com.ztl.kotlin.ui.fragment

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.BaseAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.ztl.kotlin.R
import com.ztl.kotlin.base.BaseMvpFragment
import com.ztl.kotlin.mvp.contract.KnowledgeListContract
import com.ztl.kotlin.mvp.model.bean.KnowledgeTree
import com.ztl.kotlin.mvp.presenter.KnowledgeListPresenter
import com.ztl.kotlin.ui.activity.DetailActivity
import com.ztl.kotlin.ui.activity.KnowledgeActivity
import com.ztl.kotlin.ui.activity.MainActivity
import com.ztl.kotlin.ui.adapter.KnowledgeListAdapter
import com.ztl.kotlin.utils.Constant
import com.ztl.kotlin.utils.KLogger
import com.ztl.kotlin.widget.decoration.CommonDecoration
import kotlinx.android.synthetic.main.fragment_recyclerview_layout.*

class KnowledgeListFragment : BaseMvpFragment<KnowledgeListContract.View, KnowledgeListContract.Presenter>(), KnowledgeListContract.View {

    private var isRefresh = false
    private val knowledges: MutableList<KnowledgeTree> = mutableListOf()
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    private val knowledgeListAdapter by lazy {
        KnowledgeListAdapter(knowledges)
    }

    override fun createPresenter(): KnowledgeListContract.Presenter = KnowledgeListPresenter()

    override fun layoutRes(): Int = R.layout.fragment_recyclerview_layout

    override fun loadData() {
        mPresenter?.requestKnowledges()
    }

    override fun initView(view: View) {
        super.initView(view)

        fragment_swipe_layout?.run{
            setOnRefreshListener {
                KLogger.d("start refresh ~~~~~~.")
                isRefresh = true
                mPresenter?.requestKnowledges()
            }

//            isEnabled = false
        }

        fragment_recyclerview.run {
            adapter = knowledgeListAdapter
            layoutManager = linearLayoutManager
            itemAnimator = DefaultItemAnimator()
            activity?.let {
                val itemDectoration = CommonDecoration(it, CommonDecoration.VERTICAL, R.drawable.line_divider)
                addItemDecoration(itemDectoration)
            }
        }

        knowledgeListAdapter.run {
            bindToRecyclerView(fragment_recyclerview)
            setEnableLoadMore(false)
            onItemClickListener = knowledgeItemClickListener
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

    override fun showKnowledges(list: List<KnowledgeTree>) {
        KLogger.d("showKnowledges")
        knowledgeListAdapter.replaceData(list)

    }

    private val knowledgeItemClickListener = BaseQuickAdapter.OnItemClickListener {_, _, position ->

        if (knowledges.size > 0) {
            val knowledge = knowledges[position]
            val bundle = Bundle()
            bundle.putSerializable(Constant.CONST_KNOWLEDGE_DATA, knowledge)
            (activity as MainActivity).start<KnowledgeActivity>(bundle)
        }
    }

    override fun hideLoading() {
        super.hideLoading()
        fragment_swipe_layout?.isRefreshing = false
    }
}