package com.projectblejder.rxprefs


data class Changed<T>(val value: T) {
    val isEmpty = value == null
    val hasValue = !isEmpty
}
