package com.projectblejder.rxprefs


internal object Readers {

    fun strings(default: String): ValueReader<String> = { pref, key -> pref.getString(key, default) }

    val strings: ValueReader<Changed<String?>> = { pref, key -> Changed(pref.getString(key, null)) }

    fun ints(default: Int): ValueReader<Int> = { pref, key -> pref.getInt(key, default) }

    fun stringSets(default: Set<String>): ValueReader<Set<String>> = { pref, key -> pref.getStringSet(key, default) }

    val stringSets: ValueReader<Set<String>> = { pref, key -> pref.getStringSet(key, null) }

    fun long(default: Long): ValueReader<Long> = { pref, key -> pref.getLong(key, default) }

    fun floats(default: Float): ValueReader<Float> = { pref, key -> pref.getFloat(key, default) }

    fun bools(default: Boolean): ValueReader<Boolean> = { pref, key -> pref.getBoolean(key, default) }
}

fun <T> defaultChangePublisher(key: String, reader: ValueReader<T>): OnPrefsChangedPublisher<T> =
        { observer, sharedPreferences, updatedKey ->
            if (updatedKey == key) {
                observer.onNext(reader.invoke(sharedPreferences, key))
            }
        }
