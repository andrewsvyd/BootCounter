package com.svyd.bootcounter.common.mapper

interface TypeMapper<in I, out O> {
    fun map(input : I) : O
}