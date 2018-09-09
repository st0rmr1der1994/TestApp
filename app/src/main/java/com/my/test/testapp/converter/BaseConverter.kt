package com.my.test.testapp.converter

abstract class BaseConverter<S,R> : Converter<S, R> {

    override fun convert(sources: List<S>): List<R>  = sources.map { convert(it) }
}
