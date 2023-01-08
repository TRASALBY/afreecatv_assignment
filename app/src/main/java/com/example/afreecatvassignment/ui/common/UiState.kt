package com.example.afreecatvassignment.ui.common

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<out T>(val item: T) : UiState<T>()
    object NetworkFailure : UiState<Nothing>()
    object Failure : UiState<Nothing>()
}