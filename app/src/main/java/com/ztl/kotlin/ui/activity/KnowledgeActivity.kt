package com.ztl.kotlin.ui.activity

import android.support.design.widget.TabLayout
import android.view.View
import com.ztl.kotlin.R
import com.ztl.kotlin.base.BaseActivity
import com.ztl.kotlin.mvp.model.bean.Knowledge
import com.ztl.kotlin.mvp.model.bean.KnowledgeTree
import com.ztl.kotlin.ui.adapter.KnowledgePageAdapter
import com.ztl.kotlin.ui.fragment.KnowledgeFragment
import com.ztl.kotlin.utils.Constant
import kotlinx.android.synthetic.main.activity_knowledge.*

class KnowledgeActivity: BaseActivity() {

    private var knowledges = mutableListOf<Knowledge>()

    private lateinit var toolbarTitle: String

    override fun enableEventBus(): Boolean = false

    private val knowledgePageAdapter: KnowledgePageAdapter by lazy {
        KnowledgePageAdapter(knowledges, supportFragmentManager)
    }

    override fun layoutRes(): Int = R.layout.activity_knowledge

    override fun initView() {
        intent?.extras?.let {
            it.getSerializable(Constant.CONST_KNOWLEDGE_DATA)?.let {
                val data = it as KnowledgeTree
                toolbarTitle = data.name
                data.children.let {children ->
                    knowledges.addAll(children)
                }
            }
        }

        toolbar.run {
            title = toolbarTitle
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener{
                this@KnowledgeActivity.finish()
            }
        }

        viewPager.run {
            adapter = knowledgePageAdapter
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tablayout))
            offscreenPageLimit = knowledges.size
        }

        tablayout.run {
            setupWithViewPager(viewPager)
            addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
            addOnTabSelectedListener(tabSelectedListener)
        }

        knowledge_float_action.setOnClickListener(floatBtnClickLisener)
    }

    private val tabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.let {
                viewPager.setCurrentItem(it.position, false)
            }
        }
    }

    private val floatBtnClickLisener = View.OnClickListener {
        if (knowledgePageAdapter.count == 0) {
            return@OnClickListener
        }

        val fragment = knowledgePageAdapter.getItem(viewPager.currentItem)
        (fragment as KnowledgeFragment).scrollToTop()
    }

    override fun onDestroy() {
        super.onDestroy()
        knowledges.clear();

    }
}