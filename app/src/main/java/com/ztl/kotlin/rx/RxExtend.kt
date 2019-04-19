package com.ztl.kotlin.rx

import com.cxz.wanandroid.rx.scheduler.IoMainScheduler
import com.ztl.kotlin.base.mvp.IMode
import com.ztl.kotlin.base.mvp.IView
import com.ztl.kotlin.http.api.Apis
import com.ztl.kotlin.mvp.model.bean.BaseBean
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

fun <T: BaseBean> Observable<T>.result(
    mode: IMode?,
    view: IView?,
    onSuccess: (T) -> Unit,
    onFailed: (String) -> Unit = {},
    isShowLoading: Boolean = false
) {
    this.compose(IoMainScheduler())
        .subscribe(object: Observer<T> {
            override fun onComplete() {
                view?.hideLoading()
            }

            override fun onSubscribe(d: Disposable) {
                if (isShowLoading) {
                    view?.showLoading()
                }

                mode?.addDisposable(d)

            }

            override fun onNext(t: T) {
                when {
                    t.errorCode == Apis.ErrorCode.SUCCESS -> onSuccess.invoke(t)
                    else -> onFailed.invoke(t.errorMsg)
                }
            }

            override fun onError(e: Throwable) {
                e.message?.let { onFailed.invoke(it) }
            }
        })

}