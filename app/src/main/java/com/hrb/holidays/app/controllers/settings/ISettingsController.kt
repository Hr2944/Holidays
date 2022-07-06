package com.hrb.holidays.app.controllers.settings

import com.hrb.holidays.app.entities.settings.Settings
import com.hrb.holidays.app.entities.settings.Theme

interface ISettingsController {
    fun getSettings(): Settings
    fun getTheme(): Theme
    suspend fun updateSettings(settings: Settings)
    suspend fun updateTheme(theme: Theme): Settings
}
