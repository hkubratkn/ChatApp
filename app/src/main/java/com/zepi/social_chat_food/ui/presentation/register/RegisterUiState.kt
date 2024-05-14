package com.zepi.social_chat_food.ui.presentation.register

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val button: Boolean = true
)
