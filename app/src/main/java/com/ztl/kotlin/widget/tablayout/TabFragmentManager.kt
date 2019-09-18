package com.ztl.kotlin.widget.tablayout

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

class TabFragmentManager {

    private var mMagager: FragmentManager
    private var mContainerResid = 0
    private var mFragments: MutableList<Fragment>
    private var mTabLayout: VerticalTabLayout
    private var listener: VerticalTabLayout.OnTabSelectedListener

    constructor(
        manager: FragmentManager,
        fragments: MutableList<Fragment>,
        layout: VerticalTabLayout
    ) {
        mMagager = manager
        mFragments = fragments
        mTabLayout = layout
        listener = OnFragmentTabSelectedListener()
        mTabLayout.addOnTabSelectedListener(listener)
    }

    constructor(
        manager: FragmentManager,
        containerRes: Int,
        fragments: MutableList<Fragment>,
        layout: VerticalTabLayout
    ) : this(manager, fragments, layout) {

        mContainerResid = containerRes
    }

    fun changeFragment() {
        val ft = mMagager.beginTransaction()
        val position = mTabLayout.getSelectedTabPosition()
        val addedFragments = mMagager.fragments
        mFragments?.forEachIndexed { index, fragment ->
            if ((addedFragments == null || !addedFragments.contains(fragment))
                && mContainerResid != 0
            ) {
                ft.add(mContainerResid, fragment)
            }

            if ((mFragments.size > position && index == position) ||
                mFragments.size < position && index == position - 1
            ) {
                ft.show(fragment)
            } else {
                ft.hide(fragment)
            }
        }

        ft.commit()
        mMagager.executePendingTransactions()

    }

    fun detach() {
        val ft = mMagager.beginTransaction()
        mFragments?.forEach {
            ft.remove(it)
        }

        ft.commit()
        mMagager.executePendingTransactions()
    }

    inner class OnFragmentTabSelectedListener : VerticalTabLayout.OnTabSelectedListener {
        override fun onTabSelected(tabView: TabView, position: Int) {
            changeFragment()
        }

        override fun onTabReleased(tabView: TabView, position: Int) {
        }
    }
}