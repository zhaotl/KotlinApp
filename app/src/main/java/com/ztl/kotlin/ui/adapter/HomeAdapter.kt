package com.ztl.kotlin.ui.adapter

import android.content.Context
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ztl.kotlin.R
import com.ztl.kotlin.mvp.model.bean.Article
import com.ztl.kotlin.utils.GlideLoader

class HomeAdapter(private val context:Context? , items: MutableList<Article>)
    : BaseQuickAdapter<Article, BaseViewHolder>(R.layout.article_item, items) {

    override fun convert(helper: BaseViewHolder?, item: Article?) {
        item ?: return
        helper ?: return
        var title = ""
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            title = Html.fromHtml(item.title, Html.FROM_HTML_MODE_COMPACT).toString()
        } else {
            title = Html.fromHtml(item.title).toString()
        }

        helper.setText(R.id.fragment_article_title, title)
            .setText(R.id.fragment_article_author, item.author)
            .setText(R.id.fragment_article_date, item.niceDate)
            .setImageResource(R.id.fragment_iv_like,
                if (item.collect) R.drawable.ic_favorite else R.drawable.ic_favorite_not)
            .addOnClickListener(R.id.fragment_iv_like)

        val articleName = when {
            item.superChapterName.isNotEmpty() and item.chapterName.isNotEmpty() ->
                "${item.superChapterName} / ${item.chapterName}"
            item.superChapterName.isNotEmpty() -> item.superChapterName
            item.chapterName.isNotEmpty() -> item.chapterName
            else -> ""
        }

        helper.setText(R.id.fragment_article_chapterName, articleName)

        val envelopView = helper.getView<ImageView>(R.id.iv_article_thumbnail)
        if (item.envelopePic.isNotEmpty()) {

            context?.let {
                GlideLoader.load(it, item.envelopePic, envelopView)
                envelopView.visibility = View.VISIBLE
            }

        } else {
            envelopView.visibility = View.GONE
        }

        val freshview = helper.getView<TextView>(R.id.fragment_article_new)
        freshview.visibility = if (item.fresh) View.VISIBLE else View.GONE

        val article_top = helper.getView<TextView>(R.id.fragment_article_top)
        article_top.visibility = if(item.top == "1") View.VISIBLE else View.GONE

        val article_tag = helper.getView<TextView>(R.id.fragment_article_tag)
        if (item.tags.size > 0) {
            article_tag.visibility = View.VISIBLE
            article_tag.setText(item.tags[0].name)
        } else {
            article_tag.visibility = View.GONE
        }
    }
}