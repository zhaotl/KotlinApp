package com.ztl.kotlin.ui.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBarDrawerToggle
import android.widget.TextView
import com.ztl.kotlin.R
import com.ztl.kotlin.base.BaseMvpActivity
import com.ztl.kotlin.mvp.contract.MainContract
import com.ztl.kotlin.mvp.presenter.MainPresenter
import com.ztl.kotlin.ui.fragment.HomeFragment
import com.ztl.kotlin.utils.Constant
import com.ztl.kotlin.utils.KLogger
import com.ztl.kotlin.utils.Preferences
import kotlinx.android.synthetic.main.activity_maintab.*
import kotlinx.android.synthetic.main.main_tool_bar.*

class MainActivity : BaseMvpActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {

    private var username: String by Preferences(Constant.CONST_USERNAME_KEY, "")

    private var nav_username: TextView? = null
    private var fragment_index: Int = Constant.CONST_FRAGMENT_HOME

    private var homeFragment: HomeFragment? = null

    override fun enableEventBus(): Boolean = false

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
            }
        }
    }

    private fun showFragment(index: Int) {
        val transation = supportFragmentManager.beginTransaction()
        hideFragment(transation)
        fragment_index = index

        when(index) {
            Constant.CONST_FRAGMENT_HOME -> {
                if (homeFragment == null) {
                    homeFragment = HomeFragment()
                    transation.add(R.id.main_container_layout, homeFragment!!, "home")
                } else {
                    homeFragment?.let {
                        transation.show(it)
                    }
                }
            }
        }

        transation.commit()
    }

    private fun hideFragment(transation: FragmentTransaction) {
        homeFragment?.let { transation.hide(it) }

    }

    private val bottomNaviSelectedListner =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            return@OnNavigationItemSelectedListener when (item.itemId) {
                R.id.item_home -> {
                    true
                }

                R.id.item_knowledge -> {
                    true
                }

                R.id.item_subscription -> {

                    true
                }

                R.id.item_guide -> {
                    true
                }

                R.id.item_project -> {
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
}