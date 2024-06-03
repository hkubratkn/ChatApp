package com.kapirti.pomodorotechnique_timemanagementmethod.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class Block(
    val uid: String = "",
    val name: String = "",
    val surname: String = "",
    val photo: String = "",
    @ServerTimestamp
    val date: Timestamp? = null
)
