package com.hrb.holidays

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.hrb.holidays.commons.databases.holidays.IHolidaysTimetableGateway
import com.hrb.holidays.commons.databases.office.IOfficeWeekGateway
import com.hrb.holidays.ui.theme.HolidaysTheme
import com.hrb.holidays.ui.views.timer.TimerScreenActivity
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val officeWeekGateway: IOfficeWeekGateway = get()
        val holidaysTimetableGateway: IHolidaysTimetableGateway = get()
        runBlocking {
            officeWeekGateway.initialize()
            holidaysTimetableGateway.initialize()
        }

        setContent {
            AppMainScreen()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppMainScreen() {
    HolidaysTheme {
        Surface {
            TimerScreenActivity()
        }
    }
}
