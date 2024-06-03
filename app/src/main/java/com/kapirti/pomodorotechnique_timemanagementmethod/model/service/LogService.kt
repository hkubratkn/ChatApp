package com.kapirti.pomodorotechnique_timemanagementmethod.model.service

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}
