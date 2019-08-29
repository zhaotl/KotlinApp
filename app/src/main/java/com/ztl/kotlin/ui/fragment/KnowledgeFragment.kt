package com.ztl.kotlin.ui.fragment

import com.ztl.kotlin.base.BaseMvpFragment
import com.ztl.kotlin.mvp.contract.KnowledgeListContract
import com.ztl.kotlin.mvp.model.bean.KnowledgeTree

class KnowledgeFragment : BaseMvpFragment<KnowledgeListContract.View, KnowledgeListContract.Presenter>()
        , KnowledgeListContract.View {

    override fun createPresenter(): KnowledgeListContract.Presenter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun layoutRes(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun scrollToTop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showKnowledges(list: List<KnowledgeTree>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}