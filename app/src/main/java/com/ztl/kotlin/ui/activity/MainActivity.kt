package com.ztl.kotlin.ui.activity

import android.content.SharedPreferences
import com.ztl.kotlin.R
import com.ztl.kotlin.base.BaseMvpActivity
import com.ztl.kotlin.mvp.contract.MainContract
import com.ztl.kotlin.mvp.presenter.MainPresenter
import com.ztl.kotlin.utils.Constant
import com.ztl.kotlin.utils.Preferences

class MainActivity: BaseMvpActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {

    private var user: String by Preferences(Constant.CONST_USERNAME_KEY, "")

    override fun enableEventBus(): Boolean = false

    override fun createPresenter(): MainContract.Presenter = MainPresenter()

    override fun layouRes(): Int = R.layout.activity_maintab

    override fun onLogout(success: Boolean) {
        if (success == true) {
            user = ""
        }
    }
}