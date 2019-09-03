package com.ztl.kotlin.mvp.model.bean

import com.squareup.moshi.Json
import java.io.Serializable

data class HttpResult<T>(
    @Json(name = "data") val data: T
) : BaseBean()

// 登录数据
data class LoginData(
    @Json(name = "chapterTops") val chapterTops: MutableList<String>,
    @Json(name = "collectIds") val collectIds: MutableList<String>,
    @Json(name = "email") val email: String,
    @Json(name = "icon") val icon: String,
    @Json(name = "id") val id: Int,
    @Json(name = "password") val password: String,
    @Json(name = "token") val token: String,
    @Json(name = "type") val type: Int,
    @Json(name = "username") val username: String
)

data class Tags(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String
)

data class Article(
    @Json(name = "apkLink") val apkLink: String,
    @Json(name = "author") val author: String,
    @Json(name = "chapterId") val chapterId: Int,
    @Json(name = "chapterName") val chapterName: String,
    @Json(name = "collect") var collect: Boolean,
    @Json(name = "courseId") val courseId: Int,
    @Json(name = "desc") val desc: String,
    @Json(name = "envelopePic") val envelopePic: String,
    @Json(name = "fresh") val fresh: Boolean,
    @Json(name = "id") val id: Int,
    @Json(name = "link") val link: String,
    @Json(name = "niceDate") val niceDate: String,
    @Json(name = "origin") val origin: String,
    @Json(name = "projectLink") val projectLink: String,
    @Json(name = "publishTime") val publishTime: Long,
    @Json(name = "superChapterId") val superChapterId: Int,
    @Json(name = "superChapterName") val superChapterName: String,
    @Json(name = "tags") val tags: MutableList<Tags>,
    @Json(name = "title") val title: String,
    @Json(name = "type") val type: Int,
    @Json(name = "userId") val userId: Int,
    @Json(name = "visible") val visible: Int,
    @Json(name = "zan") val zan: Int,
    @Json(name = "top") var top: String
)

data class ArticleList(
    @Json(name = "curPage") val curPage: Int,
    @Json(name = "datas") val datas: MutableList<Article>,
    @Json(name = "offset") val offset: Int,
    @Json(name = "over") val over: Boolean,
    @Json(name = "pageCount") val pageCount: Int,
    @Json(name = "size") val size: Int,
    @Json(name = "total") val total: Int
)

//轮播图
data class Banner(
    @Json(name = "desc") val desc: String,
    @Json(name = "id") val id: Int,
    @Json(name = "imagePath") val imagePath: String,
    @Json(name = "isVisible") val isVisible: Int,
    @Json(name = "order") val order: Int,
    @Json(name = "title") val title: String,
    @Json(name = "type") val type: Int,
    @Json(name = "url") val url: String
)

// 体系
data class KnowledgeTree(
    @Json(name = "children") val children: MutableList<Knowledge>,
    @Json(name = "courseId") val courseId: Int,
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "order") val order: String,
    @Json(name = "parentChapterId") val parentChapterId: String,
    @Json(name = "visible") val visible: Int
): Serializable

data class Knowledge(
    @Json(name = "children") val children: MutableList<Any>,
    @Json(name = "courseId") val courseId: Int,
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "order") val order: String,
    @Json(name = "parentChapterId") val parentChapterId: String,
    @Json(name = "visible") val visible: Int
): Serializable

data class SharedArticles(
    @Json(name = "children") val children: MutableList<String>,
    @Json(name = "courseId") val courseId: Int,
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "order") val order: Int,
    @Json(name = "parentChapterId") val parentChapterId: Int,
    @Json(name = "userControlSetTop") val userControlSetTop: Boolean,
    @Json(name = "visible") val visible: Int
)