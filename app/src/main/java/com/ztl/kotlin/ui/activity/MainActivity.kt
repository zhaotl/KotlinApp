package com.ztl.kotlin.ui.activity

import com.ztl.kotlin.R
import com.ztl.kotlin.base.BaseMvpActivity
import com.ztl.kotlin.mvp.contract.MainContract
import com.ztl.kotlin.mvp.presenter.MainPresenser

class MainActivity: BaseMvpActivity<MainContract.View, MainContract.Presenser>(), MainContract.View {


    override fun createPresenter(): MainContract.Presenser = MainPresenser()

    override fun layouRes(): Int = R.layout.activity_maintab

    override fun onLogout(isSuccess: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}