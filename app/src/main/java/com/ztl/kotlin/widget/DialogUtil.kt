package com.ztl.kotlin.widget

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.TextView
import com.ztl.kotlin.R

object DialogUtil {

    private fun alertDialog(context: Context): AlertDialog.Builder {
        return AlertDialog.Builder(context)
    }

    private fun alertDialog(context: Context, style:Int): AlertDialog.Builder {
        return AlertDialog.Builder(context, style)
    }

    fun getProgress(context: Context, tips: String = ""):AlertDialog {
        val progress = alertDialog(context, R.style.ProgressBar).create()
        val view = View.inflate(context, R.layout.widget_progress, null)
        progress?.setView(view)

        progress?.setCancelable(false)
        progress?.setCanceledOnTouchOutside(false)

        if (tips.isEmpty().not()) {
            view.findViewById<TextView>(R.id.progress_tips).text = tips
        }

        return progress

    }

}