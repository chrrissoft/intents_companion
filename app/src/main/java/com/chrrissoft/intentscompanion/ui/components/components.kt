package com.chrrissoft.intentscompanion.ui.components

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.DataObject
import androidx.compose.material.icons.rounded.RemoveCircle
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.chrrissoft.intentscompanion.Util.getBundleData
import com.chrrissoft.intentscompanion.Util.toUiAction
import com.chrrissoft.intentscompanion.ui.ScreenState.Actions
import com.chrrissoft.intentscompanion.ui.theme.cardColors
import com.chrrissoft.intentscompanion.ui.theme.inputChipColors
import com.chrrissoft.intentscompanion.ui.theme.textFieldColors


@Composable
fun IntentReader(
    title: String,
    intent: Intent?,
    actions: List<String>,
) {
    Container(title = title) {
        if (intent==null) {
            Text(
                text = "Intent is null",
                style = typography.titleMedium.copy(textAlign = TextAlign.Center),
                modifier = Modifier.fillMaxWidth()
            )
            return@Container
        }
        Actions(
            action = "${intent.action}",
            actions = actions,
            onChangeAction = {}
        )
        val data = intent.getBundleData()
        if (data.isEmpty()) {
            Row(Modifier.padding(horizontal = 10.dp)) {
                BundleDataItem("bundle data", "is empty", modifier = Modifier.fillMaxWidth())
            }
        } else {
            LazyRow(Modifier.padding(horizontal = 10.dp)) {
                items(data) {
                    BundleDataItem(it.first, it.second)
                }
            }
        }
    }
}


@Composable
fun Actions(
    action: String,
    actions: List<String>,
    onChangeAction: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        actions.forEachIndexed { index, currentAction ->
            if (index == 1) Spacer(modifier = Modifier.weight(.05f))
            ActionChip(
                action = currentAction.toUiAction(),
                selected = currentAction==action,
                onSelect = { onChangeAction(currentAction) },
                enabled = enabled,
                modifier = Modifier.weight(1f)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ActionChip(
    action: String,
    selected: Boolean,
    enabled: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    InputChip(
        selected = selected,
        enabled = enabled,
        onClick = { onSelect() },
        colors = inputChipColors,
        border = InputChipDefaults.inputChipBorder(borderColor = MaterialTheme.colorScheme.primary),
        label = { Text(action, style = typography.labelMedium) },
        modifier = modifier
    )
}


@Composable
fun BundleData(
    data: Map<String, String>,
    onAdd: (Pair<String, String>) -> Unit,
    onRemove: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val (showInput, changeShowInput) = remember {
        mutableStateOf(false)
    }

    if (showInput) {
        val (key, changeKey) = remember {
            mutableStateOf("")
        }

        val (value, changeValue) = remember {
            mutableStateOf("")
        }

        AlertDialog(
            onDismissRequest = {
                changeShowInput(false)
            },
            confirmButton = {
                Button(
                    enabled = key.isNotEmpty() && value.isNotEmpty(),
                    onClick = {
                        changeKey("")
                        changeValue("")
                        onAdd(Pair(key, value))
                    },
                ) {
                    Text(text = "Add Bundle Data")
                }
            },
            title = {
                Text(text = "Add Bundle Data to App Companion")
            },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.DataObject, contentDescription = null
                )
            },
            text = {
                Column {
                    TextField(
                        value = key,
                        onValueChange = changeKey,
                        colors = textFieldColors,
                        placeholder = { Text(text = "Key") },
                        modifier = Modifier.clip(MaterialTheme.shapes.medium)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        value = value,
                        onValueChange = changeValue,
                        colors = textFieldColors,
                        placeholder = { Text(text = "Value") },
                        modifier = Modifier.clip(MaterialTheme.shapes.medium)
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            iconContentColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.primary,
        )
    }

    ListContainer(
        onAdd = { changeShowInput(true) }, modifier = modifier
    ) {
        if (data.isEmpty()) addElementButtonChip(text = "bundle data") { changeShowInput(true) }

        data.forEach {
            item {
                BundleDataItem(key = it.key, value = it.value) { onRemove(it.key) }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BundleDataItem(
    key: String,
    value: String,
    modifier: Modifier = Modifier,
    onRemove: (() -> Unit)? = null,
) {
    InputChip(
        selected = false,
        onClick = { },
        colors = inputChipColors,
        border = InputChipDefaults.inputChipBorder(borderColor = MaterialTheme.colorScheme.primary),
        label = {
            Text("$key : $value")
        },
        trailingIcon = {
            if (onRemove!=null) {
                Icon(imageVector = Icons.Rounded.RemoveCircle,
                    contentDescription = null,
                    modifier = Modifier.clickable { onRemove() })
            }
        },
        modifier = modifier,
    )
}


@Composable
fun Container(
    modifier: Modifier = Modifier,
    title: String? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier.padding(10.dp),
        colors = cardColors
    ) {
        Spacer(modifier = Modifier.padding(top = 10.dp))
        if (title!=null) {
            Text(
                text = title,
                style = typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Divider(
                modifier = Modifier.padding(10.dp),
                color = MaterialTheme.colorScheme.primary.copy(.7f),
            )
        }
        content()
        Spacer(modifier = Modifier.padding(top = 10.dp))
    }
}

@Composable
private fun ListContainer(
    onAdd: () -> Unit,
    modifier: Modifier = Modifier,
    content: LazyListScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(modifier = Modifier.weight(1f), onClick = { onAdd() }) {
            Icon(
                imageVector = Icons.Rounded.AddCircle, contentDescription = null
            )
        }

        LazyRow(modifier = Modifier.weight(7f)) {
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private fun LazyListScope.addElementButtonChip(
    text: String,
    modifier: Modifier = Modifier,
    onAdd: () -> Unit,
) {
    item {
        InputChip(
            selected = false,
            modifier = modifier,
            onClick = { onAdd() },
            colors = inputChipColors,
            border = InputChipDefaults.inputChipBorder(borderColor = MaterialTheme.colorScheme.primary),
            label = { Text("Tap icon or here to add $text") },
            trailingIcon = {
                Icon(imageVector = Icons.Rounded.AddCircle,
                    contentDescription = null,
                    modifier = Modifier.clickable { onAdd() })
            },
        )
    }
}
