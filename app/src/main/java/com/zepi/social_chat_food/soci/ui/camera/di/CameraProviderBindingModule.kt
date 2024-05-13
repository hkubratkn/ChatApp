package com.zepi.social_chat_food.soci.ui.camera.di

import com.zepi.social_chat_food.soci.ui.camera.CameraProviderManager
import com.zepi.social_chat_food.soci.ui.camera.CameraXProcessCameraProviderManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CameraProviderBindingModule {

    @Binds
    fun bindCameraProviderManager(manager: CameraXProcessCameraProviderManager): CameraProviderManager
}
