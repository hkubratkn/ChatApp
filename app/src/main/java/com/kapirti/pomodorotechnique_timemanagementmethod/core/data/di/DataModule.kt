package com.kapirti.pomodorotechnique_timemanagementmethod.core.data.di

import com.kapirti.pomodorotechnique_timemanagementmethod.core.data.ConnectivityManagerNetworkMonitor
import com.kapirti.pomodorotechnique_timemanagementmethod.core.data.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor
}
