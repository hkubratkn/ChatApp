/*
 * Copyright (C) 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.pomodoro

import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.LogService
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.PomodoroViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.CountDownTimer
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import com.kapirti.pomodorotechnique_timemanagementmethod.R.raw as AppSound
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.PomoService
import com.kapirti.pomodorotechnique_timemanagementmethod.core.room.pomodoro.WorkTime
import com.kapirti.pomodorotechnique_timemanagementmethod.core.room.pomodoro.WorkTimeDao
import java.time.LocalDateTime

@HiltViewModel
class PomodoroViewModel @Inject constructor(
    private val pomoService: PomoService,
    private val workTimeDao: WorkTimeDao,
    logService: LogService
): PomodoroViewModel(logService){

    private val _pomo = mutableStateOf(20)
    val pomo: Int
        get() = _pomo.value

    private val _startBtnStatus = mutableStateOf(true)
    val startBtnStatus: Boolean
        get() = _startBtnStatus.value

    private val _finishBtnStatus = mutableStateOf(false)
    val finishBtnStatus: Boolean
        get() = _finishBtnStatus.value

    private val _finishClick = mutableStateOf(false)
    val finishClick: Boolean
        get() = _finishClick.value

    @RequiresApi(Build.VERSION_CODES.O)
    private val day = LocalDateTime.now().dayOfYear

    init {
        launchCatching {
            pomoService.pomo().collect { scored ->
                _pomo.value = scored
            }
        }
    }

    fun onStartPressed(context: Context, navigateTimeOver: () -> Unit) {
        launchCatching {
            val pomoCalculate = _pomo.value*60*1000
            _startBtnStatus.value = false
            _finishBtnStatus.value = true
            object : CountDownTimer(pomoCalculate.toLong(), 1000) {
                override fun onTick(p0: Long) {
                    _pomo.value = (p0 / 1000).toInt()

                    if(_finishClick.value){
                        cancel()
                        _finishBtnStatus.value = false
                    }
                }

                @RequiresApi(Build.VERSION_CODES.O)
                override fun onFinish() {
                    _pomo.value = 0
                    val mediaPlayer = MediaPlayer.create(
                        context,
                        AppSound.app_src_main_res_raw_musicback
                    )
                    mediaPlayer.start()
                    saveScore()
                    Thread.sleep(5000)
                    mediaPlayer.stop()
                    navigateTimeOver()
                }
            }.start()
        }
    }

    fun onFinishClicked(){
        launchCatching{
            _finishClick.value = true
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveScore(){
        launchCatching {
            val tt = workTimeDao.workTime(day)
            tt.collect{ itWT ->
                val score = itWT?.work ?: 0
                val totalScore = score + _pomo.value
                workTimeDao.insert(WorkTime(uid = day, work = totalScore))
            }
        }
    }
}
