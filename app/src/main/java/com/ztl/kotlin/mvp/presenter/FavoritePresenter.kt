package com.ztl.kotlin.mvp.presenter

import com.ztl.kotlin.base.BasePresenter
import com.ztl.kotlin.mvp.contract.FavoriteContract
import com.ztl.kotlin.mvp.model.FavoriteModel
import com.ztl.kotlin.rx.result

open class FavoritePresenter<M: FavoriteContract.Model, V: FavoriteContract.View>
    : BasePresenter<M, V>(), FavoriteContract.Presenter<V> {

    override fun createMode(): M? = null

    override fun addFavorite(id: Int) {
        mMode?.addFavorite(id)?.result(mMode, mView, {
            mView?.onAddFavorite(true)
        }, {
            mView?.onAddFavorite(false)
        }, false)
    }

    override fun delFavorite(id: Int) {
        mMode?.delFavorite(id)?.result(mMode, mView, {
            mView?.onDelFavorite(true)
        }, {
            mView?.onDelFavorite(false)
        }, false)
    }
}