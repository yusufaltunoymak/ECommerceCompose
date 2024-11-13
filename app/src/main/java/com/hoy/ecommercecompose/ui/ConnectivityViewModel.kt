package com.hoy.ecommercecompose.ui

import androidx.lifecycle.ViewModel
import com.hoy.ecommercecompose.domain.repository.ConnectivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ConnectivityViewModel @Inject constructor(connectivityRepository: ConnectivityRepository) :
    ViewModel() {
    private val _isConnected = connectivityRepository.isConnected

    val isConnected get() = _isConnected.asStateFlow()
}
