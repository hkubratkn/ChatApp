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

package com.kapirti.pomodorotechnique_timemanagementmethod.core.room.pomodoro

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkTimeDao {
    @Query("SELECT * FROM work_time")
    fun workTimes(): Flow<List<WorkTime>>

    @Query("SELECT * FROM work_time WHERE uid IN (:todayId)")
    fun workTime(todayId: Int): Flow<WorkTime?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(workTime: WorkTime)

    @Delete
    suspend fun delete(workTime: WorkTime)
}
