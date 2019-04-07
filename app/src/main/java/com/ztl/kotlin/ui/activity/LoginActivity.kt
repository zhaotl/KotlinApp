package com.ztl.kotlin.ui.activity

import android.view.View
import com.ztl.kotlin.R
import com.ztl.kotlin.base.BaseMvpActivity
import com.ztl.kotlin.event.LoginEvent
import com.ztl.kotlin.mvp.contract.LoginContract
import com.ztl.kotlin.mvp.model.bean.LoginData
import com.ztl.kotlin.mvp.presenter.LoginPresenter
import com.ztl.kotlin.utils.Constant
import com.ztl.kotlin.utils.KLogger
import com.ztl.kotlin.utils.Preferences
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus

class LoginActivity : BaseMvpActivity<LoginContract.View, LoginContract.Presenter>(), LoginContract.View {

    override fun createPresenter(): LoginContract.Presenter = LoginPresenter()

    private var user: String by Preferences(Constant.CONST_USERNAME_KEY, "")
    private var token: String by Preferences(Constant.CONST_LOGINTOKEN_KEY, "")

    override fun layouRes(): Int = R.layout.activity_login

    override fun enableEventBus(): Boolean = false

    override fun initView() {
        super.initView()

        KLogger.d("initview username = ${user}")
        username.setText(user)
        login_button.setOnClickListener(onClickListener)
        register_text.setOnClickListener(onClickListener)
    }

    private val onClickListener = View.OnClickListener {
        when (it.id) {
            R.id.login_button -> login()
            R.id.register_text -> {
                start<RegisterActivity>()
                finish();
                overridePendingTransition(0, 0)
            }
        }
    }

    private fun login() {
        KLogger.d("start login")
        if (check()) {
            mPresenter?.login(username.text.toString(), password.text.toString()) ?: KLogger.e("Presenser is null")
        }
    }

    private fun check(): Boolean {
        var result = true

        val name = username.text.toString()
        val pwd = password.text.toString()

        if (name.isEmpty()) {
            username.error = getString(R.string.username_not_empty)
            result = false
        } else if (pwd.isEmpty() || pwd.length < 4) {
            password.error = getString(R.string.pwd_not_empty)
            result = false
        }

        KLogger.d("result = ${result}")
        return result
    }

    override fun onLoginSuccess(data: LoginData) {
        KLogger.d("username = ${data.username}")
        KLogger.d("pwd = ${data.password}")
        KLogger.d("token = ${data.token}")
        user = data.username
        token = data.token

        showMessage("欢迎回来，${user}")

        EventBus.getDefault().post(LoginEvent(true))

    }

    override fun onLoginFailed(errMsg: String) {
        KLogger.e("login failed: ${errMsg}")
        username.error = errMsg
    }
}