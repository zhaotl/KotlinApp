package com.ztl.kotlin.widget.tablayout

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.view.Gravity
import android.view.View
import q.rorbin.badgeview.Badge

interface ITabView {

    fun setIcon(icon: TabIcon): ITabView
    fun setTitle(title: TabTitle): ITabView
    fun setBadge(badge: TabBadge): ITabView
    fun setBackground(resid: Int): ITabView
    fun getIcon(): TabIcon
    fun getTitle(): TabTitle
    fun getBadge(): TabBadge
    fun getTabView(): View

    class TabIcon {

        private var mBuilder: Builder

        private constructor(builder: TabIcon.Builder) {
            mBuilder = builder
        }

        fun getSelectedIcon() = mBuilder.mSelectIcon

        fun getNormalIcon() = mBuilder.mNormalIcon

        fun getIconGravity() = mBuilder.mIconGravity

        fun getIconWidth() = mBuilder.mIconWidth

        fun getIconHeight() = mBuilder.mIconHeight

        fun getIconMargin() = mBuilder.mMargin

        object Builder {
            var mSelectIcon = 0
            var mNormalIcon = 0
            var mIconGravity = Gravity.LEFT
            var mIconWidth = -1
            var mIconHeight = -1
            var mMargin = 0

            fun setIcon(selectIconResId: Int, normalIconResId: Int): Builder {
                mSelectIcon = selectIconResId
                mNormalIcon = normalIconResId

                return this
            }

            fun setSize(width: Int, height: Int): Builder {
                mIconWidth = width
                mIconHeight = height
                return this
            }

            fun setIconGravity(gravity: Int): Builder {
                when (gravity) {
                    Gravity.START, Gravity.END, Gravity.TOP, Gravity.BOTTOM
                    -> mIconGravity = gravity
                    else -> throw IllegalAccessException("iconGravity only support Start, End, Top or Bottom")
                }

                return this
            }

            fun setIconMargin(margin: Int): Builder {
                mMargin = margin
                return this
            }

            fun build(): TabIcon {
                return TabIcon(this)
            }
        }
    }

    class TabTitle {

        private var mBuilder: Builder

        private constructor(builder: TabTitle.Builder) {
            mBuilder = builder
        }

        fun getColorSelect(): Int = mBuilder.mColorSelected

        fun getColorNormal(): Int = mBuilder.mColorNormal

        fun getTextSize(): Int = mBuilder.mTitleTextSize

        fun getContent(): String = mBuilder.mContent

        object Builder {

            var mColorSelected: Int = 0xFFFF4081.toInt()
            var mColorNormal: Int = 0xFF757575.toInt()
            var mTitleTextSize = 16
            var mContent = ""

            fun setTextColor(colorSelected: Int, colorNormal: Int): Builder {
                mColorSelected = colorSelected
                mColorNormal = colorNormal

                return this
            }

            fun setTextSize(size: Int): Builder {
                mTitleTextSize = size
                return this
            }

            fun setContent(content: String): Builder {
                mContent = content
                return this
            }

            fun build(): TabTitle {
                return TabTitle(this)
            }
        }
    }

    class TabBadge {

        private var mBuilder: Builder

        constructor(builder: Builder) {
            mBuilder = builder
        }

        fun getBackgroundColor() = mBuilder.colorBackground
        fun getBadgeTextColor() = mBuilder.colorBadgeText
        fun getStrokeColor() = mBuilder.colorStroke
        fun getBackgroundDrawable() = mBuilder.drawableBackground
        fun getBackgroundClip() = mBuilder.drawableBackgroundClip
        fun getStrokeWidth() = mBuilder.strokeWidth
        fun getBadgeTextSize() = mBuilder.badgeTextSize
        fun getBadgePadding() = mBuilder.badgePadding
        fun getBadgeNumber() = mBuilder.badgeNumber
        fun getBadgeText() = mBuilder.badgeText
        fun getBadgeGravity() = mBuilder.badgeGravity
        fun getGravityOffsetX() = mBuilder.gravityOffsetX
        fun getGravityOffsetY() = mBuilder.gravityOffsetY
        fun getExactMode() = mBuilder.exactMode
        fun isShowShadow() = mBuilder.showShadow
        fun getDragStateChangedListener() = mBuilder.drageListener

        object Builder {
            var colorBackground: Int = 0xFFE84E40.toInt()
            var colorBadgeText: Int = 0xFFFFFFFF.toInt()
            var colorStroke = Color.TRANSPARENT
            var drawableBackground: Drawable? = null
            var drawableBackgroundClip = false
            var strokeWidth = 0
            var badgeTextSize = 11f
            var badgePadding = 5f
            var badgeNumber = 0
            var badgeText = ""
            var badgeGravity = Gravity.END or Gravity.TOP
            var gravityOffsetX = 1
            var gravityOffsetY = 1
            var exactMode = false
            var showShadow = true
            var drageListener: Badge.OnDragStateChangedListener? = null

            fun build() = TabBadge(this)

            fun setStroke(color: Int, width: Int): Builder {
                colorStroke = color
                strokeWidth = width
                return this
            }

            fun setDrawableBackground(drawable: Drawable, clipe: Boolean): Builder {
                drawableBackground = drawable
                drawableBackgroundClip = clipe

                return this
            }

            fun setShadow(shadow: Boolean): Builder {
                showShadow = shadow
                return this
            }

            fun setOnDragStateChangeListener(listener: Badge.OnDragStateChangedListener): Builder {
                drageListener = listener
                return this
            }

            fun setExactMode(mode: Boolean): Builder {
                exactMode = mode
                return this
            }

            fun setBackgroundColor(color: Int): Builder {
                colorBackground = color
                return this
            }

            fun setBadgePadding(padding: Float): Builder {
                badgePadding = padding
                return this
            }

            fun setBadgeGravity(gravity: Int): Builder {
                badgeGravity = gravity
                return this
            }

            fun setBadgeNumber(number: Int): Builder {
                badgeNumber = number
                return this
            }

            fun setTextColor(color: Int): Builder {
                colorBadgeText = color
                return this
            }

            fun setTextSize(size: Float): Builder {
                badgeTextSize = size
                return this
            }

            fun setBadgetext(content: String): Builder {
                badgeText = content
                return this
            }

            fun setOffset(offsetX: Int, offsetY: Int): Builder {
                gravityOffsetX = offsetX
                gravityOffsetY = offsetY

                return this
            }

        }
    }
}