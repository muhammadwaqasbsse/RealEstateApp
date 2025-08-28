package com.android.immobilien.presentation.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.immobilien.R
import com.android.immobilien.domain.model.Property
import com.android.immobilien.presentation.ui.components.PropertyItem
import com.android.immobilien.presentation.viewmodel.common.Effect
import com.android.immobilien.presentation.viewmodel.property.PropertyListEvent
import com.android.immobilien.presentation.viewmodel.property.PropertyViewModel

@Suppress("ktlint:standard:function-naming")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyScreen(
    viewModel: PropertyViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.onEvent(PropertyListEvent.LoadingPropertyList)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = context.getString(R.string.app_name),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                },
                modifier = Modifier.statusBarsPadding(),
            )
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
        ) {
            SearchBar(
                searchQuery = searchQuery,
                onQueryChange = { searchQuery = it },
            )

            when {
                state.isLoading -> LoadingState()
                state.properties.isEmpty() ->
                    StateMessageView(
                        context = context,
                        message = context.getString(R.string.no_property_found),
                        onRetry = { viewModel.onEvent(PropertyListEvent.LoadingPropertyList) },
                    )
                state.error != null ->
                    StateMessageView(
                        context = context,
                        message = "${context.getString(R.string.error)} ${state.error}",
                        onRetry = { viewModel.onEvent(PropertyListEvent.LoadingPropertyList) },
                    )
                else -> {
                    val filteredProperties =
                        if (searchQuery.isBlank()) {
                            state.properties
                        } else {
                            state.properties.filter { property ->
                                property.city.contains(searchQuery, ignoreCase = true) ||
                                    property.professional.contains(searchQuery, ignoreCase = true)
                            }
                        }
                    PropertyList(
                        properties = filteredProperties,
                        onItemClick = onItemClick,
                    )
                }
            }

            LaunchedEffect(Unit) {
                viewModel.effect.collect { effect ->
                    when (effect) {
                        is Effect.ShowToast -> {
                            Toast
                                .makeText(
                                    context,
                                    effect.message,
                                    Toast.LENGTH_SHORT,
                                ).show()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
) {
    val context = LocalContext.current
    TextField(
        value = searchQuery,
        onValueChange = onQueryChange,
        modifier =
            Modifier
                .testTag("search_field")
                .fillMaxWidth()
                .padding(12.dp),
        placeholder = {
            Text(
                text = context.getString(R.string.search_properties),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        colors =
            TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedLeadingIconColor = Color.Transparent,
            ),
        shape = MaterialTheme.shapes.medium,
        textStyle = MaterialTheme.typography.bodyMedium,
        singleLine = true,
    )
}

@Composable
fun PropertyList(
    properties: List<Property>,
    onItemClick: (Int) -> Unit,
) {
    LazyColumn(
        state = rememberLazyListState(),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(properties) { property ->
            PropertyItem(property) { onItemClick(property.id) }
        }
    }
}

@Composable
fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.testTag("loading_indicator"),
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
fun StateMessageView(
    context: Context,
    message: String,
    onRetry: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier.testTag("message_text"),
                text = message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onRetry,
                modifier =
                    Modifier
                        .testTag("retry_button")
                        .width(120.dp)
                        .height(48.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                shape = RoundedCornerShape(12.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = context.getString(R.string.retry),
                    modifier = Modifier.size(20.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = context.getString(R.string.retry))
            }
        }
    }
}
