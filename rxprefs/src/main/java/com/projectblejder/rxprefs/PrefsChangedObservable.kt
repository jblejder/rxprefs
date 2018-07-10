package com.projectblejder.rxprefs

import android.content.SharedPreferences
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable


class PrefsChangedObservable<T>(
        private val sharedPreferences: SharedPreferences,
        private val publisher: OnPrefsChangedPublisher<T>
) : Observable<T>() {

    override fun subscribeActual(observer: Observer<in T>) {
        val listener = PrefsListener(sharedPreferences, observer, publisher)
        observer.onSubscribe(listener)
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    class PrefsListener<T>(
            val prefs: SharedPreferences,
            val observer: Observer<in T>,
            val publisher: OnPrefsChangedPublisher<T>
    ) : MainThreadDisposable(), SharedPreferences.OnSharedPreferenceChangeListener {

        override fun onDispose() {
            prefs.unregisterOnSharedPreferenceChangeListener(this)
        }

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
            if (isDisposed) {
                return
            }
            publisher.invoke(observer, sharedPreferences, key)
        }
    }
}
