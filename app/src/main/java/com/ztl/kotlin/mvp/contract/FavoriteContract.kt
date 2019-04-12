package com.ztl.kotlin.mvp.contract

import com.ztl.kotlin.base.mvp.IMode
import com.ztl.kotlin.base.mvp.IPresenter
import com.ztl.kotlin.base.mvp.IView
import com.ztl.kotlin.mvp.model.bean.HttpResult
import io.reactivex.Observable

interface FavoriteContract {

    interface Model: IMode {
        fun addFavorite(id: Int): Observable<HttpResult<Any>>
        fun delFavorite(id: Int): Observable<HttpResult<Any>>
    }

    interface View: IView {
        fun onAddFavorite(success: Boolean)
        fun onDelFavorite(success:Boolean)
    }

    interface Presenter<in V: View>: IPresenter<V> {
        fun addFavorite(id: Int)
        fun delFavorite(id: Int)
    }
}