package com.hrb.holidays.koindi.interactors

import com.hrb.holidays.commons.interactors.holidays.Holidays
import com.hrb.holidays.commons.interactors.holidays.IHolidays
import com.hrb.holidays.commons.interactors.office.IRangeOfficeTimeCalculator
import com.hrb.holidays.commons.interactors.office.IRangeTimeCalculator
import com.hrb.holidays.commons.interactors.office.RangeOfficeTimeCalculator
import com.hrb.holidays.commons.interactors.office.RangeTimeCalculator
import org.koin.dsl.module

val interactorsModules = module {
    single<IHolidays> { Holidays(get()) }
    single<IRangeOfficeTimeCalculator> { RangeOfficeTimeCalculator(get()) }
    single<IRangeTimeCalculator> { RangeTimeCalculator() }
}
