package com.kapirti.ira.model.service

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}
