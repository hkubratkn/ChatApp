package com.kapirti.pomodorotechnique_timemanagementmethod.past.soci.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Settings(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {}/**
    val viewModel: SettingsViewModel = hiltViewModel()
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        item {
            Box(modifier = Modifier.padding(32.dp)) {
                Button(
                    onClick = { viewModel.clearMessages() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                ) {
                    Text(text = stringResource(R.string.clear_message_history))
                }
            }
        }
    }
}
*/
