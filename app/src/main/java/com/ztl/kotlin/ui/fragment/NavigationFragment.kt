package com.ztl.kotlin.ui.fragment

import com.ztl.kotlin.base.BaseMvpFragment
import com.ztl.kotlin.mvp.contract.NavigationContract
import com.ztl.kotlin.mvp.model.bean.NaviData
import com.ztl.kotlin.mvp.presenter.NavigationPresenter

class NavigationFragment : BaseMvpFragment<NavigationContract.View, NavigationContract.Presenter>(),
    NavigationContract.View {

    companion object {
        fun getInstance() = NavigationFragment()
    }

    private val datas = mutableListOf<NaviData>()

    override fun createPresenter(): NavigationContract.Presenter = NavigationPresenter()

    override fun layoutRes(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadData() {
        mPresenter?.getNavis()
    }

    override fun setNaviView(navis: MutableList<NaviData>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}