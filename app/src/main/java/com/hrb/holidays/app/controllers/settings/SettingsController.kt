package com.hrb.holidays.app.controllers.settings

import com.hrb.holidays.app.databases.settings.ISettingsRepository
import com.hrb.holidays.app.entities.settings.Settings
import com.hrb.holidays.app.entities.settings.Theme

class SettingsController(
    private val settingsRepository: ISettingsRepository
) : ISettingsController {
    override fun getSettings(): Settings {
        return settingsRepository.fetch()
    }

    override fun getTheme(): Theme {
        return getSettings().theme
    }

    override suspend fun updateSettings(settings: Settings) {
        if (settings != getSettings()) {
            settingsRepository.update(settings)
        }
    }

    override suspend fun updateTheme(theme: Theme): Settings {
        updateSettings(getSettings().copy(theme = theme))
        return getSettings()
    }
}
