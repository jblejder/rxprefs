package com.projectblejder.rxprefs

import android.content.Context
import android.content.SharedPreferences
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment


@RunWith(RobolectricTestRunner::class)
class PrefsChangedObservableTest {

    lateinit var preferences: SharedPreferences


    @Test
    fun `it does not register to shared preferences changes if subscribe is not called`() {
        PrefsChangedObservable<Any>(preferences, onPrefsChanged { _, _, _ -> Unit })

        verifyZeroInteractions(preferences)
    }

    @Test
    fun `it registers to shared preferences changes on subscribe`() {
        val prefs = PrefsChangedObservable<Any>(preferences, onPrefsChanged { _, _, _ -> Unit })

        prefs.subscribe()

        verify(preferences).registerOnSharedPreferenceChangeListener(prefs.listener)
    }

    @Test
    fun `it unregisters on dispose from preferences`() {
        val prefs = PrefsChangedObservable<Any>(preferences, onPrefsChanged { _, _, _ -> Unit })

        prefs.subscribe().dispose()

        verify(preferences).unregisterOnSharedPreferenceChangeListener(prefs.listener)
    }

    @Test
    fun `it invokes publisher function on preferences changed`() {
        var publisherUpdatedKey: String? = null
        val publisher = onPrefsChanged<Any> { _, _, updatedKey -> publisherUpdatedKey = updatedKey }

        PrefsChangedObservable(preferences, publisher).test()

        val edit = preferences.edit()
        edit.putString("my key", "value")
        edit.commit()

        Assert.assertEquals("my key", publisherUpdatedKey)
    }

    @Before
    fun before() {
        preferences = RuntimeEnvironment.application.getSharedPreferences("test", Context.MODE_PRIVATE)
        preferences = spy(preferences)
    }
}
