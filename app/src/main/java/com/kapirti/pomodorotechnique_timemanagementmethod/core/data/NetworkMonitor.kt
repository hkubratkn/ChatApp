package com.kapirti.pomodorotechnique_timemanagementmethod.core.data

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    val isOnline: Flow<Boolean>
}
