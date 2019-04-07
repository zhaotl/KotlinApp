package com.ztl.kotlin.base.mvp

interface IView {

    fun showLoading()

    fun hideLoading()

    fun showMessage(msg: String)

    fun showError(errMsg: String)

}