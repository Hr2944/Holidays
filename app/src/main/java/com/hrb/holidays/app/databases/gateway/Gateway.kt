package com.hrb.holidays.app.databases.gateway

interface Gateway<T> {
    fun fetch(): T
    suspend fun update(newValue: T): T
    suspend fun initialize(): T
}
