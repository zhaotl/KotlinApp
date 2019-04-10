package com.ztl.kotlin.mvp.contract

import com.ztl.kotlin.base.mvp.IMode
import com.ztl.kotlin.base.mvp.IPresenter
import com.ztl.kotlin.base.mvp.IView

interface HomeContract {

    interface Model: IMode {

    }

    interface View: IView {

    }

    interface Presenter: IPresenter<View> {

    }
}