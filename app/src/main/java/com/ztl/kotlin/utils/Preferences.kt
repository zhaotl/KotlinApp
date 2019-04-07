package com.ztl.kotlin.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.Preference
import com.ztl.kotlin.app.App
import java.io.*
import java.lang.IllegalArgumentException
import kotlin.reflect.KProperty

// SharedPreferences 委托类
class Preferences<T>(val key: String, private val default: T) {

    companion object {
        private val filename = "ztl.kotlin"

        private val prefs: SharedPreferences by lazy {
            App.context.getSharedPreferences(filename, Context.MODE_PRIVATE)
        }

        fun clearPreference() = prefs.edit().clear().apply()

        fun delete(key: String) = prefs.edit().remove(key).apply()

        fun contains(key: String): Boolean = prefs.contains(key)

    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>):T {
        return getPreference(key, default)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        setPreference(key, value)
    }

    private fun getPreference(key: String, default: T): T = with(prefs) {
        val ret: Any = when(default) {
            is Long -> getLong(key, default)
            is Int  -> getInt(key, default)
            is String -> getString(key, default)
            is Boolean -> getBoolean(key, default)
            is Float -> getFloat(key, default)
            else -> throw IllegalArgumentException("Preference can not get this type")
        }

        return ret as T
    }

    private fun setPreference(key: String, value: T) = with(prefs.edit()) {
        when(value) {
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            is Boolean -> putBoolean(key, value)
            is String -> putString(key, value)
            else -> throw IllegalArgumentException("Preference can not set this type")
        }.apply()
    }

}