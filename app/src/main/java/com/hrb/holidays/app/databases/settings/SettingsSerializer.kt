package com.hrb.holidays.app.databases.settings

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import com.hrb.holidays.app.databases.proto.SettingsProto
import com.hrb.holidays.app.databases.proto.ThemeProto
import com.hrb.holidays.app.databases.proto.settingsProto
import java.io.InputStream
import java.io.OutputStream

internal object SettingsSerializer : Serializer<SettingsProto> {
    override val defaultValue: SettingsProto = initSettings()

    private fun initSettings(): SettingsProto {
        return settingsProto {
            theme = ThemeProto.THEME_LIGHT
        }
    }

    override suspend fun readFrom(input: InputStream): SettingsProto {
        try {
            return SettingsProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: SettingsProto, output: OutputStream) = t.writeTo(output)
}

val Context.settingsDataStore: DataStore<SettingsProto> by dataStore(
    fileName = "settings.pb",
    serializer = SettingsSerializer,
    corruptionHandler = ReplaceFileCorruptionHandler(
        produceNewData = { SettingsProto.getDefaultInstance() }
    )
)
