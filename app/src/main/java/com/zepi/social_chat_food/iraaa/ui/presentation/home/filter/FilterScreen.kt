@file:OptIn(ExperimentalLayoutApi::class)

package com.zepi.social_chat_food.iraaa.ui.presentation.home.filter


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.zepi.social_chat_food.R.string as AppText
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.zepi.social_chat_food.iraaa.model.QChatRepo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = null
                            )
                        }
                    },
                    title = {
                        Text(
                            text = stringResource(AppText.filters_title),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    },
                    actions = {
                        /**val resetEnabled = sortState != defaultFilter
                        IconButton(
                        onClick = { /* TODO: Open search */ },
                        enabled = resetEnabled
                        ) {
                        val alpha = if (resetEnabled) {
                        ContentAlpha.high
                        } else {
                        ContentAlpha.disabled
                        }
                        CompositionLocalProvider(LocalContentAlpha provides alpha) {
                        Text(
                        text = "stringResource(id = R.string.reset)",
                        style = MaterialTheme.typography.body2
                        )
                        }
                        }*/
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                )
            },
            modifier = Modifier.padding(vertical = 20.dp),
            containerColor = Color.Gray
        ) { innerPadding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp, vertical = 16.dp),
            ) {
                FilterChipSection(
                    title = stringResource(AppText.gender),
                    filters = QChatRepo.getGender()
                )
            }
        }
    }
}

@Composable
private fun FilterChipSection(title: String, filters: List<String> /**filters: List<Filter>*/) {
    FilterTitle(text = title)
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 16.dp)
            .padding(horizontal = 4.dp)
    ) {
        filters.forEach { filter ->
            Text(
                text = filter,
                modifier = Modifier.padding(end = 4.dp, bottom = 8.dp)
            )
/**        FilterChip(
        filter = filter,
modifier = Modifier.padding(end = 4.dp, bottom = 8.dp)
        )*/
        }
    }
}

@Composable
private fun FilterTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onError,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}







    /**
    var sortState by remember { mutableStateOf(SnackRepo.getSortDefault()) }
    //var maxCalories by remember { mutableFloatStateOf(0f) }
    val defaultFilter = SnackRepo.getSortDefault()


        val priceFilters = remember { SnackRepo.getPriceFilters() }
        val categoryFilters = remember { SnackRepo.getCategoryFilters() }
        val lifeStyleFilters = remember { SnackRepo.getLifeStyleFilters() }

                SortFiltersSection(
                    sortState = sortState,
                    onFilterChange = { filter ->
                        sortState = filter.name
                    }
                )
                FilterChipSection(
                    title = "stringResource(id = R.string.price)",
                    filters = priceFilters
                )
    FilterChipSection(
    title = "stringResource(id = R.string.category)",
    filters = categoryFilters
    )

                MaxCalories(
                    sliderPosition = 1f, //maxCalories,
                    onValueChanged = { newValue ->
                       // maxCalories = newValue
                    }
                )
                FilterChipSection(
                    title = "stringResource(id = R.string.lifestyle)",
                    filters = lifeStyleFilters
                )
            }
        }
    }
}



@Composable
fun SortFiltersSection(sortState: String, onFilterChange: (Filter) -> Unit) {
    FilterTitle(text = "stringResource(id = R.string.sort)")
    Column(Modifier.padding(bottom = 24.dp)) {
        SortFilters(
            sortState = sortState,
            onChanged = onFilterChange
        )
    }
}

@Composable
fun SortFilters(
    sortFilters: List<Filter> = SnackRepo.getSortFilters(),
    sortState: String,
    onChanged: (Filter) -> Unit
) {

    sortFilters.forEach { filter ->
        SortOption(
            text = filter.name,
            icon = filter.icon,
            selected = sortState == filter.name,
            onClickOption = {
                onChanged(filter)
            }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MaxCalories(sliderPosition: Float, onValueChanged: (Float) -> Unit) {
    FlowRow {
        FilterTitle(text = "stringResource(id = R.string.max_calories)")
        Text(
            text = "stringResource(id = R.string.per_serving)",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier.padding(top = 5.dp, start = 10.dp)
        )
    }
    Slider(
        value = sliderPosition,
        onValueChange = { newValue ->
            onValueChanged(newValue)
        },
        valueRange = 0f..300f,
        steps = 5,
        modifier = Modifier
            .fillMaxWidth(),
        colors = SliderDefaults.colors(
            thumbColor = MaterialTheme.colorScheme.onBackground,
            activeTrackColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Composable
fun SortOption(
    text: String,
    icon: ImageVector?,
    onClickOption: () -> Unit,
    selected: Boolean
) {
    Row(
        modifier = Modifier
            .padding(top = 14.dp)
            .selectable(selected) { onClickOption() }
    ) {
        if (icon != null) {
            Icon(imageVector = icon, contentDescription = null)
        }
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .padding(start = 10.dp)
                .weight(1f)
        )
        if (selected) {
            Icon(
                imageVector = Icons.Filled.Done,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}*/
