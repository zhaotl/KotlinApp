package com.ztl.kotlin.base

import android.app.admin.NetworkEvent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ztl.kotlin.app.App
import com.ztl.kotlin.event.NetworkChangeEvent
import com.ztl.kotlin.utils.Constant
import com.ztl.kotlin.utils.Preferences
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseFragment: Fragment() {

    protected var isLogin: Boolean by Preferences(Constant.CONST_ISLOGIN_KEY, false)

    private var isViewPrepared = false
    private var hasLoadData = false

    abstract fun layoutRes(): Int
    abstract fun initView(view: View)
    abstract fun loadData()

    open fun enableEventBus(): Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(layoutRes(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (enableEventBus()) {
            EventBus.getDefault().register(this)
        }

        isViewPrepared = true

        initView(view)
        startLoadData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            startLoadData()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetWorkChangeEvent(event: NetworkChangeEvent) {
        if (event.isConnected) {
            startLoadData()
        }
    }

    private fun startLoadData() {
        if (isViewPrepared && userVisibleHint && !hasLoadData) {
            loadData()
            hasLoadData = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (enableEventBus()) {
            EventBus.getDefault().unregister(this)
        }

        activity?.let {
            App.getRefWatcher(it)?.watch(it)
        }
    }
}