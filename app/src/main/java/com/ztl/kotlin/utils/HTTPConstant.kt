package com.ztl.kotlin.utils

object HTTPConstant {

    const val DEFAULT_TIMEOUT: Long = 60

    const val BASE_URL = "https://www.wanandroid.com/"

    const val SET_COOKIE_KEY = "set-cookie"
    const val COLLECTIONS_WEBSITE = "lg/collect"
    const val UNCOLLECTIONS_WEBSITE = "lg/uncollect"
    const val ARTICLE_WEBSITE = "article"
    const val TODO_WEBSITE = "lg/todo"

    // 登陆
    const val LOGIN_PATH = "user/login"

    // 注册
    const val REGISTER_PATH = "user/register"

    // 登出
    const val LOGOUT_PATH = "user/logout/json"

    // banner
    const val HOME_BANNER = "banner/json"

    // 置顶文章
    const val HOME_TOP_ARTICLES = "article/top/json"

    // 文章列表
    const val HOME_ARTICLES_LIST = "article/list/{index}/json"

    // 收藏站内文章
    const val ADD_INSITE_FAVORITE = "lg/collect/{id}/json"
    // 收藏站外文章
    const val ADD_OUTSITE_FAVORITE = "lg/collect/add/json"

    // 取消收藏（文章列表）
    const val DEL_FAVORITE_ARTICLELIST = "lg/uncollect_originId/{id}/json"
    // 取消收藏（我的收藏）
    const val DEL_FAVORITE_MY_FAVORITES = "lg/uncollect/{id}/json"

    const val KNOWLEDGE_LIST = "tree/json"

    const val KNOWLEDGE_CATAGORY = "article/list/{page}/json"

    const val SUBCRIBE_ARTICLES = "wxarticle/chapters/json"
}