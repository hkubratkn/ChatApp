package com.zepi.social_chat_food.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class User(
    val uid: String = "uid",
    val displayName: String = "Display name",
    val name: String = "Name",
    val surname: String = "Surname",
    val photo: String = "Photo",
    val birthday: String = "Birthday",
    val gender: String = "Gender",
    val hobby: List<String> = listOf(),
    val description: String = "Description",
    val language: String = "Language",
    val online: Boolean = false,
    val token: String = "Token",
    @ServerTimestamp
    var lastSeen: Timestamp? = null,
    @ServerTimestamp
    var date: Timestamp? = null
)
