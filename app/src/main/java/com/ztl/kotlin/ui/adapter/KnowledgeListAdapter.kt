package com.ztl.kotlin.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ztl.kotlin.R
import com.ztl.kotlin.mvp.model.bean.KnowledgeTree

class KnowledgeListAdapter(items: MutableList<KnowledgeTree>)
    : BaseQuickAdapter<KnowledgeTree, BaseViewHolder>(R.layout.knowledge_list_item, items) {

    override fun convert(helper: BaseViewHolder?, item: KnowledgeTree?) {

        helper ?: return
        item ?: return

        helper.setText(R.id.title_first, item.name)
        item.children?.let {child ->
            helper.setText(R.id.title_second,
                child.joinToString(separator = "   ", transform = {
                    it.name
                }))
        }
    }
}