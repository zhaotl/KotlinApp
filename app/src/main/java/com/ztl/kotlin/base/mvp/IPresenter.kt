package com.ztl.kotlin.base.mvp

interface IPresenter<in V: IView> {

    fun attachView(view: V)

    fun detachView()
}