package com.ztl.kotlin.ui.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBarDrawerToggle
import android.widget.TextView
import com.ztl.kotlin.R
import com.ztl.kotlin.base.BaseMvpActivity
import com.ztl.kotlin.event.RefreshHomeEvent
import com.ztl.kotlin.mvp.contract.MainContract
import com.ztl.kotlin.mvp.presenter.MainPresenter
import com.ztl.kotlin.ui.fragment.HomeFragment
import com.ztl.kotlin.ui.fragment.KnowledgeListFragment
import com.ztl.kotlin.ui.fragment.ProjectFragment
import com.ztl.kotlin.ui.fragment.SharedArticlesFragment
import com.ztl.kotlin.utils.Constant
import com.ztl.kotlin.utils.KLogger
import com.ztl.kotlin.utils.Preferences
import kotlinx.android.synthetic.main.activity_maintab.*
import kotlinx.android.synthetic.main.main_tool_bar.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : BaseMvpActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {

    private var username: String by Preferences(Constant.CONST_USERNAME_KEY, "")

    private var nav_username: TextView? = null
    private var fragment_index: Int = Constant.CONST_FRAGMENT_HOME

    private var homeFragment: HomeFragment? = null
    private var knowledgelistFragment: KnowledgeListFragment? = null
    private var substribeFragment: SharedArticlesFragment? = null
    private var projectFragment: ProjectFragment? = null

    override fun enableEventBus(): Boolean = true

    override fun createPresenter(): MainContract.Presenter = MainPresenter()

    override fun layoutRes(): Int = R.layout.activity_maintab

    override fun onLogout(success: Boolean) {
        username = ""
        isLogin = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        super.initView()

        // action tool bar
        initToolBar()

        // bottom navigation
        initBottomNavi()

        // drawerlayout
        initDrawerLayout()

        // slide navigation view
        initNavigation()

        showFragment(fragment_index)

        initFloatAction()
    }

    private fun initToolBar() = main_tool_bar.run {
        title = getString(R.string.app_name)
        setSupportActionBar(this)
    }

    private fun initBottomNavi() = main_bottom_nav.run {
        labelVisibilityMode = 1
        setOnNavigationItemSelectedListener(bottomNaviSelectedListner)
    }

    private fun initDrawerLayout() = drawer_layout.run {
        val mToggle = ActionBarDrawerToggle(this@MainActivity, this, main_tool_bar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        addDrawerListener(mToggle)
        mToggle.syncState()
    }

    private fun initNavigation() {
        main_nav_view.run {
            KLogger.d("isLogin = $isLogin")
            setNavigationItemSelectedListener(naviItemSelectedListener)
            menu.findItem(R.id.nav_logout).isVisible = isLogin
            nav_username = getHeaderView(0).findViewById(R.id.nav_username)
        }

        nav_username?.let {
            when(isLogin) {
                true -> {
                    it.text = username
                }

                false -> {
                    it.text = getString(R.string.title_login)
                    it.setOnClickListener{
                        start<LoginActivity>()
                    }
                }
            }

        }
    }

    private fun initFloatAction() {
        main_float_action.setOnClickListener{
            when(fragment_index) {
                Constant.CONST_FRAGMENT_HOME -> {
                    homeFragment?.scrollToTop()
                }

                Constant.CONST_FRAGMENT_KNOWLEDGE -> {
                    knowledgelistFragment?.scrollToTop()
                }

                Constant.CONST_FRAGMENT_GUIDE -> {

                }

                Constant.CONST_FRAGMENT_SUBSCRIPTION -> {
                    substribeFragment?.scroll2Top()
                }

                Constant.CONST_FRAGMENT_PROJECT -> {
                    projectFragment?.scroll2Top()
                }
            }
        }
    }

    private fun showFragment(index: Int) {
        val transation = supportFragmentManager.beginTransaction()
        hideFragment(transation)
        fragment_index = index

        KLogger.d("index = $index")
        when(index) {
            Constant.CONST_FRAGMENT_HOME -> {
                if (homeFragment == null) {
                    homeFragment = HomeFragment()
                    transation.add(R.id.main_container_layout, homeFragment!!, "home")
                } else {
                    KLogger.d("show home fragment 1.")
                    homeFragment?.let {
                        KLogger.d("show home fragment 2.")
                        transation.show(it)
                    }
                }
            }

            Constant.CONST_FRAGMENT_KNOWLEDGE -> {
                if (knowledgelistFragment == null) {
                    knowledgelistFragment = KnowledgeListFragment()
                    transation.add(R.id.main_container_layout, knowledgelistFragment!!, "knowledge")
                } else {
                    knowledgelistFragment?.let {
                        transation.show(it)
                    }
                }

            }

            Constant.CONST_FRAGMENT_SUBSCRIPTION -> {
                if (substribeFragment == null) {
                    substribeFragment = SharedArticlesFragment.getInstance()
                    transation.add(R.id.main_container_layout, substribeFragment!!, "subscribe")
                } else {
                    substribeFragment?.let {
                        transation.show(it)
                    }
                }
            }

            Constant.CONST_FRAGMENT_PROJECT -> {
                if (projectFragment == null) {
                    projectFragment = ProjectFragment.getInstance()
                    transation.add(R.id.main_container_layout, projectFragment!!, "project")
                } else {
                    projectFragment?.let {
                        transation.show(it)
                    }
                }
            }
        }

        transation.commit()
    }

    private fun hideFragment(transation: FragmentTransaction) {
        homeFragment?.let { transation.hide(it) }
        knowledgelistFragment?.let { transation.hide(it) }
        substribeFragment?.let { transation.hide(it) }
        projectFragment?.let { transation.hide(it) }
    }

    private val bottomNaviSelectedListner =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            return@OnNavigationItemSelectedListener when (item.itemId) {
                R.id.item_home -> {
                    showFragment(Constant.CONST_FRAGMENT_HOME)
                    true
                }

                R.id.item_knowledge -> {
                    showFragment(Constant.CONST_FRAGMENT_KNOWLEDGE)
                    true
                }

                R.id.item_subscription -> {
                    showFragment(Constant.CONST_FRAGMENT_SUBSCRIPTION)
                    true
                }

                R.id.item_guide -> {
                    showFragment(Constant.CONST_FRAGMENT_GUIDE)
                    true
                }

                R.id.item_project -> {
                    showFragment(Constant.CONST_FRAGMENT_PROJECT)
                    true
                }

                else -> {
                    false
                }
            }
        }

    private val naviItemSelectedListener =
            NavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_like -> {
                        KLogger.d("favorite")
                    }

                    R.id.nav_setting -> {
                        KLogger.d("setting")
                    }

                    R.id.nav_night -> {
                        KLogger.d("night")
                    }

                    R.id.nav_logout -> {
                        KLogger.d("logout")
                    }

                }
                true
            }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshHomeEvent(event: RefreshHomeEvent) {
        if (event.refresh) {
            homeFragment?.loadData()
        }
    }
}