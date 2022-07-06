package com.hrb.holidays.app.presenters.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hrb.holidays.app.controllers.settings.ISettingsController
import com.hrb.holidays.app.entities.settings.Theme

class SettingsScreenPresenter(
    private val controller: ISettingsController
) : ViewModel() {
    var settingsState: SettingsState by mutableStateOf(initState())

    private fun initState(): SettingsState {
        return SettingsState(
            theme = getTheme()
        )
    }

    private fun getTheme(): Theme {
        return controller.getTheme()
    }

    suspend fun updateTheme(theme: Theme) {
        updateSettingsState(
            theme = controller.updateTheme(theme).theme
        )
    }

    private fun updateSettingsState(
        theme: Theme = settingsState.theme
    ) {
        if (theme != settingsState.theme) {
            settingsState = settingsState.copy(
                theme = theme
            )
        }
    }
}
