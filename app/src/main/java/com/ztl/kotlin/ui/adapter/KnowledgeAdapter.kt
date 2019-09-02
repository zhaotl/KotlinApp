package com.ztl.kotlin.ui.adapter

import android.content.Context
import android.text.Html
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ztl.kotlin.R
import com.ztl.kotlin.mvp.model.bean.Article
import com.ztl.kotlin.utils.GlideLoader
import com.ztl.kotlin.utils.KLogger

class KnowledgeAdapter(private val context: Context?, val articles: MutableList<Article>) :
    BaseQuickAdapter<Article, BaseViewHolder>(R.layout.knowledge_adapter_item, articles) {

    override fun convert(helper: BaseViewHolder?, item: Article?) {
        helper ?: return
        item ?: return
        KLogger.d("item = ${item.toString()}")
        KLogger.d("author = ${item.author}")
        KLogger.d("data = ${item.niceDate}")
        KLogger.d("title = ${item.title}")

        helper.setText(R.id.article_author, item.author)
            .setText(R.id.article_date, (item.niceDate))
            .setText(R.id.article_title, Html.fromHtml(item.title))
            .setImageResource(R.id.article_favorite,
                if(item.collect) R.drawable.ic_favorite else R.drawable.ic_favorite_not)
            .addOnClickListener(R.id.article_favorite)

        val category = when {
            item.superChapterName.isNotEmpty() and item.chapterName.isNotEmpty()
            -> "${item.superChapterName} / ${item.chapterName}"

            item.superChapterName.isNotEmpty() -> item.superChapterName

            item.chapterName.isNotEmpty() -> item.chapterName

            else -> ""
        }
        helper.setText(R.id.article_category, category)

        val envelopView = helper.getView<ImageView>(R.id.article_img)
        if (item.envelopePic.isNotEmpty()) {
            context?.let {
                GlideLoader.load(it, item.envelopePic, envelopView)
                envelopView.visibility = View.VISIBLE
            }
        } else {
            envelopView.visibility = View.GONE
        }

    }
}