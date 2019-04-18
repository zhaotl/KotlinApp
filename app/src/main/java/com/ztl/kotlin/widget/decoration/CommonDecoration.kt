package com.ztl.kotlin.widget.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

class CommonDecoration(val context: Context,
                       val orientation: Int = VERTICAL)
    : RecyclerView.ItemDecoration() {

    companion object {
        const val VERTICAL = LinearLayoutManager.VERTICAL
        const val  HORIZONTAL  = LinearLayoutManager.HORIZONTAL
    }

    private var dividerHeight = 2
    private var mDivider: Drawable? = null


    init {

        if (orientation != RecyclerView.VERTICAL && orientation != RecyclerView.HORIZONTAL) {
            throw IllegalArgumentException("请输入正确的参数！")
        }

        var a = context.obtainStyledAttributes(intArrayOf(android.R.attr.listDivider))
        mDivider = a.getDrawable(0)
        a.recycle()
    }

    constructor(context: Context, orientation: Int, resId: Int) : this(context, orientation) {
        mDivider = ContextCompat.getDrawable(context, resId)
        dividerHeight = mDivider?.intrinsicHeight ?: 2
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        if (orientation == VERTICAL) {
            outRect.set(0, 0, 0, dividerHeight)
        } else if (orientation == HORIZONTAL){
            outRect.set(0, 0, dividerHeight, 0)
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        if (orientation == VERTICAL) {
            drawVerticle(c, parent)
        } else if (orientation == HORIZONTAL){
            drwaHorizonal(c, parent)
        }
    }

    private fun drawVerticle(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childreCnt = parent.childCount
        for (i in 0 until childreCnt) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + dividerHeight

            mDivider?.run {
                setBounds(left, top, right, bottom)
                draw(c)
            }
        }
    }

    private fun drwaHorizonal(c: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop
        val bootom = parent.height - parent.paddingBottom

        val childrenCnt = parent.childCount
        for (i in 0 until childrenCnt) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin
            val right = left + dividerHeight

            mDivider?.run {
                setBounds(left, top, right, bootom)
                draw(c)
            }
        }
    }
}