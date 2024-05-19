package com.kapirti.video_food_delivery_shopping.model.service

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}
