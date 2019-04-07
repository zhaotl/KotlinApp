package com.ztl.kotlin.widget

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.ztl.kotlin.R

class CustomToast private constructor(builder: Builder) {

    private var toast: Toast
    init {
        toast = Toast(builder.mContext)

        val view = View.inflate(builder.mContext, R.layout.widget_toast, null)
        toast.view = view
        toast.setGravity(builder.position, 0, 50)
        toast.duration = builder.mDuration
    }

    fun show(msg: String) {
        if (msg.isEmpty().not()) {
            toast.view.findViewById<TextView>(R.id.toast_msg).text = msg
            toast.show()
        }
    }

    class Builder constructor(context: Context) {

        var mContext: Context
            private set

        var mDuration: Int = Toast.LENGTH_SHORT
            private set

        var position: Int = Gravity.TOP
            private set

        init {
            mContext = context
        }

        fun duration(duration: Int): Builder {
            mDuration = duration
            return this
        }

        fun gravity(gravity: Int): Builder {
            position = gravity
            return this
        }

        fun build(): CustomToast {
            return CustomToast(this)
        }
    }

}