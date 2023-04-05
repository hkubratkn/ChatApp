/*
 * Copyright (C) 2023 The Android Open Source Project
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

package com.example.android.social.data

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChatDaoTest {

    private fun createTestDatabase(): AppDatabase {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        return Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    }

    @Test
    fun initialData() = runBlocking {
        val db = createTestDatabase()
        db.populateInitialData()
        val contacts = db.contact().loadAll()
        assertThat(contacts).hasSize(5)
        val chatDetails = db.chat().loadAllDetails()
        assertThat(chatDetails).hasSize(4)
        for (detail in chatDetails) {
            assertThat(detail.attendees).hasSize(1)
            val messages = db.message().loadAll(detail.chat.id)
            assertThat(messages).hasSize(2)
        }
    }
}