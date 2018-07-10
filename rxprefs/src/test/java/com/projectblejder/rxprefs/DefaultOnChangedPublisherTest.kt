package com.projectblejder.rxprefs

import android.content.Context
import android.content.SharedPreferences
import com.nhaarman.mockitokotlin2.*
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class DefaultOnChangedPublisherTest {

    lateinit var preferences: SharedPreferences
    lateinit var valueReader: ValueReader<Any>
    lateinit var observer: PublishSubject<Any>

    @Test
    fun `it created publisher that publishes events on specified key from reader`() {
        stub {
            on { valueReader.get(any(), eq("my key")) } doReturn "precious value"
        }
        val publisher = defaultChangePublisher("my key", valueReader)
        val test = observer.test()

        publisher.onChange(observer, preferences, "my key")
        publisher.onChange(observer, preferences, "not my key")

        test.assertValueCount(1)
        test.assertValue("precious value")
    }

    @Before
    fun before() {
        preferences = RuntimeEnvironment.application.getSharedPreferences("data", Context.MODE_PRIVATE)
        valueReader = mock { ValueReader::class.java }
        observer = PublishSubject.create()
    }
}
