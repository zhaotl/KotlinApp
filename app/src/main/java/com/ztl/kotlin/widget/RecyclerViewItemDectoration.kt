package com.ztl.kotlin.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View

class RecyclerViewItemDectoration(context: Context) : RecyclerView.ItemDecoration() {

    private var mDivider: Drawable? = null
    private var mSectionOffsetV = 0
    private var mSectionOffsetH = 0
    private var mDrawOver = true
    private var attrs: IntArray = intArrayOf(android.R.attr.listDivider)

    init {
        var a = context.obtainStyledAttributes(attrs)
        mDivider = a.getDrawable(0)
        a.recycle()
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        mDivider?.let {
            if (mDrawOver) {
                draw(c, parent)
            }
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        mDivider?.let {
            if (mDrawOver) {
                draw(c, parent)
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        if (getOrientation(parent.layoutManager!!) == RecyclerView.VERTICAL) {
            outRect.set(mSectionOffsetH, 0, mSectionOffsetH, mSectionOffsetV)
        } else {
            outRect.set(0, 0, mSectionOffsetV, 0)
        }

    }

    private fun draw(canvas: Canvas, parent: RecyclerView) {
        val left = parent.left
        val right = parent.width - parent.paddingRight

        val childCnt = parent.childCount
        val size = if (mDivider!!.intrinsicHeight <=0) 1 else mDivider!!.intrinsicHeight
        for (i in 0 until childCnt) {
            val child = parent.getChildAt(i)
            val param = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + param.bottomMargin
            val bottom = top + size

            mDivider?.run {
                setBounds(left, top, right, bottom)
                draw(canvas)
            }
        }
    }

    private fun getOrientation(layoutManager: RecyclerView.LayoutManager): Int {

        if (layoutManager is LinearLayoutManager) {
            return layoutManager.orientation
        } else if (layoutManager is StaggeredGridLayoutManager) {
            return layoutManager.orientation
        }

        return OrientationHelper.HORIZONTAL

    }
}