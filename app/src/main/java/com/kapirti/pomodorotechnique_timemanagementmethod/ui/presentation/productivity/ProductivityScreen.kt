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

package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.productivity

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kapirti.pomodorotechnique_timemanagementmethod.R.string as AppText
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.BasicButton
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.BottomCard
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.TextPomo
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.card
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.formatTime
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.smallSpacer
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.tabContainerModifier
import com.kapirti.pomodorotechnique_timemanagementmethod.past.common.composable.InterestsAdaptiveContentLayout
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.productivity.ext.ProductivityTabRow


enum class SectionsProductivity(@StringRes val titleResId: Int) {
    Pomodoro(AppText.pomodoro),
    Timer(AppText.timer),
    Stayed(AppText.stayed)
}

class TabContentProductivity(val section: SectionsProductivity, val content: @Composable () -> Unit)

@Composable
fun rememberTabContent(
    pomo: Long,
    finishClick: Boolean,
    startBtnStatus: Boolean,
    finishBtnStatus: Boolean,
    navigateToPomodoro: () -> Unit,
    showInterstialAd: () -> Unit,
    onStartPressed: () -> Unit,
    onFinishClicked: () -> Unit,

    timerValue: Long,
    onStartClick: () -> Unit,
    onPauseClick: () -> Unit,
    onStopClick: () -> Unit,
): List<TabContentProductivity> {
    val pomodoroSection = TabContentProductivity(SectionsProductivity.Pomodoro) {
        TabWithPomodoro(pomo = pomo, finishClick = finishClick, startBtnStatus = startBtnStatus, finishBtnStatus = finishBtnStatus, navigateToPomodoro = navigateToPomodoro, onFinishClicked = onFinishClicked, onStartPressed = onStartPressed, showInterstialAd = showInterstialAd, ) }
    val timerSection = TabContentProductivity(SectionsProductivity.Timer) {
        TabWithTimer(timerValue = timerValue, onStartClick = onStartClick, onPauseClick = onPauseClick,onStopClick = onStopClick) }
    val stayedSection = TabContentProductivity(SectionsProductivity.Stayed) { TabWithStayed() }

    return listOf(pomodoroSection, timerSection, stayedSection)
}

@Composable
fun ProductivityScreen(
    currentSection: SectionsProductivity,
    isExpandedScreen: Boolean,
    updateSection: (SectionsProductivity) -> Unit,
    tabContent: List<TabContentProductivity>,
    modifier: Modifier = Modifier
) {
    val selectedTabIndex = tabContent.indexOfFirst { it.section == currentSection }
    Column(modifier) {
        ProductivityTabRow(selectedTabIndex, updateSection, tabContent, isExpandedScreen)
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
        )
        Box(modifier = Modifier.weight(1f)) {
            // display the current tab content which is a @Composable () -> Unit
            tabContent[selectedTabIndex].content()
        }
    }
}


@Composable
private fun TabWithPomodoro(
    pomo: Long,
    finishClick: Boolean,
    startBtnStatus: Boolean,
    finishBtnStatus: Boolean,
    navigateToPomodoro: () -> Unit,
    showInterstialAd: () -> Unit,
    onStartPressed: () -> Unit,
    onFinishClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    InterestsAdaptiveContentLayout(
        topPadding = 16.dp,
        modifier = tabContainerModifier.verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextPomo(title = pomo.formatTime())
            Spacer(modifier = Modifier.smallSpacer())

            if (finishClick) {
                BasicButton(
                    text = AppText.try_again, modifier = Modifier.card(), true,
                    action = {
                        navigateToPomodoro()
                        showInterstialAd()
                    }
                )
                Spacer(modifier = Modifier.smallSpacer())
            }
            BottomCard(
                start = AppText.start,
                finish = AppText.done,
                startBtnStatus = startBtnStatus,
                finishBtnStatus = finishBtnStatus,
                onStartClick = {
                    onStartPressed()
                },
                onFinishClick = {
                    onFinishClicked()
                }
            )

        }
    }
}



@Composable
private fun TabWithTimer(
    timerValue: Long,
    onStartClick: () -> Unit,
    onPauseClick: () -> Unit,
    onStopClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    InterestsAdaptiveContentLayout(
        topPadding = 16.dp,
        modifier = tabContainerModifier.verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = timerValue.formatTime(), fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = onStartClick) {
                    Text("Start")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = onPauseClick) {
                    Text("Pause")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = onStopClick) {
                    Text("Stop")
                }
            }
        }
    }
}


@Composable
private fun TabWithStayed(
    //modifier: Modifier = Modifier,
) {
    InterestsAdaptiveContentLayout(
        topPadding = 16.dp,
        modifier = tabContainerModifier.verticalScroll(rememberScrollState())
    ) {
    }
}
