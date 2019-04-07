package com.ztl.kotlin.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import com.ztl.kotlin.base.mvp.IMode
import com.ztl.kotlin.base.mvp.IPresenter
import com.ztl.kotlin.base.mvp.IView
import com.ztl.kotlin.mvp.model.bean.BaseBean
import com.ztl.kotlin.mvp.model.bean.HttpResult
import org.greenrobot.eventbus.EventBus

abstract class BasePresenter<M: IMode, V: IView>: IPresenter<V>, LifecycleObserver {

    protected var mMode: M? = null
    protected var mView: V? = null

    open fun enableEventBus(): Boolean = false

    abstract fun createMode(): M?

    override fun attachView(view: V) {
        this.mView = view
        mMode = createMode()

        if (view is LifecycleOwner) {
            (mView as LifecycleOwner).lifecycle.addObserver(this)
            if (mMode != null && mMode is LifecycleObserver) {
                (mView as LifecycleOwner).lifecycle.addObserver(mMode as  LifecycleObserver)
            }
        }

        if (enableEventBus()) {
            EventBus.getDefault().register(this)
        }
    }

    override fun detachView() {
        if (enableEventBus()) {
            EventBus.getDefault().unregister(this)
        }

        mMode?.onDetach()
        mMode = null
        mView = null

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
    }

    fun onLoadDataSuccess(): (HttpResult<BaseBean>) -> Unit = {

    }
}