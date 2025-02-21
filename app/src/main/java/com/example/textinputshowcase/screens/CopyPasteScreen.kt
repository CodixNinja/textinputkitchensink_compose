package com.example.textinputshowcase.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.textinputshowcase.components.StandardTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CopyPasteScreen(navController: NavController) {
    val clipboardManager = LocalClipboardManager.current
    var sourceText by remember { mutableStateOf("") }
    var plainTextDest by remember { mutableStateOf("") }
    var numberDest by remember { mutableStateOf("") }
    var multilineDest by remember { mutableStateOf("") }
    
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Copy & Paste") },
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
            // Source section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Source Text",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    OutlinedTextField(
                        value = sourceText,
                        onValueChange = { sourceText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        placeholder = { Text("Enter or paste text here") },
                        maxLines = 5
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = {
                                sourceText = "Sample text: 123-456-7890\nEmail: test@example.com\nAmount: $99.99"
                            }
                        ) {
                            Text("Load Sample")
                        }
                        
                        IconButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(sourceText))
                            }
                        ) {
                            Icon(
                                Icons.Default.ContentCopy,
                                contentDescription = "Copy to clipboard"
                            )
                        }
                    }
                }
            }

            // Destination section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Paste Destinations",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    StandardTextField(
                        value = plainTextDest,
                        onValueChange = { plainTextDest = it },
                        label = "Plain Text Field",
                        supportingText = "Try pasting any text here"
                    )
                    
                    StandardTextField(
                        value = numberDest,
                        onValueChange = { numberDest = it },
                        label = "Numbers Only",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        supportingText = "Try pasting numbers and non-numbers here"
                    )
                    
                    StandardTextField(
                        value = multilineDest,
                        onValueChange = { multilineDest = it },
                        label = "Multiline Field",
                        maxLines = 3,
                        supportingText = "Try pasting multi-line text here"
                    )
                }
            }

            // Instructions
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Test Instructions",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    Text("1. Enter text in the source field or use 'Load Sample'")
                    Text("2. Try copying text using:")
                    Text("   • Long press to select text")
                    Text("   • Use the copy button")
                    Text("3. Attempt to paste into different field types")
                    Text("4. Test copying from external apps")
                }
            }
        }
    }
} 