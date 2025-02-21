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
fun SettingsScreen(navController: NavController) {
    var displayName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var website by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    
    // Validation states
    var emailError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var websiteError by remember { mutableStateOf<String?>(null) }
    
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    fun validateEmail(email: String): Boolean {
        return email.isEmpty() || email.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"))
    }

    fun formatPhoneNumber(phone: String): String {
        val digits = phone.filter { it.isDigit() }
        return when {
            digits.length <= 3 -> digits
            digits.length <= 6 -> "(${digits.take(3)}) ${digits.substring(3)}"
            else -> "(${digits.take(3)}) ${digits.substring(3,6)}-${digits.substring(6).take(4)}"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
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
                        value = displayName,
                        onValueChange = { displayName = it },
                        label = "Display Name",
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
                        value = email,
                        onValueChange = { 
                            email = it
                            emailError = if (!validateEmail(it)) "Invalid email format" else null
                        },
                        label = "Email Address",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        isError = emailError != null,
                        supportingText = emailError,
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = null)
                        }
                    )

                    StandardTextField(
                        value = phone,
                        onValueChange = { input ->
                            val formatted = formatPhoneNumber(input)
                            if (formatted.length <= 14) { // (123) 456-7890
                                phone = formatted
                            }
                            phoneError = if (formatted.length < 14 && formatted.isNotEmpty()) 
                                "Please enter complete phone number" else null
                        },
                        label = "Phone Number",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        isError = phoneError != null,
                        supportingText = phoneError,
                        leadingIcon = {
                            Icon(Icons.Default.Phone, contentDescription = null)
                        }
                    )

                    StandardTextField(
                        value = website,
                        onValueChange = { 
                            website = it
                            websiteError = if (it.isNotEmpty() && !it.startsWith("http")) 
                                "Website should start with http:// or https://" else null
                        },
                        label = "Website",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Uri,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        isError = websiteError != null,
                        supportingText = websiteError,
                        leadingIcon = {
                            Icon(Icons.Default.Language, contentDescription = null)
                        }
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "About You",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    StandardTextField(
                        value = bio,
                        onValueChange = { bio = it },
                        label = "Bio",
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        ),
                        maxLines = 5,
                        supportingText = "${bio.length}/200 characters",
                        isError = bio.length > 200,
                        leadingIcon = {
                            Icon(Icons.Default.Description, contentDescription = null)
                        }
                    )
                }
            }

            StandardButton(
                onClick = { /* Handle profile update */ },
                text = "Save Changes",
                enabled = displayName.isNotBlank() && 
                    emailError == null && 
                    phoneError == null && 
                    websiteError == null &&
                    bio.length <= 200,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
} 