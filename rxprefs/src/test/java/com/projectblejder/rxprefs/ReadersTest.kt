package com.projectblejder.rxprefs

import android.content.Context
import android.content.SharedPreferences
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ReadersTest {

    lateinit var preferences: SharedPreferences

    @Test
    fun `it created string reader with default value`() {
        val reader = Readers.strings("default value")
        val value = reader.get(preferences, "key")

        Assert.assertEquals("default value", value)
    }

    @Test
    fun `it created string reader`() {
        val edit = preferences.edit()
        edit.putString("key", "my value")
        edit.commit()

        val reader = Readers.strings("default value")
        val value = reader.get(preferences, "key")

        Assert.assertEquals("my value", value)
    }

    @Test
    fun `it created string reader that can wrapped in Changed model`() {
        val reader = Readers.strings
        val value = reader.get(preferences, "key")

        Assert.assertEquals(Changed(null), value)
    }

    @Test
    fun `it creates set string reader with default value`() {
        val set = setOf("one", "two", "three")

        val reader = Readers.stringSets(set)
        val value = reader.get(preferences, "key")

        Assert.assertEquals(set, value)
    }

    @Test
    fun `it creates set string reader wrapped in Changed model`() {
        val set = setOf("one", "two", "three")

        val editor = preferences.edit()
        editor.putStringSet("key", set)
        editor.commit()

        val reader = Readers.stringSets
        val value = reader.get(preferences, "key")

        Assert.assertEquals(Changed(set), value)
    }

    @Test
    fun `it creates int reader`() {
        val editor = preferences.edit()
        editor.putInt("key", 27)
        editor.commit()

        val reader = Readers.ints(15)

        Assert.assertEquals(27, reader.get(preferences, "key"))
    }

    @Test
    fun `it creates int reader with default value`() {
        val reader = Readers.ints(15)

        Assert.assertEquals(15, reader.get(preferences, "key"))
    }

    @Test
    fun `it creates float reader`() {
        val editor = preferences.edit()
        editor.putFloat("key", 27f)
        editor.commit()

        val reader = Readers.floats(15f)

        Assert.assertEquals(27f, reader.get(preferences, "key"))
    }

    @Test
    fun `it creates float reader with default value`() {
        val reader = Readers.floats(15f)

        Assert.assertEquals(15f, reader.get(preferences, "key"))
    }

    @Test
    fun `it creates boolean reader`() {
        val editor = preferences.edit()
        editor.putBoolean("key", true)
        editor.commit()

        val reader = Readers.bools(false)

        Assert.assertEquals(true, reader.get(preferences, "key"))
    }

    @Test
    fun `it creates boolean reader with default value`() {
        val reader = Readers.bools(true)

        Assert.assertEquals(true, reader.get(preferences, "key"))
    }

    @Before
    fun before() {
        preferences = RuntimeEnvironment.application.getSharedPreferences("data", Context.MODE_PRIVATE)
    }
}