package com.ztl.kotlin.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.text.Html
import com.ztl.kotlin.mvp.model.bean.ProjectData
import com.ztl.kotlin.ui.fragment.ProjectListFragment

class ProjectPageAdapter(private val datas: MutableList<ProjectData>, fragment: FragmentManager?) :
    FragmentStatePagerAdapter(fragment) {

    private val fragments = mutableListOf<Fragment>()

    init {
        fragments.clear()
        datas.forEach {
            fragments.add(ProjectListFragment.getInstance(it.id))
        }
    }

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? = Html.fromHtml(datas[position].name)
}