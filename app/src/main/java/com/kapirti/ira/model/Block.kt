package com.kapirti.ira.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Block(
    val uid: String = "",
    val name: String = "",
    val surname: String = "",
    val photo: String = "",
    @ServerTimestamp
    val date: Timestamp? = null
)
