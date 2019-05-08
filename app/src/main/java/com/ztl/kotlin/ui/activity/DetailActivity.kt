package com.ztl.kotlin.ui.activity

import android.content.Intent
import android.net.Uri
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.text.Html
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.just.agentweb.AgentWeb
import com.just.agentweb.NestedScrollAgentWebView
import com.ztl.kotlin.R
import com.ztl.kotlin.base.BaseMvpActivity
import com.ztl.kotlin.event.RefreshHomeEvent
import com.ztl.kotlin.mvp.contract.DetailContract
import com.ztl.kotlin.mvp.presenter.DetailPresenter
import com.ztl.kotlin.utils.Constant
import com.ztl.kotlin.utils.KLogger
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.main_tool_bar.*
import org.greenrobot.eventbus.EventBus
import java.net.URI

class DetailActivity: BaseMvpActivity<DetailContract.View, DetailContract.Presenter>(), DetailContract.View {

    var agentweb: AgentWeb? = null
    lateinit var articleTitle : String
    var articleId: Int = 0
    lateinit var articleUrl: String
    var isFavorite: Boolean = false
    var favoriteAction: MenuItem? = null

    override fun createPresenter(): DetailContract.Presenter = DetailPresenter()

    override fun layoutRes(): Int = R.layout.activity_detail

    override fun enableEventBus(): Boolean = false

    override fun initView() {
        super.initView()

        initTitleBar()
        initWebView()
    }

    private fun initTitleBar() {
        main_tool_bar.apply {
            title = ""
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }

        main_bar_title.apply {
            text = ""
            visibility = View.VISIBLE
            postDelayed({
                this.isSelected = true
            }, 2000)
        }

        intent.extras?.let {
            articleTitle = it.getString(Constant.CONTENT_TITLE_KEY, "")
            articleUrl = it.getString(Constant.CONTENT_URL_KEY, "")
            articleId = it.getInt(Constant.CONTENT_ID_KEY, 0)
            isFavorite = it.getBoolean(Constant.CONTENT_FAVORITE_KEY, false)
            KLogger.d("isFavorite = $isFavorite")
        }
    }

    private fun initWebView() {
        val layoutParams = CoordinatorLayout.LayoutParams(-1, -1)
        layoutParams.behavior = AppBarLayout.ScrollingViewBehavior()

        val webview = NestedScrollAgentWebView(this)
        val webChromClient = object : WebChromeClient() {

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                title?.let {
                    main_bar_title?.text = Html.fromHtml(it)
                }
            }
        }

        agentweb = AgentWeb.with(this)
            .setAgentWebParent(detail_content, layoutParams)
            .useDefaultIndicator()
            .setWebView(webview)
            .setWebChromeClient(webChromClient)
            .createAgentWeb()
            .ready()
            .go(articleUrl)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        favoriteAction = menu?.findItem(R.id.action_favorite)
        favoriteAction?.let {
            it.setTitle(if (isFavorite) "取消收藏" else "收藏")
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_favorite -> {
                if (isLogin) {
                    if (!isFavorite) {
                        mPresenter?.addFavorite(articleId)
                    } else {
                        mPresenter?.delFavorite(articleId)
                    }
                } else {
                    showMessage("请先登录")
                    start<LoginActivity>()
                }
            }

            R.id.action_share -> {
                with(Intent()) {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT,
                        getString(
                            R.string.share_article_url,
                            getString(R.string.app_name),
                            articleTitle,
                            articleUrl
                        ))

                    type = "text/plain"
                    startActivity(Intent.createChooser(this, getString(R.string.action_share)))
                }

            }

            R.id.action_browser -> {
                with(Intent()) {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(articleUrl)
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onAddFavorite(success: Boolean) {
        KLogger.d("add favorite $success")
        if (success) {
            isFavorite = true
            favoriteAction?.setTitle("取消收藏")
            showMessage("收藏成功")
            EventBus.getDefault().post(RefreshHomeEvent(true))
        }
    }

    override fun onDelFavorite(success: Boolean) {
        KLogger.d("delete favorite $success")
        if (success) {
            isFavorite = false
            favoriteAction?.setTitle("收藏")
            showMessage("已取消收藏")
            EventBus.getDefault().post(RefreshHomeEvent(true))
        }
    }

    override fun onBackPressed() {
        agentweb?.let {
            if (!it.back()) {
                super.onBackPressed()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        agentweb?.webLifeCycle?.onResume()
    }

    override fun onPause() {
        super.onPause()
        agentweb?.webLifeCycle?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        agentweb?.webLifeCycle?.onDestroy()
    }
}