package com.projectblejder.rxprefs

import android.content.SharedPreferences
import io.reactivex.Observer


interface ValueReader<T> {
    fun get(sharedPreferences: SharedPreferences, key: String): T
}

interface OnPrefsChangedPublisher<T> {
    fun onChange(observer: Observer<in T>, sharedPreferences: SharedPreferences, updatedKey: String)
}

fun <T> valueReader(lambda: (sharedPreferences: SharedPreferences, key: String) -> T): ValueReader<T> {
    return object : ValueReader<T> {
        override fun get(sharedPreferences: SharedPreferences, key: String): T {
            return lambda(sharedPreferences, key)
        }
    }
}

fun <T> onPrefsChanged(lambda: (observer: Observer<in T>, sharedPreferences: SharedPreferences, updatedKey: String) -> Unit): OnPrefsChangedPublisher<T> {
    return object : OnPrefsChangedPublisher<T> {
        override fun onChange(observer: Observer<in T>, sharedPreferences: SharedPreferences, updatedKey: String) {
            lambda(observer, sharedPreferences, updatedKey)
        }
    }
}