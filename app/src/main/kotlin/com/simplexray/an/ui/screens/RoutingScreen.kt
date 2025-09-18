@file:OptIn(ExperimentalUuidApi::class)

package com.simplexray.an.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.simplexray.an.R
import com.simplexray.an.viewmodel.RoutesViewModel
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun RoutingScreen(
    routesViewModel: RoutesViewModel,
    listState: LazyListState,
) {
    val routes by routesViewModel.routes.collectAsState()

    val hapticFeedback = LocalHapticFeedback.current
    val reorderableLazyListState = rememberReorderableLazyListState(listState) { from, to ->
        val routeUuid = from.key as? Uuid ?: return@rememberReorderableLazyListState
        routesViewModel.changeUserOrder(routeUuid, to.index)
        hapticFeedback.performHapticFeedback(HapticFeedbackType.SegmentFrequentTick)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (routes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    stringResource(R.string.no_routes),
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight(),
                contentPadding = PaddingValues(bottom = 10.dp, top = 10.dp),
                state = listState
            ) {
                items(routes, key = { it.uuid }) { route ->
                    ReorderableItem(reorderableLazyListState, key = route.uuid) {

                    }
                }
            }
        }
    }
}