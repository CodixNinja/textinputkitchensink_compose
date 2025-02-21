package com.example.textinputshowcase.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.textinputshowcase.utils.CreditCardUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchaseScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var streetAddress by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var zipCode by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Purchase") },
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
            Text(
                text = "Shipping Information",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            StandardTextField(
                value = name,
                onValueChange = { name = it },
                label = "Full Name",
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                modifier = Modifier.autofillHints(listOf("name", "personName"))
            )

            StandardTextField(
                value = streetAddress,
                onValueChange = { streetAddress = it },
                label = "Street Address",
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                modifier = Modifier.autofillHints(listOf("streetAddress", "postalAddress"))
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StandardTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = "City",
                    modifier = Modifier
                        .weight(1f)
                        .autofillHints(listOf("city", "addressCity")),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Right) }
                    )
                )

                StandardTextField(
                    value = state,
                    onValueChange = { state = it.take(2) },
                    label = "State",
                    modifier = Modifier
                        .width(96.dp)
                        .autofillHints(listOf("state", "addressState")),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Characters,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Right) }
                    )
                )

                StandardTextField(
                    value = zipCode,
                    onValueChange = { zipCode = it.take(5) },
                    label = "ZIP",
                    modifier = Modifier
                        .width(96.dp)
                        .autofillHints(listOf("zipCode", "postalCode")),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            Text(
                text = "Payment Information",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            StandardTextField(
                value = cardNumber,
                onValueChange = { input ->
                    if (input.filter { it.isDigit() }.length <= 16) {
                        cardNumber = CreditCardUtils.formatCreditCardNumber(input)
                    }
                },
                label = "Card Number",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                modifier = Modifier.autofillHints(listOf("creditCardNumber"))
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StandardTextField(
                    value = expiryDate,
                    onValueChange = { input ->
                        if (input.filter { it.isDigit() }.length <= 4) {
                            expiryDate = CreditCardUtils.formatExpiryDate(input)
                        }
                    },
                    label = "MM/YY",
                    modifier = Modifier
                        .weight(1f)
                        .autofillHints(listOf("creditCardExpirationDate")),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Right) }
                    ),
                    supportingText = if (expiryDate.length == 5 && !CreditCardUtils.isValidExpiryDate(expiryDate)) 
                        "Invalid expiry date" else null,
                    isError = expiryDate.length == 5 && !CreditCardUtils.isValidExpiryDate(expiryDate)
                )

                StandardTextField(
                    value = cvv,
                    onValueChange = { cvv = it.take(4) },
                    label = "CVV",
                    modifier = Modifier
                        .width(96.dp)
                        .autofillHints(listOf("creditCardSecurityCode")),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    )
                )
            }

            StandardButton(
                onClick = { /* Handle purchase */ },
                text = "Complete Purchase",
                enabled = name.isNotBlank() && 
                    streetAddress.isNotBlank() && 
                    city.isNotBlank() && 
                    state.length == 2 && 
                    zipCode.length == 5 &&
                    cardNumber.filter { it.isDigit() }.length == 16 &&
                    expiryDate.length == 5 && 
                    CreditCardUtils.isValidExpiryDate(expiryDate) &&
                    cvv.length >= 3,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
    }
} 