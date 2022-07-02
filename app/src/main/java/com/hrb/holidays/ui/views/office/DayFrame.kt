package com.hrb.holidays.ui.views.office

import com.hrb.holidays.ui.modifiers.FrameLine


fun getFrameLinesForPlaceInRow(rowSize: Int, placeInRow: Int): List<FrameLine> {
    val lines = mutableListOf<FrameLine>()

    if (rowSize == 1) lines.add(FrameLine.LEFT)
    // Never have to add TOP
    if (placeInRow != rowSize || rowSize == 1) lines.add(FrameLine.RIGHT)
    if (rowSize != 1) lines.add(FrameLine.BOTTOM)

    return lines
}
