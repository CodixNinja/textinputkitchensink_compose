package com.example.textinputshowcase.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocialPostScreen(navController: NavController) {
    var postContent by remember { mutableStateOf(TextFieldValue()) }
    var showSuggestions by remember { mutableStateOf(false) }
    var currentWord by remember { mutableStateOf("") }
    var isHashtag by remember { mutableStateOf(false) }
    var isMention by remember { mutableStateOf(false) }
    
    val maxCharacters = 280

    // Demo suggestions
    val hashtagSuggestions = remember {
        listOf(
            "TextInput", "Compose", "Android", "iOS", "Mobile",
            "Development", "UX", "Design", "Programming"
        )
    }

    val mentionSuggestions = remember {
        listOf(
            "alice_dev", "bob_designer", "charlie_pm",
            "diana_engineer", "evan_mobile", "fiona_ux"
        )
    }

    fun updateCurrentWord(text: String, position: Int) {
        val words = text.substring(0, position).split(' ')
        currentWord = words.lastOrNull()?.trim() ?: ""
        isHashtag = currentWord.startsWith("#")
        isMention = currentWord.startsWith("@")
        showSuggestions = (isHashtag || isMention) && currentWord.length > 1
    }

    fun getHighlightedText(text: String): AnnotatedString {
        return buildAnnotatedString {
            val words = text.split(' ')
            words.forEachIndexed { index, word ->
                if (index > 0) append(" ")
                when {
                    word.startsWith("#") -> {
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append(word)
                        }
                    }
                    word.startsWith("@") -> {
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
                            append(word)
                        }
                    }
                    else -> append(word)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Post") },
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
        ) {
            Box(modifier = Modifier.weight(1f)) {
                Column {
                    OutlinedTextField(
                        value = postContent,
                        onValueChange = { newValue ->
                            if (newValue.text.length <= maxCharacters) {
                                postContent = newValue
                                updateCurrentWord(newValue.text, newValue.selection.start)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        placeholder = { Text("What's on your mind?") },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Done
                        ),
                        visualTransformation = { text ->
                            AnnotatedString(text.text, getHighlightedText(text.text).spanStyles)
                        },
                        maxLines = 8
                    )

                    // Suggestions
                    AnimatedVisibility(
                        visible = showSuggestions,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            val suggestions = if (isHashtag) hashtagSuggestions else mentionSuggestions
                            val prefix = if (isHashtag) "#" else "@"
                            
                            items(
                                suggestions.filter { 
                                    it.startsWith(currentWord.substring(1), ignoreCase = true) 
                                }
                            ) { suggestion ->
                                SuggestionItem(
                                    text = prefix + suggestion,
                                    onClick = {
                                        val beforeWord = postContent.text.substring(0, 
                                            postContent.text.lastIndexOf(currentWord))
                                        val afterWord = postContent.text.substring(
                                            postContent.text.lastIndexOf(currentWord) + currentWord.length)
                                        postContent = TextFieldValue(
                                            text = beforeWord + prefix + suggestion + afterWord,
                                            selection = (beforeWord + prefix + suggestion).length
                                        )
                                        showSuggestions = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Bottom Bar
            Surface(
                tonalElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        IconButton(onClick = { 
                            postContent = TextFieldValue(
                                text = postContent.text + "#",
                                selection = postContent.text.length + 1
                            )
                        }) {
                            Icon(Icons.Default.Tag, contentDescription = "Add hashtag")
                        }
                        
                        IconButton(onClick = { 
                            postContent = TextFieldValue(
                                text = postContent.text + "@",
                                selection = postContent.text.length + 1
                            )
                        }) {
                            Icon(Icons.Default.Person, contentDescription = "Mention someone")
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "${postContent.text.length}/$maxCharacters",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (postContent.text.length > maxCharacters * 0.9) 
                                MaterialTheme.colorScheme.error 
                            else 
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Button(
                            onClick = { /* Handle post creation */ },
                            enabled = postContent.text.isNotBlank() && 
                                postContent.text.length <= maxCharacters
                        ) {
                            Text("Post")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SuggestionItem(
    text: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(16.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
} 