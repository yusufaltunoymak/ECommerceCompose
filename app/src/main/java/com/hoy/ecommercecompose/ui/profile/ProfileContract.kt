package com.hoy.ecommercecompose.ui.profile

import com.hoy.ecommercecompose.data.source.remote.model.ProductDetail
import com.hoy.ecommercecompose.data.source.remote.model.response.BaseResponse

object  ProfileContract {
    data class UiState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
    )

    sealed class UiAction {
        data object BackButtonClick : UiAction()
    }

    sealed class UiEffect {
        data object ShowError : UiEffect()
        data object BackScreen : UiEffect()
        data object ShowToastMessage : UiEffect()
        data object NavigateBack : UiEffect()
    }
}