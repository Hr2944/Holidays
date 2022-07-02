package com.hrb.holidays.ui.views.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun DrawerTopBar() {
    Box(
        modifier = Modifier
            .padding(0.dp, 6.dp, 0.dp, 32.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Divider(
            modifier = Modifier
                .fillMaxWidth(.4f),
            thickness = 3.dp
        )
    }
}
