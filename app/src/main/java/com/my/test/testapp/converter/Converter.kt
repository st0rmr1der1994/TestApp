package com.my.test.testapp.converter

interface Converter<S, R> {

    fun convert(source: S): R

    fun convert(sources: List<S>): List<R>
}
