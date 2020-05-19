package com.unito.easypay.data

interface Mappable<T : Any> {


    fun mapToResult(): Result<T>
}
