package com.zepi.social_chat_food.model.service

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}
