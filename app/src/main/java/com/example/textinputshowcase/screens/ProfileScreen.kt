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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.textinputshowcase.components.StandardButton
import com.example.textinputshowcase.components.StandardTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var occupation by remember { mutableStateOf("") }
    var interests by remember { mutableStateOf("") }
    
    // Validation states
    var usernameError by remember { mutableStateOf<String?>(null) }
    
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    fun validateUsername(username: String): Boolean {
        return username.matches(Regex("^[a-zA-Z0-9_]{3,20}$"))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
        ) {
            // Basic Info Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Basic Information",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    StandardTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = "Full Name",
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null)
                        }
                    )

                    StandardTextField(
                        value = username,
                        onValueChange = { 
                            username = it
                            usernameError = if (!validateUsername(it) && it.isNotEmpty()) 
                                "Username must be 3-20 characters (letters, numbers, underscore)" 
                            else null
                        },
                        label = "Username",
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        isError = usernameError != null,
                        supportingText = usernameError,
                        leadingIcon = {
                            Icon(Icons.Default.AlternateEmail, contentDescription = null)
                        }
                    )

                    StandardTextField(
                        value = location,
                        onValueChange = { location = it },
                        label = "Location",
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        leadingIcon = {
                            Icon(Icons.Default.LocationOn, contentDescription = null)
                        }
                    )

                    StandardTextField(
                        value = occupation,
                        onValueChange = { occupation = it },
                        label = "Occupation",
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        leadingIcon = {
                            Icon(Icons.Default.Work, contentDescription = null)
                        }
                    )
                }
            }

            // Additional Info Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Additional Information",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    StandardTextField(
                        value = bio,
                        onValueChange = { bio = it },
                        label = "Bio",
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        maxLines = 4,
                        supportingText = "${bio.length}/160 characters",
                        isError = bio.length > 160,
                        leadingIcon = {
                            Icon(Icons.Default.Description, contentDescription = null)
                        }
                    )

                    StandardTextField(
                        value = interests,
                        onValueChange = { interests = it },
                        label = "Interests (comma separated)",
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        ),
                        leadingIcon = {
                            Icon(Icons.Default.Favorite, contentDescription = null)
                        }
                    )
                }
            }

            StandardButton(
                onClick = { /* Handle profile creation */ },
                text = "Create Profile",
                enabled = fullName.isNotBlank() && 
                    username.isNotBlank() && 
                    usernameError == null && 
                    bio.length <= 160,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
} 