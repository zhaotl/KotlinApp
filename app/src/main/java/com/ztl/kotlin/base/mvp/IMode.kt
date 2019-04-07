package com.ztl.kotlin.base.mvp

import io.reactivex.disposables.Disposable

interface IMode {

    fun addDisposable(disposable: Disposable)

    fun onDetach()
}