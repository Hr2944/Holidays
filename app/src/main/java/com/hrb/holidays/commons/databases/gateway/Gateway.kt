package com.hrb.holidays.commons.databases.gateway

interface Gateway<T> {
    fun fetch(): T
    suspend fun update(newValue: T): T
    suspend fun initialize(): T
}
