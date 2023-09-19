package com.chrrissoft.intentscompanion.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.unit.dp
import com.chrrissoft.intentscompanion.MainActivity
import com.chrrissoft.intentscompanion.ui.ScreenEvent.OnChangePage
import com.chrrissoft.intentscompanion.ui.ScreenState.Page
import com.chrrissoft.intentscompanion.ui.components.*
import com.chrrissoft.intentscompanion.ui.theme.centerAlignedTopAppBarColors
import com.chrrissoft.intentscompanion.ui.theme.navigationBarItemColors
import java.lang.Exception

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(
    state: ScreenState,
    onEvent: (ScreenEvent) -> Unit,
) {
    MainActivity.setBarsColors()

    var showCancelError by remember {
        mutableStateOf(false)
    }

    if (showCancelError) {
        AlertDialog(
            onDismissRequest = { /*TODO*/ },
            text = {},
            title = {
                Text(text = "Pending was be canceled")
            },
            confirmButton = {
                Button(
                    onClick = { showCancelError = false },
                    colors = buttonColors(
                        contentColor = colorScheme.onErrorContainer,
                        containerColor = colorScheme.error
                    )
                ) {
                    Text(text = "Ok")
                }
            },
            containerColor = colorScheme.errorContainer,
            titleContentColor = colorScheme.onErrorContainer,
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = centerAlignedTopAppBarColors,
                title = { Text(text = "Intent Companion App", fontWeight = Medium) }
            )
        },
        floatingActionButton = {
            if (state.page==Page.REPLAY || !state.roundTripState.enabled) return@Scaffold
            ExtendedFloatingActionButton(
                onClick = {
                    try {
                        state.roundTripState.pendingIntent?.send()
                    } catch (e: Exception) {
                        showCancelError = true
                    }
                },
            ) {
                Text(text = "Call Send")
                Spacer(modifier = Modifier.width(5.dp))
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = colorScheme.primaryContainer,
                contentColor = colorScheme.primary
            ) {
                Page.pages.forEach {
                    NavigationBarItem(
                        selected = state.page==it,
                        onClick = { onEvent(OnChangePage(it)) },
                        label = { Text(text = it.label) },
                        icon = { Icon(imageVector = it.icon, contentDescription = null) },
                        colors = navigationBarItemColors
                    )
                }
            }
        },
        content = {
            PageContainer(paddingValues = it) {
                when (state.page) {
                    Page.REPLAY -> {
                        ReplayPage(state = state.replayState)
                    }
                    Page.ROUND_TRIP -> {
                        RoundTripPage(state = state.roundTripState)
                    }
                }
            }
        },
    )
}
