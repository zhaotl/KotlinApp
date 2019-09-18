package com.ztl.kotlin.widget.tablayout

interface TabAdapter {
    fun getCount(): Int
    fun getBadge(position: Int): ITabView.TabBadge
    fun getIcon(position: Int): ITabView.TabIcon
    fun getTitle(position: Int): ITabView.TabTitle
    fun getBackground(position: Int): Int
}