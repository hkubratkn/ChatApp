package com.zepi.social_chat_food.iraaa.core.data.di

import com.zepi.social_chat_food.iraaa.core.data.ConnectivityManagerNetworkMonitor
import com.zepi.social_chat_food.iraaa.core.data.NetworkMonitor
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
