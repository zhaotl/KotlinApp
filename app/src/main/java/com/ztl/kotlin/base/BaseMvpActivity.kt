package com.ztl.kotlin.base

import android.app.AlertDialog
import android.view.Gravity
import android.widget.Toast
import com.ztl.kotlin.base.mvp.IPresenter
import com.ztl.kotlin.base.mvp.IView
import com.ztl.kotlin.widget.CustomToast
import com.ztl.kotlin.widget.DialogUtil

abstract class BaseMvpActivity<in V: IView, P: IPresenter<V>>: BaseActivity(), IView {

    protected var mPresenter: P? = null

    private val progress: AlertDialog? by lazy {
        DialogUtil.getProgress(this)
    }

    private val tips: CustomToast? by lazy {
        CustomToast.Builder(this)
            .duration(Toast.LENGTH_LONG)
            .gravity(Gravity.BOTTOM)
            .build()
    }

    abstract fun createPresenter(): P

    override fun initView() {
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
        mPresenter = null
    }

    override fun showLoading() {
        progress?.show()
    }

    override fun hideLoading() {
        progress?.dismiss()
    }

    override fun showMessage(msg: String) {
        runOnUiThread({
            tips?.show(msg)
        })
    }

    override fun showError(errMsg: String) {
    }
}