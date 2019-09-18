package com.ztl.kotlin.widget.tablayout

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TabHost
import com.pdog.dimension.dp
import com.ztl.kotlin.R

class VerticalTabLayout : ScrollView {

    companion object {
        const val TAB_MODE_FIX = 10
        const val TAB_MODE_SCROLLABLE = 11
    }

    private val mTabSelectedListener = mutableListOf<OnTabSelectedListener>()
    private lateinit var mTabPageChangeListener: TabHost.OnTabChangeListener

    private var typedArray: TypedArray? = null
    private var mColorIndicator: Int = 0
    private var mTabMargin: Int = 0
    private var mIndicatorWidth = 0
    private var mIndicatorGravity = 0
    private var mIndicatorCorner: Float = 0f
    private var mTabMode = 0
    private var mTabHeight = 0
    private var mFragmentManager: TabFragmentManager? = null

    private var mSelectedTab: TabView? = null
    private var mTabStrip: TabStrip? = null
    private var mViewPage: ViewPager? = null
    private var mPagerAdapter: PagerAdapter? = null
    private var mTabAdapter: TabAdapter? = null



    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int = android.R.attr.scrollViewStyle
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) {

        attrs?.let {
            typedArray = context.obtainStyledAttributes(it, R.styleable.VerticalTabLayout)

            typedArray?.run {

                mColorIndicator = getColor(
                    R.styleable.VerticalTabLayout_indicator_color,
                    ContextCompat.getColor(context, R.color.colorAccent)
                )

                mIndicatorWidth =
                    getDimension(R.styleable.VerticalTabLayout_indicator_width, 3.0f.dp).toInt()
                mIndicatorCorner = getDimension(R.styleable.VerticalTabLayout_indicator_corners, 0f)
                mIndicatorGravity =
                    getInteger(R.styleable.VerticalTabLayout_indicator_gravity, Gravity.LEFT)
                when (mIndicatorGravity) {
                    3 -> mIndicatorGravity = Gravity.LEFT
                    5 -> mIndicatorGravity = Gravity.RIGHT
                    119 -> mIndicatorGravity = Gravity.FILL
                }

                mTabMargin = getDimension(R.styleable.VerticalTabLayout_tab_margin, 0f).toInt()
                mTabMode = getInteger(R.styleable.VerticalTabLayout_tab_mode, TAB_MODE_FIX)
                val defalutHeight = LinearLayout.LayoutParams.WRAP_CONTENT
                mTabHeight = getDimension(
                    R.styleable.VerticalTabLayout_tab_height,
                    defalutHeight.toFloat()
                ).toInt()

                recycle()
            }


        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 0)
            removeAllViews()

        initTabStrip()
    }

    fun initTabStrip() {
        mTabStrip = TabStrip(context)
        addView(mTabStrip, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
    }

    fun getSelectedTabPosition(): Int {
        val index = mTabStrip?.indexOfChild(mSelectedTab) ?: 0
        return if (index == -1) 0 else index
    }

    private fun removeAllTabs() {
        mTabStrip?.removeAllViews()
        mSelectedTab = null
    }

    fun getTabViewAt(position: Int): TabView {

        return mTabStrip?.getChildAt(position) as TabView
    }

    fun getTabCount(): Int {
        return mTabStrip?.childCount ?: 0
    }

    private fun addTabWithMode(tabView: TabView) {
        var params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        initTabWithMode(params)

        mTabStrip?.run {
            tabView.let {
                addView(it, params)
                if (indexOfChild(it) == 0) {
                    it.isChecked = true
                    params = it.layoutParams as LinearLayout.LayoutParams
                    params.setMargins(0, 0, 0, 0)
                    it.layoutParams = params
                    mSelectedTab = it
                    post {
                        moveIndicator(0f)
                    }
                }
            }
        }
    }

    private fun initTabWithMode(params: LinearLayout.LayoutParams) {
        when (mTabMode) {
            TAB_MODE_FIX -> {
                params.height = 0
                params.weight = 1.0f
                params.setMargins(0, 0, 0, 0)
                isFillViewport = true
            }

            TAB_MODE_SCROLLABLE -> {
                params.height = mTabHeight
                params.weight = 0f
                params.setMargins(0, mTabMargin, 0, 0)
                isFillViewport = false
            }
        }
    }

    private fun scroll2Tab(position: Int) {
        val tabView = getTabViewAt(position)
        val y = scrollY
        tabView.let {
            val tabTop = it.top + it.height / 2 - y
            val target = it.height / 2
            if (tabTop > target) {
                smoothScrollBy(0, tabTop - target)
            } else {
                smoothScrollBy(0, target - tabTop)
            }
        }
    }

    private fun addTab(tabView: TabView) {
        tabView.let {
            addTabWithMode(it)
            it.setOnClickListener { view ->
                mSelectedTab?.run {
                    val position = indexOfChild(view)
                    setTabSelected(position)
                }
            }
        }
    }

    fun addOnTabSelectedListener(listener: OnTabSelectedListener) {
        listener?.let {
            mTabSelectedListener.add(it)
        }
    }

    fun removeOnTabSelectedListener(listener: OnTabSelectedListener) {
        listener?.let {
            mTabSelectedListener.remove(it)
        }
    }

    fun setTabAdapter(adapter: TabAdapter) {
        removeAllTabs()
        adapter?.let {
            for (i in 0 until it.getCount()) {
                var qTabView = QTabView(context).setIcon(adapter.getIcon(i))
                    .setTitle(adapter.getTitle(i))
                    .setBadge(adapter.getBadge(i))
                    .setBackground(adapter.getBackground(i))
                addTab(qTabView)
            }
        }
    }

    fun setupWithFragment(manager: FragmentManager, containerRes: Int = 0, fragments: MutableList<Fragment>, adapter: TabAdapter? = null) {
        adapter?.let {
            setTabAdapter(it)
        }

        mFragmentManager?.detach()

        if (containerRes != 0) {
            mFragmentManager = TabFragmentManager(manager, containerRes, fragments, this)
        } else {
            mFragmentManager = TabFragmentManager(manager, fragments, this)
        }
    }

    fun setTabSelected(position: Int) {
        setTabSelected(position, true, true)
    }

    fun setTabSelected(position: Int, updateIndicator: Boolean, callListener: Boolean) {
        val view = getTabViewAt(position)
        view ?: return
        var selected = false
        mSelectedTab?.let {
            selected = (it != view)
            if (selected) {
                it.isChecked = false
            }

            view.isChecked = true
            if (updateIndicator) {
                mTabStrip?.moveIndicatorWithAnimator(position)
            }

            mSelectedTab = view
            scroll2Tab(position)
        }

        if (callListener) {
            mTabSelectedListener.forEach {
                it?.run {
                    if (selected) {
                        onTabSelected(view, position)
                    } else {
                        onTabReleased(view, position)
                    }
                }
            }
        }
    }

    inner class TabStrip : LinearLayout {
        private var mIndicatorTopY = 0f
        private var mIndicatorX = 0f
        private var mIndicatorBottomY = 0f
        private var mLastWidth = 0
        private var mIndicatorPaint: Paint
        private var mIndicatorRect: RectF
        private var mIndicatorSet: AnimatorSet? = null

        constructor(context: Context) : super(context) {
            mIndicatorPaint = Paint()
            mIndicatorPaint.isAntiAlias = true
            mIndicatorRect = RectF()

            when (mIndicatorGravity) {
                0 -> mIndicatorGravity = Gravity.LEFT
            }

            setIndicatorGravity()
        }


        private fun setIndicatorGravity() {
            when (mIndicatorGravity) {
                Gravity.LEFT -> {
                    mIndicatorX = 0f
                    if (mLastWidth != 0) {
                        mIndicatorWidth = mLastWidth
                    }
                    setPadding(mIndicatorWidth, 0, 0, 0)
                }

                Gravity.RIGHT -> {
                    if (mLastWidth != 0) {
                        mIndicatorWidth = mLastWidth
                    }
                    setPadding(0, 0, mIndicatorWidth, 0)
                }

                Gravity.FILL -> {
                    mIndicatorX = 0f
                    setPadding(0, 0, 0, 0)
                }
            }

            post {
                when (mIndicatorGravity) {
                    Gravity.RIGHT -> {
                        mIndicatorX = (width - mIndicatorWidth).toFloat()
                    }

                    Gravity.FILL -> {
                        mLastWidth = mIndicatorWidth
                        mIndicatorWidth = width
                    }
                }

                invalidate()
            }
        }

        private fun calcIndicatorY(offset: Float) {
            val index = Math.floor(offset.toDouble()).toInt()
            val childView = getChildAt(index)

            if (index != childCount - 1 && Math.ceil(offset.toDouble()).toInt() != 0) {
                val nextView = getChildAt(index + 1)
                mIndicatorTopY = childView.top + (nextView.top - childView.top) * (offset - index)
                mIndicatorBottomY =
                    childView.bottom + (nextView.bottom - childView.bottom) * (offset - index)
            } else {
                mIndicatorTopY = childView.top.toFloat()
                mIndicatorBottomY = childView.bottom.toFloat()
            }
        }

        fun moveIndicator(offset: Float) {
            calcIndicatorY(offset)
            invalidate()
        }

        private fun updateIndicator() {
            moveIndicatorWithAnimator(getSelectedTabPosition())
        }

        fun moveIndicatorWithAnimator(index: Int) {
            val direction = index - getSelectedTabPosition()
            val childView = getChildAt(index)
            val targetTop = childView.top
            val targetBottom = childView.bottom

            if (mIndicatorTopY == targetTop.toFloat() && mIndicatorBottomY == targetBottom.toFloat()) return

            mIndicatorSet?.let {
                if (it.isRunning) {
                    it.end()
                }
            }

            post {
                val startAnime: ValueAnimator
                val endAnime: ValueAnimator

                if (direction > 0) {
                    startAnime = ValueAnimator.ofFloat(mIndicatorBottomY, targetBottom.toFloat())
                    with(startAnime) {
                        duration = 100
                        addUpdateListener { animation ->
                            mIndicatorBottomY = animation.getAnimatedValue().toString().toFloat()
                            invalidate()
                        }
                    }

                    endAnime = ValueAnimator.ofFloat(mIndicatorTopY, targetTop.toFloat())
                    with(endAnime) {
                        duration = 100
                        addUpdateListener { animation ->
                            mIndicatorTopY = animation.getAnimatedValue().toString().toFloat()
                            invalidate()
                        }
                    }
                } else {
                    startAnime = ValueAnimator.ofFloat(mIndicatorTopY, targetTop.toFloat())
                    with(startAnime) {
                        duration = 100
                        addUpdateListener { animation ->
                            mIndicatorTopY = animation.getAnimatedValue().toString().toFloat()
                            invalidate()
                        }
                    }

                    endAnime = ValueAnimator.ofFloat(mIndicatorBottomY, targetBottom.toFloat())
                    with(endAnime) {
                        duration = 100
                        addUpdateListener { animation ->
                            mIndicatorBottomY = animation.getAnimatedValue().toString().toFloat()
                            invalidate()
                        }
                    }
                }

                startAnime?.let { start ->
                    endAnime?.let { end ->
                        mIndicatorSet = AnimatorSet()
                        mIndicatorSet?.run {
                            play(end).after(start)
                            start()
                        }
                    }
                }
            }
        }

        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)

            mIndicatorPaint.color = mColorIndicator
            mIndicatorRect.left = mIndicatorX
            mIndicatorRect.top = mIndicatorTopY
            mIndicatorRect.right = mIndicatorX + mIndicatorWidth
            mIndicatorRect.bottom = mIndicatorBottomY

            if (mIndicatorCorner != 0f) {
                canvas?.drawRoundRect(
                    mIndicatorRect,
                    mIndicatorCorner,
                    mIndicatorCorner,
                    mIndicatorPaint
                )
            } else {
                canvas?.drawRect(mIndicatorRect, mIndicatorPaint)
            }
        }
    }

    interface OnTabSelectedListener {
        fun onTabSelected(tabView: TabView, position: Int)
        fun onTabReleased(tabView: TabView, position: Int)
    }
}
