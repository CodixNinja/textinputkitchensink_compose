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

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.textinputshowcase.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Text Input Showcase") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(getScenarios()) { scenario ->
                ElevatedCard(
                    onClick = { navController.navigate(scenario.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    ListItem(
                        headlineContent = { Text(scenario.title) },
                        supportingContent = { Text(scenario.description) }
                    )
                }
            }
        }
    }
}

private data class Scenario(
    val title: String,
    val description: String,
    val route: String
)

private fun getScenarios() = listOf(
    Scenario("Login", "Email and password input fields", Screen.Login.route),
    Scenario("Purchase", "Payment and shipping information", Screen.Purchase.route),
    Scenario("Profile Creation", "Multi-field data entry form", Screen.Profile.route),
    Scenario("Chat", "Message input field", Screen.Chat.route),
    Scenario("Search", "Search input field", Screen.Search.route),
    Scenario("Review", "Review and rating input", Screen.Review.route),
    Scenario("Settings", "Profile editing fields", Screen.Settings.route),
    Scenario("Calendar Event", "Event creation fields", Screen.Calendar.route),
    Scenario("Social Media Post", "Post creation field", Screen.SocialPost.route),
    Scenario("Copy & Paste", "Copy and paste functionality", Screen.CopyPaste.route)
) 