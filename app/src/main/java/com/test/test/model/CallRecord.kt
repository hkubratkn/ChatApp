
package com.test.test.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import com.test.test.ui.presentation.calls.CallType

data class CallRecord(
    //@DocumentId val id: String = "id",
    val callType: CallType? = null,
    val userId: String = "",
    //val roomId: String = "",
    val peerName: String = "",
    @ServerTimestamp
    var callStart: Timestamp? = null,
    @ServerTimestamp
    var callEnd: Timestamp? = null,
)
