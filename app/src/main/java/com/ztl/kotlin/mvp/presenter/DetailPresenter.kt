package com.ztl.kotlin.mvp.presenter

import com.ztl.kotlin.mvp.contract.DetailContract
import com.ztl.kotlin.mvp.model.DetailModel

class DetailPresenter : FavoritePresenter<DetailContract.Model, DetailContract.View>(), DetailContract.Presenter {

    override fun createMode(): DetailContract.Model? = DetailModel()
}