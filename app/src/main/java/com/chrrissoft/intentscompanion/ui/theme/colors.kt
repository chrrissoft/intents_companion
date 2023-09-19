package com.chrrissoft.intentscompanion.ui.theme

import androidx.compose.material3.*
import androidx.compose.material3.IconButtonDefaults.filledIconToggleButtonColors
import androidx.compose.material3.InputChipDefaults.inputChipColors
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.material3.NavigationBarItemDefaults.colors as navigationBarItemColors

@OptIn(ExperimentalMaterial3Api::class)
val centerAlignedTopAppBarColors
    @Composable get() = centerAlignedTopAppBarColors(
        containerColor = colorScheme.primaryContainer,
        navigationIconContentColor = colorScheme.primary,
        titleContentColor = colorScheme.primary,
        actionIconContentColor = colorScheme.primary,
    )

val navigationBarItemColors
    @Composable get() = navigationBarItemColors(
        selectedIconColor = colorScheme.onPrimary,
        selectedTextColor = colorScheme.primary,
        indicatorColor = colorScheme.primary,
        unselectedIconColor = colorScheme.primary.copy(.5f),
        unselectedTextColor = colorScheme.primary.copy(.5f),
    )

val navigationDrawerItemColors
    @Composable get() = NavigationDrawerItemDefaults.colors(
        selectedContainerColor = colorScheme.primary,
        unselectedContainerColor = colorScheme.onPrimary,
        selectedIconColor = colorScheme.onPrimary,
        unselectedIconColor = colorScheme.secondary.copy(.5f),
        selectedTextColor = colorScheme.onPrimary,
        unselectedTextColor = colorScheme.secondary.copy(.5f),
    )

@OptIn(ExperimentalMaterial3Api::class)
val inputChipColors
    @Composable get() = inputChipColors(
        containerColor = colorScheme.primaryContainer,
        labelColor = colorScheme.primary,
        leadingIconColor = colorScheme.primary,
        trailingIconColor = colorScheme.primary,
        disabledContainerColor = colorScheme.primaryContainer.copy(.5f),
        disabledLabelColor = colorScheme.secondary.copy(.5f),
        disabledLeadingIconColor = colorScheme.secondary.copy(.5f),
        disabledTrailingIconColor = colorScheme.secondary.copy(.5f),
        selectedContainerColor = colorScheme.primary,
        disabledSelectedContainerColor = colorScheme.errorContainer,
        selectedLabelColor = colorScheme.onPrimary,
        selectedLeadingIconColor = colorScheme.onPrimary,
        selectedTrailingIconColor = colorScheme.onPrimary,
    )

val cardColors
    @Composable get() = CardDefaults.cardColors(
        containerColor = colorScheme.primaryContainer,
    )

val textFieldColors
    @Composable get() = TextFieldDefaults.colors(
        focusedTextColor = colorScheme.primary,
        unfocusedTextColor = colorScheme.primary,
        focusedContainerColor = colorScheme.onPrimary,
        unfocusedContainerColor = colorScheme.onPrimary,
        cursorColor = colorScheme.primary,
        focusedIndicatorColor = Transparent,
        unfocusedIndicatorColor = Transparent,
        focusedPlaceholderColor = colorScheme.primary.copy(.5f),
        unfocusedPlaceholderColor = colorScheme.primary.copy(.5f),
        disabledContainerColor = colorScheme.onPrimary.copy(.5f),
        disabledPlaceholderColor = colorScheme.onPrimary.copy(.5f),
        disabledIndicatorColor = Transparent,
        focusedTrailingIconColor = colorScheme.primary,
        unfocusedTrailingIconColor = colorScheme.primary,
        unfocusedLabelColor = colorScheme.primary,
        focusedLabelColor = colorScheme.primary,
        disabledLabelColor = colorScheme.primary.copy(.5f),
    )

val filledIconToggleButtonColors @Composable get() = filledIconToggleButtonColors(
    containerColor = colorScheme.onPrimary,
    contentColor = colorScheme.primary,
    disabledContainerColor = colorScheme.secondary.copy(.5f),
    disabledContentColor = colorScheme.onSecondary.copy(.5f),
    checkedContainerColor = colorScheme.primary,
    checkedContentColor = colorScheme.onPrimary,
)
