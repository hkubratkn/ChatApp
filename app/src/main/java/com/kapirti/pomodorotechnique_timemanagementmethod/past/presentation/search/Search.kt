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

package com.kapirti.pomodorotechnique_timemanagementmethod.past.presentation.search
/**
import androidx.compose.runtime.Immutable
import com.kapirti.pomodorotechnique_timemanagementmethod.past.core.constants.Avatar.AVATAR_1
import com.kapirti.pomodorotechnique_timemanagementmethod.past.core.constants.Avatar.AVATAR_10
import com.kapirti.pomodorotechnique_timemanagementmethod.past.core.constants.Avatar.AVATAR_2
import com.kapirti.pomodorotechnique_timemanagementmethod.past.core.constants.Avatar.AVATAR_3
import com.kapirti.pomodorotechnique_timemanagementmethod.past.core.constants.Avatar.AVATAR_4
import com.kapirti.pomodorotechnique_timemanagementmethod.past.core.constants.Avatar.AVATAR_5
import com.kapirti.pomodorotechnique_timemanagementmethod.past.core.constants.Avatar.AVATAR_6
import com.kapirti.pomodorotechnique_timemanagementmethod.past.core.constants.Avatar.AVATAR_7
import com.kapirti.pomodorotechnique_timemanagementmethod.past.core.constants.Avatar.AVATAR_8
import com.kapirti.pomodorotechnique_timemanagementmethod.past.core.constants.Avatar.AVATAR_9
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

object SearchRepo {
    fun getCategories(): List<SearchCategoryCollection> = searchCategoryCollections

    suspend fun search(query: String, users: List<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.User>): List<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.User> = withContext(Dispatchers.Default) {
        delay(200L)
        users.filter { it.displayName.contains(query, ignoreCase = true) }
    }
}

@Immutable
data class SearchCategoryCollection(
    val id: Long,
    val name: String,
    val categories: List<SearchCategory>
)

@Immutable
data class SearchCategory(
    val name: String,
    val imageUrl: String
)

private val searchCategoryCollections = listOf(
    SearchCategoryCollection(
        id = 0L,
        name = "New Users",
        categories = listOf(
            SearchCategory(
                name = "Adem",
                imageUrl = AVATAR_1
            ),
            SearchCategory(
                name = "Fisun Ak",
                imageUrl = AVATAR_2
            ),
            SearchCategory(
                name = "Fevzi Cakmak",
                imageUrl = AVATAR_3
            ),
            SearchCategory(
                name = "Nurten Okcu",
                imageUrl = AVATAR_4
            )
        )
    ),
    SearchCategoryCollection(
        id = 1L,
        name = "Users",
        categories = listOf(
            SearchCategory(
                name = "Osman Sınav",
                imageUrl = AVATAR_5
            ),
            SearchCategory(
                name = "ulya Kocyigit",
                imageUrl = AVATAR_6
            ),
            SearchCategory(
                name = "Pelo",
                imageUrl = AVATAR_7
            ),
            SearchCategory(
                name = "Veysel Fırat",
                imageUrl = AVATAR_8
            ),
            SearchCategory(
                name = "Vanlı Osman",
                imageUrl = AVATAR_9
            ),
            SearchCategory(
                name = "Genc Osman",
                imageUrl = AVATAR_10
            )
        )
    )
)
*/
