package com.hoy.ecommercecompose.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.domain.model.DeleteFromFavoriteBody
import com.hoy.ecommercecompose.domain.model.ProductUi
import com.hoy.ecommercecompose.domain.usecase.favorite.DeleteFavoriteUseCase
import com.hoy.ecommercecompose.domain.usecase.favorite.GetFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoriteUiState())
    val uiState: StateFlow<FavoriteUiState> = _uiState.asStateFlow()


    fun loadFavorites() {
        viewModelScope.launch {
            getFavoriteUseCase(
                userId = firebaseAuth.currentUser?.uid
                    ?: ""
            ).collect { response ->
                response.data?.let { favoriteProductList ->
                    when (response) {
                        is Resource.Loading -> {
                            _uiState.update { it.copy(isLoading = true) }
                        }

                        is Resource.Success -> {
                            _uiState.update {
                                it.copy(
                                    favoriteProducts = favoriteProductList,
                                    isLoading = false
                                )
                            }
                        }

                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(
                                    errorMessage = response.message,
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun deleteFavorite(product: ProductUi) {
        viewModelScope.launch {
            val deleteFromFavoriteBody = DeleteFromFavoriteBody(
                userId = firebaseAuth.currentUser?.uid ?: "",
                id = product.id)
            deleteFavoriteUseCase(deleteFromFavoriteBody).collect { response ->
                response.data?.let {
                    when (response) {
                        is Resource.Loading -> {
                            _uiState.update { it.copy(isLoading = true) }
                        }

                        is Resource.Success -> {
                            loadFavorites()
                        }

                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(
                                    errorMessage = response.message,
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
