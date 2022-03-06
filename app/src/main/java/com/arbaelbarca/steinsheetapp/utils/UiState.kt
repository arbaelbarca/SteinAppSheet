package com.arbaelbarca.steinsheetapp.utils

sealed class UiState<T> {
    data class Loading<T>(val isLoading: Boolean = true) : UiState<T>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Failure<T>(val throwable: Throwable) : UiState<T>()
}