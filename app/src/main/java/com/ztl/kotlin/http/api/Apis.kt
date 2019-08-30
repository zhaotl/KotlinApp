package com.ztl.kotlin.http.api

import com.ztl.kotlin.mvp.model.bean.*
import com.ztl.kotlin.utils.HTTPConstant
import io.reactivex.Observable
import retrofit2.http.*

interface Apis {

    object ErrorCode {
        val SUCCESS = 0
    }

    // Login
    @FormUrlEncoded
    @POST(value = HTTPConstant.LOGIN_PATH)
    fun loginWithName(@Field("username") name: String,
                      @Field("password") password: String): Observable<HttpResult<LoginData>>

    @FormUrlEncoded
    @POST(value = HTTPConstant.REGISTER_PATH)
    fun register(@Field("username") name: String,
                 @Field("password") pwd: String,
                 @Field("repassword") repwd: String): Observable<HttpResult<LoginData>>

    // 登出
    @GET(value = HTTPConstant.LOGOUT_PATH)
    fun logout(): Observable<HttpResult<Any>>

    // 收藏站内文章
    @POST(value = HTTPConstant.ADD_INSITE_FAVORITE)
    fun addInSiteFavorite(@Path("id") id: Int): Observable<HttpResult<Any>>

    // 收藏站外文章
    @POST(value = HTTPConstant.ADD_OUTSITE_FAVORITE)
    @FormUrlEncoded
    fun addOutSiteFavorite(@Field("title") title: String ,
                           @Field("author") author: String,
                           @Field("link") link: String): Observable<HttpResult<Any>>

    // 取消站内收藏
    @POST(value = HTTPConstant.DEL_FAVORITE_ARTICLELIST)
    fun deleteFromArticles(@Path("id") id: Int):Observable<HttpResult<Any>>

    // 取消收藏列表中的收藏
    @POST(value = HTTPConstant.DEL_FAVORITE_MY_FAVORITES)
    fun deleteFromFavorites(@Path("id") id: Int,
                            @Field("originId") originId: Int): Observable<HttpResult<Any>>

    // 首页的Banner
    @GET(value = HTTPConstant.HOME_BANNER)
    fun getHomeBanner(): Observable<HttpResult<List<Banner>>>

    @GET(value = HTTPConstant.HOME_TOP_ARTICLES)
    fun getTopArticles(): Observable<HttpResult<MutableList<Article>>>

    @GET(value = HTTPConstant.HOME_ARTICLES_LIST)
    fun getHomeArticles(@Path("index") index: Int): Observable<HttpResult<ArticleList>>

    @GET(value = HTTPConstant.KNOWLEDGE_LIST)
    fun getKnowledgeList(): Observable<HttpResult<List<KnowledgeTree>>>

    // 知识体系下文章
    @GET(value = HTTPConstant.KNOWLEDGE_CATAGORY)
    fun getKnoledgeCategory(@Path("page") page: Int, @Query("cid") cid: Int): Observable<HttpResult<ArticleList>>
}