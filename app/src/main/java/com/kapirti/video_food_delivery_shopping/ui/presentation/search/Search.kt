package com.kapirti.video_food_delivery_shopping.ui.presentation.search

import androidx.compose.runtime.Immutable
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_1
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_10
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_2
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_3
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_4
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_5
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_6
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_7
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_8
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_9
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

object SearchRepo {
    fun getCategories(): List<SearchCategoryCollection> = searchCategoryCollections

    suspend fun search(query: String, users: List<com.kapirti.video_food_delivery_shopping.model.User>): List<com.kapirti.video_food_delivery_shopping.model.User> = withContext(Dispatchers.Default) {
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
