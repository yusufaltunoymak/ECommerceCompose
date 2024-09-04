package com.hoy.ecommercecompose.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.domain.model.DeleteFromFavoriteBody
import com.hoy.ecommercecompose.domain.usecase.favorite.DeleteFavoriteUseCase
import com.hoy.ecommercecompose.domain.usecase.favorite.GetFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoriteContract.UiState())
    val uiState: StateFlow<FavoriteContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<FavoriteContract.UiEffect>() }
    val uiEffect: Flow<FavoriteContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(action: FavoriteContract.UiAction) {
        when (action) {
            is FavoriteContract.UiAction.DeleteFromFavorites -> deleteFavorite(action.productId)
            is FavoriteContract.UiAction.LoadFavorites -> updateUiState {
                copy(
                    favoriteProducts = action.favoriteProducts
                )
            }
        }
    }

    fun loadFavorites() {
        viewModelScope.launch {
            getFavoriteUseCase(
                userId = firebaseAuth.currentUser?.uid.orEmpty()
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        updateUiState { copy(favoriteProducts = response.data) }
                    }

                    is Resource.Error -> {
                        updateUiState { copy(errorMessage = response.message) }
                    }
                }
            }
        }
    }

    private fun deleteFavorite(productId: Int) {
        viewModelScope.launch {
            val deleteFromFavoriteBody = DeleteFromFavoriteBody(
                userId = firebaseAuth.currentUser?.uid ?: "",
                id = productId
            )
            deleteFavoriteUseCase(deleteFromFavoriteBody).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        loadFavorites()
                    }

                    is Resource.Error -> {
                        updateUiState { copy(errorMessage = response.message) }
                        emitUiEffect(FavoriteContract.UiEffect.ShowError(response.message))
                    }
                }
            }
        }
    }

    private fun updateUiState(block: FavoriteContract.UiState.() -> FavoriteContract.UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: FavoriteContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }
}
