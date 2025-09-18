package com.simplexray.an.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EnableButton(
    serviceEnabled: Boolean,
    stateUpdating: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .widthIn(max = 192.dp)
            .fillMaxWidth()
            .aspectRatio(1f),
        color = MaterialTheme.colorScheme.surfaceContainer,
        shadowElevation = 4.dp,
        shape = CircleShape,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = onClick),
        ) {

        }
    }
}