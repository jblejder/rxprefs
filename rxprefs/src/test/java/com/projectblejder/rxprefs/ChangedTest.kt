package com.projectblejder.rxprefs

import org.junit.Assert
import org.junit.Test

class ChangedTest {

    @Test
    fun `it stores data`() {
        val changed = Changed("data")

        Assert.assertEquals("data", changed.value)
    }

    @Test
    fun `it stores nulls`() {
        val changed = Changed<String?>(null)

        Assert.assertEquals(null, changed.value)
    }

    @Test
    fun `it shows if data is present`() {
        val changed = Changed("data")

        Assert.assertEquals(true, changed.hasValue)
        Assert.assertEquals(false, changed.isEmpty)
    }

    @Test
    fun `it shows if data is not present`() {
        val changed = Changed<String?>(null)

        Assert.assertEquals(true, changed.isEmpty)
        Assert.assertEquals(false, changed.hasValue)
    }
}
