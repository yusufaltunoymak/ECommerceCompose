package com.hoy.ecommercecompose.ui.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.hoy.ecommercecompose.data.source.local.payment.City
import com.hoy.ecommercecompose.data.source.local.payment.getCitiesAndDistricts
import com.hoy.ecommercecompose.ui.components.CustomButton
import com.hoy.ecommercecompose.ui.theme.LocalColors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun PaymentScreen(
    uiState: PaymentContract.UiState,
    onAction: (PaymentContract.UiAction) -> Unit,
    uiEffect: Flow<PaymentContract.UiEffect>,
    onBackPress: () -> Unit
) {
    val context = LocalContext.current
    var showConfirmationDialog by remember { mutableStateOf(false) }
    var confirmationMessage by remember { mutableStateOf("") }
    var alertDialogState by remember { mutableStateOf(false) }
    var alertDialogMessage by remember { mutableStateOf("") }


    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(uiEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEffect.collect { effect ->
                when (effect) {
                    is PaymentContract.UiEffect.ShowAlertDialog -> {
                        alertDialogState = true
                        alertDialogMessage = effect.message
                    }

                    is PaymentContract.UiEffect.BackScreen -> onBackPress()
                    is PaymentContract.UiEffect.ShowOrderConfirmation -> {
                        confirmationMessage = context.getString(effect.message)
                        showConfirmationDialog = true
                    }
                }
            }
        }
    }

    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            title = { Text(text = "Order Confirmed") },
            text = { Text(text = confirmationMessage) },
            confirmButton = {
                TextButton(onClick = {
                    showConfirmationDialog = false
                    onBackPress()
                }) {
                    Text("Okay")
                }
            }
        )
    }

    if (alertDialogState) {
        AlertDialog(
            onDismissRequest = { alertDialogState = false },
            title = { Text(text = "Validation Errors") },
            text = { Text(text = alertDialogMessage) },
            confirmButton = {
                TextButton(onClick = {
                    onAction(PaymentContract.UiAction.ClearError)
                    alertDialogState = false
                }) {
                    Text("Okay")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = LocalColors.current.primary,
                modifier = Modifier
                    .clickable { onBackPress() }
                    .size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Payment",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        // Bank Card Component
        BankCard(
            cardHolder = uiState.cardHolderName,
            cardNumber = uiState.cardNumber,
            expiryDate = uiState.expiryDate,
            cvv = uiState.cvv,
            cardBrand = "WISA"
        )

        Spacer(modifier = Modifier.height(16.dp))

        CardHolderInput(
            cardHolder = uiState.cardHolderName,
            onCardHolderChange = { onAction(PaymentContract.UiAction.ChangeCardHolderName(it)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CardNumberInput(
            cardNumber = uiState.cardNumber,
            onCardNumberChange = { onAction(PaymentContract.UiAction.ChangeCardNumber(it)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        ExpiryDateAndCvvInput(
            expiryDate = "${uiState.selectedMonth}/${uiState.selectedYear}",
            onExpiryDateChange = { month, year ->
                onAction(PaymentContract.UiAction.ChangeExpiryDate(month, year))
            },
            cvv = uiState.cvv,
            onCvvChange = { onAction(PaymentContract.UiAction.ChangeCvv(it)) },
            isMonthDropdownExpanded = uiState.isMonthDropdownExpanded,
            isYearDropdownExpanded = uiState.isYearDropdownExpanded,
            onToggleMonthDropdown = { onAction(PaymentContract.UiAction.ToggleMonthDropdown) },
            onToggleYearDropdown = { onAction(PaymentContract.UiAction.ToggleYearDropdown) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        AddressInput(
            uiState = uiState,
            onAction = onAction
        )

        CustomButton(
            text = " Order Now ",
            onClick = { onAction(PaymentContract.UiAction.OrderClick) })
    }
}


@Composable
fun BankCard(
    cardHolder: String,
    cardNumber: String,
    expiryDate: String,
    cvv: String,
    cardBrand: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF1E88E5))
            .height(220.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "CARD HOLDER",
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = cardHolder.uppercase(), // Cardholder name in uppercase
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = cardNumber.chunked(4).joinToString(" "),
                    fontSize = 24.sp,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }

            // Expiry Date and CVV
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "EXPIRES",
                        color = Color.White.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = expiryDate,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Column {
                    Text(
                        text = "CVV",
                        color = Color.White.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = cvv,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Text(
                text = cardBrand,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
fun CardHolderInput(cardHolder: String, onCardHolderChange: (String) -> Unit) {
    OutlinedTextField(
        value = cardHolder.uppercase(), // Convert input to uppercase
        onValueChange = { onCardHolderChange(it) },
        label = { Text(text = "Card Holder Name") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CardNumberInput(cardNumber: String, onCardNumberChange: (String) -> Unit) {
    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                cardNumber.chunked(4).joinToString(" ")
            )
        )
    }

    OutlinedTextField(
        value = textFieldValue,
        onValueChange = { newValue ->
            val rawInput = newValue.text.replace(" ", "")
            if (rawInput.length <= 16 && rawInput.all { it.isDigit() }) {
                val formattedCardNumber = rawInput.chunked(4).joinToString(" ")
                val spaceDifference = formattedCardNumber.length - rawInput.length
                val newCursorPosition = newValue.selection.end + spaceDifference

                textFieldValue = TextFieldValue(
                    text = formattedCardNumber,
                    selection = TextRange(newCursorPosition.coerceAtMost(formattedCardNumber.length))
                )
                onCardNumberChange(rawInput)
            }
        },
        label = { Text(text = "Card Number") },
        visualTransformation = VisualTransformation.None,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun ExpiryDateAndCvvInput(
    expiryDate: String,
    onExpiryDateChange: (String, String) -> Unit,
    cvv: String,
    onCvvChange: (String) -> Unit,
    isMonthDropdownExpanded: Boolean,
    isYearDropdownExpanded: Boolean,
    onToggleMonthDropdown: () -> Unit,
    onToggleYearDropdown: () -> Unit
) {
    val months = (1..12).map { it.toString().padStart(2, '0') }
    val years = (2024..2030).map { it.toString() }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Month Selector
        Box(modifier = Modifier.weight(1f)) {
            OutlinedTextField(
                value = expiryDate.split("/")[0],
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "MM") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.clickable { onToggleMonthDropdown() }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            DropdownMenu(
                expanded = isMonthDropdownExpanded,
                onDismissRequest = { onToggleMonthDropdown() },
                modifier = Modifier.fillMaxWidth()
            ) {
                months.forEach { month ->
                    DropdownMenuItem(
                        text = { Text(text = month) },
                        onClick = {
                            onExpiryDateChange(month, expiryDate.split("/")[1])
                            onToggleMonthDropdown()
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Year Selector
        Box(modifier = Modifier.weight(1f)) {
            OutlinedTextField(
                value = expiryDate.split("/")[1],
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "YY") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.clickable { onToggleYearDropdown() }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            DropdownMenu(
                expanded = isYearDropdownExpanded,
                onDismissRequest = { onToggleYearDropdown() },
                modifier = Modifier.fillMaxWidth()
            ) {
                years.forEach { year ->
                    DropdownMenuItem(
                        text = { Text(text = year) },
                        onClick = {
                            onExpiryDateChange(expiryDate.split("/")[0], year)
                            onToggleYearDropdown()
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // CVV Input
        OutlinedTextField(
            value = cvv,
            onValueChange = {
                if (it.length <= 3 && it.all { char -> char.isDigit() }) {
                    onCvvChange(it)
                }
            },
            label = { Text(text = "CVV") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}


@Composable
fun AddressInput(
    uiState: PaymentContract.UiState,
    onAction: (PaymentContract.UiAction) -> Unit
) {
    val cities = listOf("Ankara", "İstanbul", "İzmir")
    val districts = when (uiState.selectedCity) {
        "Ankara" -> listOf("Çankaya", "Keçiören", "Mamak")
        "İstanbul" -> listOf("Kadıköy", "Beşiktaş", "Şişli")
        "İzmir" -> listOf("Konak", "Karşıyaka", "Bornova")
        else -> emptyList()
    }


    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Address",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = uiState.selectedCity,
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "City") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.clickable { onAction(PaymentContract.UiAction.ToggleCityDropdown) }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            DropdownMenu(
                expanded = uiState.isCityDropdownExpanded,
                onDismissRequest = { onAction(PaymentContract.UiAction.ToggleCityDropdown) },
                modifier = Modifier.fillMaxWidth()
            ) {
                cities.forEach { city ->
                    DropdownMenuItem(
                        text = { Text(text = city) },
                        onClick = {
                            onAction(PaymentContract.UiAction.ChangeCity(city))
                            onAction(PaymentContract.UiAction.ChangeDistrict(""))
                            onAction(PaymentContract.UiAction.ToggleCityDropdown)
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // District Selector
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = uiState.selectedDistrict,
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "District") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.clickable { onAction(PaymentContract.UiAction.ToggleDistrictDropdown) }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            DropdownMenu(
                expanded = uiState.isDistrictDropdownExpanded,
                onDismissRequest = { onAction(PaymentContract.UiAction.ToggleDistrictDropdown) },
                modifier = Modifier.fillMaxWidth()
            ) {
                districts.forEach { district ->
                    DropdownMenuItem(
                        text = { Text(text = district) },
                        onClick = {
                            onAction(PaymentContract.UiAction.ChangeDistrict(district))
                            onAction(PaymentContract.UiAction.ToggleDistrictDropdown)
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.addressText,
            onValueChange = { onAction(PaymentContract.UiAction.ChangeAddressText(it)) },
            label = { Text(text = "Full Address") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewPaymentScreen() {
    val uiState = PaymentContract.UiState(
        cardHolderName = "JOHN DOE",
        cardNumber = "1234567812345678",
        expiryDate = "12/2025",
        cvv = "123",
        selectedMonth = "12",
        selectedYear = "2025",
        selectedCity = "Ankara",
        selectedDistrict = "Çankaya",
        addressText = "1234 Elm Street"
    )

    PaymentScreen(uiState = uiState, onAction = {}, onBackPress = {}, uiEffect = flow {})
}

