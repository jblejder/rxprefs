package com.projectblejder.rxprefs

import android.content.SharedPreferences
import com.projectblejder.rxprefs.Readers.bools
import com.projectblejder.rxprefs.Readers.floats
import com.projectblejder.rxprefs.Readers.ints
import com.projectblejder.rxprefs.Readers.long
import com.projectblejder.rxprefs.Readers.stringSets
import com.projectblejder.rxprefs.Readers.strings
import io.reactivex.Observable


val SharedPreferences.rx get() = RxPrefs.from(this)

class RxPrefs(val sharedPreferences: SharedPreferences) {

    companion object {
        fun from(sharedPreferences: SharedPreferences) = RxPrefs(sharedPreferences)
    }

    fun string(key: String, default: String): Observable<String> {
        return PrefsChangedObservable(sharedPreferences, defaultChangePublisher(key, strings(default)))
    }

    fun string(key: String): Observable<Changed<String?>> {
        return PrefsChangedObservable(sharedPreferences, defaultChangePublisher(key, strings))
    }

    fun int(key: String, default: Int): Observable<Int> {
        return PrefsChangedObservable(sharedPreferences, defaultChangePublisher(key, ints(default)))
    }

    fun stringSet(key: String, default: Set<String>): Observable<Set<String>> {
        return PrefsChangedObservable(sharedPreferences, defaultChangePublisher(key, stringSets(default)))
    }

    fun stringSet(key: String): Observable<Set<String>> {
        return PrefsChangedObservable(sharedPreferences, defaultChangePublisher(key, stringSets))
    }

    fun long(key: String, default: Long): Observable<Long> {
        return PrefsChangedObservable(sharedPreferences, defaultChangePublisher(key, long(default)))
    }

    fun float(key: String, default: Float): Observable<Float> {
        return PrefsChangedObservable(sharedPreferences, defaultChangePublisher(key, floats(default)))
    }

    fun boolean(key: String, default: Boolean): Observable<Boolean> {
        return PrefsChangedObservable(sharedPreferences, defaultChangePublisher(key, bools(default)))
    }

    fun all(): Observable<Map<String, *>> {
        return PrefsChangedObservable(sharedPreferences) { observer, prefs, _ -> observer.onNext(sharedPreferences.all) }
    }

    fun <T> custom(publisher: OnPrefsChangedPublisher<T>): Observable<T> {
        return PrefsChangedObservable(sharedPreferences, publisher)
    }

    fun edit() = sharedPreferences.edit()

    fun edit(action: SharedPreferences.Editor.() -> Unit) {
        val edit = sharedPreferences.edit()
        edit.action()
        edit.apply()
    }

    fun editNow(action: SharedPreferences.Editor.() -> Unit) {
        val edit = sharedPreferences.edit()
        edit.action()
        edit.commit()
    }
}
