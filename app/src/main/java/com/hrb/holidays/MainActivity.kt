package com.hrb.holidays

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.hrb.holidays.app.databases.settings.ISettingsRepository
import com.hrb.holidays.app.entities.settings.Theme
import com.hrb.holidays.app.presenters.settings.SettingsScreenPresenter
import com.hrb.holidays.app.databases.holidays.IHolidaysTimetableGateway
import com.hrb.holidays.app.databases.office.IOfficeWeekGateway
import com.hrb.holidays.ui.theme.HolidaysTheme
import com.hrb.holidays.ui.views.base.BaseScreenActivity
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.get
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val officeWeekGateway: IOfficeWeekGateway = get()
        val holidaysTimetableGateway: IHolidaysTimetableGateway = get()
        val settingsRepository: ISettingsRepository = get()
        runBlocking {
            officeWeekGateway.initialize()
            holidaysTimetableGateway.initialize()
            settingsRepository.initialize()
        }

        setContent {
            AppMainScreen()
        }
    }
}

@Composable
fun AppMainScreen(settingsPresenter: SettingsScreenPresenter = getViewModel()) {
    HolidaysTheme(
        darkTheme = settingsPresenter.settingsState.theme == Theme.DARK
    ) {
        Surface {
            BaseScreenActivity()
        }
    }
}
