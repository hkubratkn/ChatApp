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

package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.timeover

import android.content.Context
import android.media.MediaPlayer
import android.os.CountDownTimer
import androidx.compose.runtime.mutableStateOf
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.PomodoroViewModel
import com.kapirti.pomodorotechnique_timemanagementmethod.R.raw as AppRaw
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimeOverViewModel @Inject constructor(
    logService: LogService
): PomodoroViewModel(logService){
    private val _score = mutableStateOf("5 min")
    val score: String
        get() = _score.value

    private val _startBtnStatus = mutableStateOf(true)
    val startBtnStatus: Boolean
        get() = _startBtnStatus.value

    private val _finishBtnStatus = mutableStateOf(false)
    val finishBtnStatus: Boolean
        get() = _finishBtnStatus.value

    private val _finishClick = mutableStateOf(false)

    fun onStartPressed(context: Context, navigateToHome: () -> Unit,){
        launchCatching{
            _startBtnStatus.value = false
            _finishBtnStatus.value = true
            object : CountDownTimer(300000, 1000) {
                override fun onTick(p0: Long) {
                    _score.value = (p0 / 1000).toString()

                    if(_finishClick.value){
                        cancel()
                        navigateToHome()
                    }
                }

                override fun onFinish() {
                    _score.value = 0.toString()
                    val mediaPlayer = MediaPlayer.create(
                        context,
                        AppRaw.app_src_main_res_raw_musicback
                    )
                    mediaPlayer.start()
                    Thread.sleep(5000)
                    mediaPlayer.stop()
                    navigateToHome()
                }
            }.start()
        }
    }

    fun onFinishClicked(){
        launchCatching{
            _finishClick.value = true
        }
    }
}
