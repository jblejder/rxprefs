package com.projectblejder.rxprefs


internal object Readers {

    fun strings(default: String): ValueReader<String> = valueReader { pref, key -> pref.getString(key, default) }

    val strings: ValueReader<Changed<String?>> = valueReader { pref, key -> Changed(pref.getString(key, null)) }

    fun ints(default: Int): ValueReader<Int> = valueReader { pref, key -> pref.getInt(key, default) }

    fun stringSets(default: Set<String>): ValueReader<Set<String>> = valueReader { pref, key -> pref.getStringSet(key, default) }

    val stringSets: ValueReader<Changed<Set<String>?>> = valueReader { pref, key -> Changed(pref.getStringSet(key, null)) }

    fun long(default: Long): ValueReader<Long> = valueReader { pref, key -> pref.getLong(key, default) }

    fun floats(default: Float): ValueReader<Float> = valueReader { pref, key -> pref.getFloat(key, default) }

    fun bools(default: Boolean): ValueReader<Boolean> = valueReader { pref, key -> pref.getBoolean(key, default) }
}

fun <T> defaultChangePublisher(key: String, reader: ValueReader<T>): OnPrefsChangedPublisher<T> =
        onPrefsChanged { observer, sharedPreferences, updatedKey ->
            if (updatedKey == key) {
                observer.onNext(reader.get(sharedPreferences, key))
            }
        }
