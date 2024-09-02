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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.hoy.ecommercecompose.common.collectWithLifecycle
import com.hoy.ecommercecompose.ui.components.CustomButton
import com.hoy.ecommercecompose.ui.theme.ECTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val ONE = 1
private const val THREE = 3
private const val FOUR = 4
private const val TWELVE = 12
private const val SIXTEEN = 16
private const val START_YEAR = 2024
private const val END_YEAR = 2030

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

    uiEffect.collectWithLifecycle { effect ->
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
            .padding(ECTheme.dimensions.sixteen)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = ECTheme.dimensions.four),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = ECTheme.colors.primary,
                modifier = Modifier
                    .clickable { onBackPress() }
                    .size(ECTheme.dimensions.twentyFour)
            )

            Spacer(modifier = Modifier.width(ECTheme.dimensions.sixteen))

            Text(
                text = "Payment",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        BankCard(
            uiState = uiState
        )

        Spacer(modifier = Modifier.height(ECTheme.dimensions.sixteen))

        CardHolderInput(
            uiState = uiState,
            onAction = onAction
        )

        Spacer(modifier = Modifier.height(ECTheme.dimensions.sixteen))

        CardNumberInput(
            uiState = uiState,
            onAction = onAction
        )

        Spacer(modifier = Modifier.height(ECTheme.dimensions.sixteen))

        ExpiryDateAndCvvInput(
            uiState = uiState,
            onAction = onAction
        )
        Spacer(modifier = Modifier.height(ECTheme.dimensions.twentyFour))

        AddressInput(
            uiState = uiState,
            onAction = onAction
        )

        CustomButton(
            text = " Order Now ",
            onClick = { onAction(PaymentContract.UiAction.OrderClick) }
        )
    }
}

@Composable
fun BankCard(
    uiState: PaymentContract.UiState
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(ECTheme.dimensions.four)
            .clip(RoundedCornerShape(ECTheme.dimensions.sixteen))
            .background(ECTheme.colors.primary)
            .height(ECTheme.dimensions.twoHundredTwenty)
    ) {
        Column(
            modifier = Modifier
                .padding(ECTheme.dimensions.sixteen)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "CARD HOLDER",
                color = ECTheme.colors.semiTransparentWhite,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = uiState.cardHolderName.uppercase(),
                color = ECTheme.colors.white,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = ECTheme.dimensions.sixteen)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = uiState.cardNumber.chunked(4).joinToString(" "),
                    fontSize = ECTheme.typography.extraLarge,
                    color = ECTheme.colors.white,
                    modifier = Modifier.padding(horizontal = ECTheme.dimensions.four)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "EXPIRES",
                        color = ECTheme.colors.semiTransparentWhite,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "${uiState.selectedMonth}/${uiState.selectedYear}",
                        color = ECTheme.colors.white,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Column {
                    Text(
                        text = "CVV",
                        color = ECTheme.colors.semiTransparentWhite,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = uiState.cvv,
                        color = ECTheme.colors.white,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            Text(
                text = "VISA",
                color = ECTheme.colors.white,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
fun CardHolderInput(
    uiState: PaymentContract.UiState,
    onAction: (PaymentContract.UiAction) -> Unit
) {
    OutlinedTextField(
        value = uiState.cardHolderName.uppercase(),
        onValueChange = { onAction(PaymentContract.UiAction.ChangeCardHolderName(it)) },
        label = { Text(text = "Card Holder Name") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
fun CardNumberInput(
    uiState: PaymentContract.UiState,
    onAction: (PaymentContract.UiAction) -> Unit
) {
    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                uiState.cardNumber.chunked(FOUR).joinToString(" ")
            )
        )
    }
    OutlinedTextField(
        value = textFieldValue,
        onValueChange = { newValue ->
            val rawInput = newValue.text.replace(" ", "")
            if (rawInput.length <= SIXTEEN && rawInput.all { it.isDigit() }) {
                val formattedCardNumber = rawInput.chunked(FOUR).joinToString(" ")
                val spaceDifference = formattedCardNumber.length - rawInput.length
                val newCursorPosition = newValue.selection.end + spaceDifference

                textFieldValue = TextFieldValue(
                    text = formattedCardNumber,
                    selection = TextRange(newCursorPosition.coerceAtMost(formattedCardNumber.length))
                )
                onAction(PaymentContract.UiAction.ChangeCardNumber(rawInput))
            }
        },
        label = { Text(text = "Card Number") },
        visualTransformation = VisualTransformation.None,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true
    )
}

@Composable
fun ExpiryDateAndCvvInput(
    uiState: PaymentContract.UiState,
    onAction: (PaymentContract.UiAction) -> Unit
) {
    val months = (ONE..TWELVE).map { it.toString().padStart(2, '0') }
    val years = (START_YEAR..END_YEAR).map { it.toString() }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier.weight(1f)) {
            OutlinedTextField(
                value = uiState.selectedMonth.ifEmpty { "MM" },
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "MM") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.clickable { onAction(PaymentContract.UiAction.ToggleMonthDropdown) }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAction(PaymentContract.UiAction.ToggleMonthDropdown) }
            )
            DropdownMenu(
                expanded = uiState.isMonthDropdownExpanded,
                onDismissRequest = { onAction(PaymentContract.UiAction.ToggleMonthDropdown) },
                modifier = Modifier
                    .width(ECTheme.dimensions.fortyEight)
            ) {
                months.forEach { month ->
                    DropdownMenuItem(
                        text = { Text(text = month) },
                        onClick = {
                            onAction(
                                PaymentContract.UiAction.ChangeExpiryDate(
                                    month,
                                    uiState.selectedYear
                                )
                            )
                            onAction(PaymentContract.UiAction.ToggleMonthDropdown)
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(ECTheme.dimensions.sixteen))

        Box(modifier = Modifier.weight(1f)) {
            OutlinedTextField(
                value = uiState.selectedYear.ifEmpty { "YY" },
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "YY") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.clickable { onAction(PaymentContract.UiAction.ToggleYearDropdown) }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAction(PaymentContract.UiAction.ToggleYearDropdown) }
            )
            DropdownMenu(
                expanded = uiState.isYearDropdownExpanded,
                onDismissRequest = { onAction(PaymentContract.UiAction.ToggleYearDropdown) },
                modifier = Modifier
                    .width(ECTheme.dimensions.seventyTwo)
            ) {
                years.forEach { year ->
                    DropdownMenuItem(
                        text = { Text(text = year) },
                        onClick = {
                            onAction(
                                PaymentContract.UiAction.ChangeExpiryDate(
                                    uiState.selectedMonth,
                                    year
                                )
                            )
                            onAction(PaymentContract.UiAction.ToggleYearDropdown)
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(ECTheme.dimensions.sixteen))

        OutlinedTextField(
            value = uiState.cvv,
            onValueChange = {
                if (it.length <= THREE && it.all { char -> char.isDigit() }) {
                    onAction(PaymentContract.UiAction.ChangeCvv(it))
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
    val cities = uiState.cities.map { it.il }
    val districts = uiState.cities.find { it.il == uiState.selectedCity }?.ilceleri ?: emptyList()

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Address",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = ECTheme.dimensions.sixteen)
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

        Spacer(modifier = Modifier.height(ECTheme.dimensions.eight))

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
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.selectedCity.isNotEmpty()
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

        Spacer(modifier = Modifier.height(ECTheme.dimensions.eight))

        OutlinedTextField(
            value = uiState.addressText,
            onValueChange = { onAction(PaymentContract.UiAction.ChangeAddressText(it)) },
            label = { Text(text = "Full Address") },
            modifier = Modifier
                .fillMaxWidth()
                .height(ECTheme.dimensions.oneHundredFifty),
            enabled = uiState.selectedCity.isNotEmpty() && uiState.selectedDistrict.isNotEmpty()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPaymentScreen() {
    ECTheme {
        val uiState = PaymentContract.UiState(
            cardHolderName = "JOHN DOE",
            cardNumber = "1234567812345678",
            expiryDate = "12/2025",
            cvv = "123",
            selectedMonth = "12",
            selectedYear = "2025",
            selectedCity = "Ankara",
            selectedDistrict = "California",
            addressText = "1234 Elm Street"
        )
        PaymentScreen(uiState = uiState, onAction = {}, onBackPress = {}, uiEffect = flow {})
    }
}
