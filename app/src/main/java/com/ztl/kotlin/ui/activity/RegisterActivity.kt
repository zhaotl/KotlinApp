package com.ztl.kotlin.ui.activity

import android.view.View
import com.ztl.kotlin.base.BaseMvpActivity
import com.ztl.kotlin.mvp.contract.RegisterContract
import com.ztl.kotlin.R
import com.ztl.kotlin.mvp.model.bean.LoginData
import com.ztl.kotlin.mvp.presenter.RegisterPresenter
import com.ztl.kotlin.utils.Constant
import com.ztl.kotlin.utils.KLogger
import com.ztl.kotlin.utils.Preferences
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity: BaseMvpActivity<RegisterContract.View, RegisterContract.Presenter>(), RegisterContract.View {

    private var user: String by Preferences(Constant.CONST_LOGINTOKEN_KEY, "")

    override fun createPresenter(): RegisterContract.Presenter = RegisterPresenter()

    override fun layouRes(): Int = R.layout.activity_register

    override fun enableEventBus(): Boolean = false

    override fun initView() {
        super.initView()

        KLogger.d("RegisterActivity initView ")

        register_button.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                register()
            }

        })
    }

    private fun register() {
        val name = username.text.toString()
        val pwd = password.text.toString()
        val repeatPwd = repeat_password.text.toString()

        if (name.isEmpty()) {
            username.error = "用户名不能为空"
        } else if (pwd.isEmpty()) {
            password.error = "密码不能为空"
        } else if (pwd.length < 4) {
            password.error = "密码长度必须大于4"
        } else if (!pwd.equals(repeatPwd)) {
            repeat_password.error = "两次密码必须相同"
        } else {
            mPresenter?.register(name, pwd, repeatPwd)
        }
    }

    override fun onRegisterSuccess(data: LoginData) {
        KLogger.d("register success")

        user = data.username

    }

    override fun showError(errMsg: String) {
        KLogger.e("register failed")
    }
}