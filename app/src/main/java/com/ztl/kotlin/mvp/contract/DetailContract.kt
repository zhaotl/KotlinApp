package com.ztl.kotlin.mvp.contract

interface DetailContract: FavoriteContract {
    interface Model: FavoriteContract.Model {

    }

    interface View: FavoriteContract.View {

    }

    interface Presenter: FavoriteContract.Presenter<View> {

    }
}