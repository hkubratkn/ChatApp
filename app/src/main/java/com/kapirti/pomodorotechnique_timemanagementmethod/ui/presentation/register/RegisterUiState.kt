package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.register

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val button: Boolean = true
)
