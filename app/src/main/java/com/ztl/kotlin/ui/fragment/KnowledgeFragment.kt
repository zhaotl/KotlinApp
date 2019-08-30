package com.ztl.kotlin.ui.fragment

import android.os.Bundle
import android.view.View
import com.ztl.kotlin.R
import com.ztl.kotlin.base.BaseMvpFragment
import com.ztl.kotlin.mvp.contract.KnowledgeContract
import com.ztl.kotlin.mvp.model.bean.Article
import com.ztl.kotlin.mvp.model.bean.ArticleList
import com.ztl.kotlin.mvp.presenter.KnowledgePresenter
import com.ztl.kotlin.ui.adapter.KnowledgeListAdapter
import com.ztl.kotlin.utils.Constant
import com.ztl.kotlin.widget.decoration.CommonDecoration

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

    private val articles = mutableListOf<Article>()

    private val recyclerViewDecoration by lazy {
        activity?.let {
            CommonDecoration(it, CommonDecoration.VERTICAL, R.drawable.line_divider)
        }
    }

    override fun createPresenter(): KnowledgeContract.Presenter = KnowledgePresenter()

    override fun layoutRes(): Int = R.layout.fragment_recyclerview_layout

    override fun loadData() {
        mPresenter?.getSharedArticleList(0, cid)
    }

    override fun initView(view: View) {
        super.initView(view)
    }

    override fun setSharedArticleList(articles: ArticleList) {

    }

    override fun onAddFavorite(success: Boolean) {
    }

    override fun onDelFavorite(success: Boolean) {
    }
}