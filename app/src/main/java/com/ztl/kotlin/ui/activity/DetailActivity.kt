package com.ztl.kotlin.ui.activity

import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.text.Html
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.just.agentweb.AgentWeb
import com.just.agentweb.NestedScrollAgentWebView
import com.ztl.kotlin.R
import com.ztl.kotlin.base.BaseMvpActivity
import com.ztl.kotlin.mvp.contract.DetailContract
import com.ztl.kotlin.mvp.presenter.DetailPresenter
import com.ztl.kotlin.utils.Constant
import com.ztl.kotlin.utils.KLogger
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.main_tool_bar.*

class DetailActivity: BaseMvpActivity<DetailContract.View, DetailContract.Presenter>(), DetailContract.View {

    var agentweb: AgentWeb? = null
    lateinit var articleTitle : String
    var articleId: Int = 0
    lateinit var articleUrl: String

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

    override fun onAddFavorite(success: Boolean) {
        KLogger.d("add favorite $success")
    }

    override fun onDelFavorite(success: Boolean) {
        KLogger.d("delete favorite $success")
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