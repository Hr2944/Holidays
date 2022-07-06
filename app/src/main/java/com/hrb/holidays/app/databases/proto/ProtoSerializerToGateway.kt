package com.hrb.holidays.app.databases.proto

import androidx.datastore.core.DataStore
import com.hrb.holidays.app.databases.gateway.Gateway
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

abstract class ProtoSerializerToGateway<Entity, Proto>(
    private val store: DataStore<Proto>
) : Gateway<Entity> {
    private var flow: Flow<Entity> = store.data.map { it.toEntity() }
    private var isInitialized = false

    abstract var fetchedValue: Entity

    abstract fun Proto.toEntity(): Entity
    abstract suspend fun updateStoreData(proto: Proto, newValue: Entity): Proto

    override suspend fun initialize(): Entity {
        fetchedValue = flow.first()
        isInitialized = true
        return fetchedValue
    }

    private suspend fun copyStoreToInternalData() {
        fetchedValue = flow.first()
    }

    override fun fetch(): Entity {
        if (!isInitialized) {
            throw IllegalStateException("$this should be initialized before calling fetch() method!")
        }
        return fetchedValue
    }

    override suspend fun update(newValue: Entity): Entity {
        store.updateData {
            updateStoreData(it, newValue)
        }
        copyStoreToInternalData()
        return fetch()
    }
}
