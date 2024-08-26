package com.hoy.ecommercecompose.ui.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.ui.theme.ECTheme
import com.hoy.ecommercecompose.ui.theme.bodyFontFamily
import com.hoy.ecommercecompose.ui.theme.displayFontFamily
import kotlinx.coroutines.flow.Flow

@Composable
fun NotificationScreen(
    uiEffect: Flow<NotificationContract.UiEffect>,
    uiState: NotificationContract.UiState,
    onAction: (NotificationContract.UiAction) -> Unit,
    navigateBack: () -> Unit,
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(uiEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEffect.collect { effect ->
                when (effect) {
                    else -> {}
                }
            }
        }
    }
    Column {
        NotificationTopBar()
        NotificationList(notificationList = listOf())
    }

}

@Composable
fun NotificationTopBar(
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Notifications",
        fontSize = ECTheme.typography.extraLarge,
        color = ECTheme.colors.black,
        style = MaterialTheme.typography.headlineMedium,
        modifier = modifier
            .fillMaxWidth()
            .padding(ECTheme.dimensions.twelve)
    )
}

@Composable
fun NotificationList(
    notificationList: List<Any>,
    modifier: Modifier = Modifier,
) {
    if (notificationList.isEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_notifications),
                contentDescription = "Empty Favorite Icon",
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = stringResource(id = R.string.no_notifications),
                fontFamily = displayFontFamily,
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 8.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(id = R.string.no_notifications_long),
                fontFamily = bodyFontFamily,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    } else {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(ECTheme.dimensions.four)
        ) {
            items(notificationList) { paymentEntity ->
            }
        }
    }
}
