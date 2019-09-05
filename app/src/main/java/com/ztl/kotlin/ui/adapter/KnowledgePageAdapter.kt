package com.ztl.kotlin.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.text.Html
import com.ztl.kotlin.mvp.model.bean.Knowledge
import com.ztl.kotlin.mvp.model.bean.SharedArticles
import com.ztl.kotlin.ui.fragment.KnowledgeFragment

class KnowledgePageAdapter(
    private val datas: MutableList<Knowledge>
    , fragment: FragmentManager?
) : FragmentStatePagerAdapter(fragment) {

    private val fms = mutableListOf<Fragment>()

    init {
        fms.clear()
        datas.forEach {
            fms.add(KnowledgeFragment.getInstance(it.id))
        }
    }

    override fun getItem(position: Int): Fragment = fms[position]

    override fun getCount(): Int = fms.size

    override fun getPageTitle(position: Int): CharSequence? = Html.fromHtml(datas[position].name)

    override fun getItemPosition(`object`: Any): Int = PagerAdapter.POSITION_NONE
}