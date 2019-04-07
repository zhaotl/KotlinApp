package com.ztl.kotlin.widget

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.ztl.kotlin.R

@Deprecated("this api can be replaced by CustomToast")
class CustomToast1 {

    private var toast: Toast

    private constructor(context: Context, msg: String): this(context, msg, Toast.LENGTH_SHORT)

    private constructor(context: Context, message: String, duration: Int) {
        toast = Toast(context)
        toast.duration = duration

        val view = View.inflate(context, R.layout.widget_toast, null)
        val toast_msg = view.findViewById<TextView>(R.id.toast_msg)
        toast_msg.text = message
        toast.view = view
        toast.setGravity(Gravity.TOP, 0, 0)
    }

    fun show() {
        toast.show()
    }

}