package com.ztl.kotlin.ui.adapter

import android.content.Context
import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ztl.kotlin.R
import com.ztl.kotlin.mvp.model.bean.Article
import com.ztl.kotlin.utils.GlideLoader

class ProjectAdapter(private val context: Context?, datas: MutableList<Article>) :
    BaseQuickAdapter<Article, BaseViewHolder>(R.layout.item_project_list, datas) {

    override fun convert(helper: BaseViewHolder?, item: Article?) {

        helper ?: return
        item ?: return

        helper.setText(R.id.project_name, Html.fromHtml(item.title))
            .setText(R.id.project_content, Html.fromHtml(item.desc))
            .setText(R.id.project_author, item.author)
            .setText(R.id.project_date, item.niceDate)
            .setImageResource(
                R.id.project_favorite,
                if (item.collect) R.drawable.ic_favorite else R.drawable.ic_favorite_not
            )
            .addOnClickListener(R.id.project_favorite)

        context?.let {
            GlideLoader.load(it, item.envelopePic, helper.getView(R.id.project_iv))
        }
    }
}