package com.projectblejder.rxprefs

import android.content.SharedPreferences
import io.reactivex.Observer


typealias ValueReader<T> = (sharedPreferences: SharedPreferences, key: String) -> T

typealias OnPrefsChangedPublisher<T> = (observer: Observer<in T>, sharedPreferences: SharedPreferences, updatedKey: String) -> Unit
