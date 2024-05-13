package com.google.android.samples.socialite.repository

import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.samples.socialite.data.createTestDatabase
import com.zepi.social_chat_food.soci.repository.ChatRepository
import com.zepi.social_chat_food.soci.repository.NotificationHelper
import com.zepi.social_chat_food.soci.widget.model.WidgetModelRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

fun createTestRepository(): ChatRepository {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val database = createTestDatabase()
    return ChatRepository(
        chatDao = database.chatDao(),
        contactDao = database.contactDao(),
        messageDao = database.messageDao(),
        notificationHelper = NotificationHelper(context),
        widgetModelRepository = WidgetModelRepository(database.widgetDao(), CoroutineScope(SupervisorJob() + Dispatchers.Default), context),
        coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default),
    )
}
