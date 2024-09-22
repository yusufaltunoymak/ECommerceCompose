package com.hoy.ecommercecompose.ui

import androidx.lifecycle.ViewModel
import com.hoy.ecommercecompose.data.repository.ConnectivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ConnectivityViewModel @Inject constructor(connectivityRepository: ConnectivityRepository) :
    ViewModel() {
    val isConnected = connectivityRepository.isConnected
}