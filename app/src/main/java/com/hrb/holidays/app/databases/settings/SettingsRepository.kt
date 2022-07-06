package com.hrb.holidays.app.databases.settings

import android.content.Context
import androidx.datastore.core.DataStore
import com.hrb.holidays.app.databases.proto.SettingsProto
import com.hrb.holidays.app.databases.proto.ThemeProto
import com.hrb.holidays.app.entities.settings.Settings
import com.hrb.holidays.app.entities.settings.Theme
import com.hrb.holidays.app.databases.proto.ProtoSerializerToGateway

class SettingsRepository(
    context: Context,
    store: DataStore<SettingsProto> = context.settingsDataStore
) : ProtoSerializerToGateway<Settings, SettingsProto>(store), ISettingsRepository {
    override var fetchedValue: Settings = Settings(theme = Theme.LIGHT)

    override fun SettingsProto.toEntity(): Settings {
        return Settings(
            theme = when (this.theme) {
                ThemeProto.THEME_LIGHT -> Theme.LIGHT
                ThemeProto.THEME_DARK -> Theme.DARK
                else -> Theme.LIGHT
            }
        )
    }

    override suspend fun updateStoreData(proto: SettingsProto, newValue: Settings): SettingsProto {
        return proto.toBuilder()
            .setTheme(
                when (newValue.theme) {
                    Theme.LIGHT -> ThemeProto.THEME_LIGHT
                    Theme.DARK -> ThemeProto.THEME_DARK
                }
            )
            .build()
    }
}
