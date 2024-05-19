package com.kapirti.video_food_delivery_shopping.core.data.di

import com.kapirti.video_food_delivery_shopping.core.data.ConnectivityManagerNetworkMonitor
import com.kapirti.video_food_delivery_shopping.core.data.NetworkMonitor
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
