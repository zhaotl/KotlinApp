package com.ztl.kotlin.ui.fragment

import android.support.design.widget.TabLayout
import android.view.View
import com.ztl.kotlin.R
import com.ztl.kotlin.base.BaseMvpFragment
import com.ztl.kotlin.mvp.contract.SharedArticlesContract
import com.ztl.kotlin.mvp.model.bean.SharedArticles
import com.ztl.kotlin.mvp.presenter.SharedArticlesPresenter
import com.ztl.kotlin.ui.adapter.SharedArticlesAdapter
import com.ztl.kotlin.utils.KLogger
import kotlinx.android.synthetic.main.fragment_recyclerview_layout.*
import kotlinx.android.synthetic.main.shared_articles.*

class SharedArticlesFragment: BaseMvpFragment<SharedArticlesContract.View, SharedArticlesContract.Presenter>(), SharedArticlesContract.View {

    companion object {
        fun getInstance(): SharedArticlesFragment = SharedArticlesFragment()
    }

    private val datas = mutableListOf<SharedArticles>()

    override fun createPresenter(): SharedArticlesContract.Presenter = SharedArticlesPresenter()

    override fun layoutRes(): Int = R.layout.shared_articles

    private val viewPageAdapter: SharedArticlesAdapter by lazy {
        SharedArticlesAdapter(datas, childFragmentManager)
    }

    override fun loadData() {
        KLogger.d("SharedArticles loadData")
        mPresenter?.getSharedArticles()
    }

    override fun initView(view: View) {
        super.initView(view)

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tablayout))
        tablayout.run {
            setupWithViewPager(viewPager)
            addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
            addOnTabSelectedListener(tabSelectedListener)
        }
    }

    override fun scroll2Top() {
        if (viewPageAdapter.count == 0) {
            return
        }

        val fragment = viewPageAdapter.getItem(viewPager.currentItem)
        (fragment as KnowledgeFragment).scrollToTop()
    }

    override fun showSharedArticles(articles: MutableList<SharedArticles>) {
        KLogger.d("show subscribe articles")
        articles.let {
            datas.addAll(articles)
            viewPager.run {
                adapter = viewPageAdapter
                offscreenPageLimit = datas.size
            }
        }
    }

    private val tabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.let {
                viewPager.setCurrentItem(it.position, false)
            }
        }
    }
}