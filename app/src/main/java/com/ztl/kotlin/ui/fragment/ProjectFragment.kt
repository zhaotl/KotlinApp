package com.ztl.kotlin.ui.fragment

import android.support.design.widget.TabLayout
import android.view.View
import android.widget.TableLayout
import com.ztl.kotlin.R
import com.ztl.kotlin.base.BaseMvpFragment
import com.ztl.kotlin.mvp.contract.ProjectContract
import com.ztl.kotlin.mvp.model.bean.ProjectData
import com.ztl.kotlin.mvp.presenter.ProjectPresenter
import com.ztl.kotlin.ui.adapter.ProjectPageAdapter
import kotlinx.android.synthetic.main.fragment_project.*

class ProjectFragment : BaseMvpFragment<ProjectContract.View, ProjectContract.Presenter>(),
    ProjectContract.View {

    companion object {
        fun getInstance() = ProjectFragment()
    }

    private val datas = mutableListOf<ProjectData>()

    override fun enableEventBus(): Boolean = false

    override fun createPresenter(): ProjectContract.Presenter = ProjectPresenter()

    override fun layoutRes(): Int = R.layout.fragment_project

    override fun loadData() {
        mPresenter?.getProjects()
    }

    private val projectPageAdapter: ProjectPageAdapter by lazy {
        ProjectPageAdapter(datas, childFragmentManager)
    }

    override fun initView(view: View) {
        super.initView(view)

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tablayout))

        tablayout.run {
            setupWithViewPager(viewPager)
            addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
            addOnTabSelectedListener(onTabSelectListener)
        }
    }

    override fun scroll2Top() {
        if (projectPageAdapter.count == 0) {
            return
        }

        val fragment = projectPageAdapter.getItem(viewPager.currentItem)
        (fragment as ProjectListFragment).scrollToTop()
    }

    override fun setProjects(projects: MutableList<ProjectData>) {
        datas.addAll(projects)
        viewPager.run {
            adapter = projectPageAdapter
            offscreenPageLimit = datas.size
        }
    }

    private val onTabSelectListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(p0: TabLayout.Tab?) {
        }

        override fun onTabUnselected(p0: TabLayout.Tab?) {
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.let {
                viewPager.setCurrentItem(it.position, false)
            }
        }
    }
}