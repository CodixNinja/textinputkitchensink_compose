/*
 * Copyright (c) 2025, Seth Ladd
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 */

package com.example.textinputshowcase.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class SearchSuggestion(
    val text: String,
    val type: SuggestionType,
    val icon: ImageVector
)

enum class SuggestionType {
    RECENT, POPULAR, RESULT
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    // Demo data
    val recentSearches = remember {
        listOf(
            SearchSuggestion("Text input patterns", SuggestionType.RECENT, Icons.Default.History),
            SearchSuggestion("iOS keyboard types", SuggestionType.RECENT, Icons.Default.History),
            SearchSuggestion("Material 3 text fields", SuggestionType.RECENT, Icons.Default.History)
        )
    }

    val popularSearches = remember {
        listOf(
            SearchSuggestion("Form validation", SuggestionType.POPULAR, Icons.Default.TrendingUp),
            SearchSuggestion("Password fields", SuggestionType.POPULAR, Icons.Default.TrendingUp),
            SearchSuggestion("Autofill support", SuggestionType.POPULAR, Icons.Default.TrendingUp)
        )
    }

    val allItems = remember {
        listOf(
            "Text Input Best Practices",
            "iOS Keyboard Handling",
            "Android Text Input",
            "Form Design Patterns",
            "Password Field Security",
            "Search Bar Implementation",
            "Chat Input Features",
            "Text Field Validation",
            "Autocomplete Patterns",
            "Copy and Paste Handling"
        )
    }

    val filteredResults = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            emptyList()
        } else {
            allItems
                .filter { it.contains(searchQuery, ignoreCase = true) }
                .map { SearchSuggestion(it, SuggestionType.RESULT, Icons.Default.Search) }
        }
    }

    Scaffold(
        topBar = {
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { isSearchActive = false },
                active = isSearchActive,
                onActiveChange = { isSearchActive = it },
                placeholder = { Text("Search text input examples") },
                leadingIcon = {
                    if (isSearchActive) {
                        IconButton(onClick = { isSearchActive = false }) {
                            Icon(Icons.Default.ArrowBack, "Back")
                        }
                    } else {
                        Icon(Icons.Default.Search, "Search")
                    }
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, "Clear")
                        }
                    }
                },
                modifier = Modifier.focusRequester(focusRequester)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    if (searchQuery.isBlank()) {
                        if (recentSearches.isNotEmpty()) {
                            item {
                                ListSubheader("Recent Searches")
                            }
                            items(recentSearches) { suggestion ->
                                SuggestionItem(suggestion) {
                                    searchQuery = suggestion.text
                                }
                            }
                        }

                        if (popularSearches.isNotEmpty()) {
                            item {
                                ListSubheader("Popular Searches")
                            }
                            items(popularSearches) { suggestion ->
                                SuggestionItem(suggestion) {
                                    searchQuery = suggestion.text
                                }
                            }
                        }
                    } else {
                        items(filteredResults) { suggestion ->
                            SuggestionItem(suggestion) {
                                searchQuery = suggestion.text
                                isSearchActive = false
                            }
                        }

                        if (filteredResults.isEmpty()) {
                            item {
                                NoResults()
                            }
                        }
                    }
                }
            }
        }
    ) { padding ->
        if (!isSearchActive) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Tap the search bar to start",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }

    // Auto-focus the search bar when screen is shown
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
private fun ListSubheader(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun SuggestionItem(
    suggestion: SearchSuggestion,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = {
            Text(
                suggestion.text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        leadingContent = {
            Icon(
                suggestion.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        modifier = Modifier.clickable(onClick = onClick)
    )
}

@Composable
private fun NoResults() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "No results found",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
} 