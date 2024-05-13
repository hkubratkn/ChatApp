package com.zepi.social_chat_food.iraaa.ui.presentation.profile

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import com.zepi.social_chat_food.R.string as AppText
import com.zepi.social_chat_food.iraaa.core.Async
import com.zepi.social_chat_food.iraaa.core.WhileUiSubscribed
import com.zepi.social_chat_food.iraaa.core.constants.EditType.PHOTO
import com.zepi.social_chat_food.iraaa.core.datastore.EditTypeRepository
import com.zepi.social_chat_food.iraaa.core.room.profile.Profile
import com.zepi.social_chat_food.iraaa.core.room.profile.ProfileDao
import com.zepi.social_chat_food.iraaa.model.UserPhotos
import com.zepi.social_chat_food.iraaa.model.service.AccountService
import com.zepi.social_chat_food.iraaa.model.service.FirestoreService
import com.zepi.social_chat_food.iraaa.model.service.LogService
import com.zepi.social_chat_food.iraaa.ui.presentation.QChatViewModel

/**
data class InterestsUiState(
    val topics: List<String> = emptyList(),
    val people: List<String> = emptyList(),
    val loading: Boolean = false,
)


data class SelectedAssetUiState(
    val items: List<Asset> = emptyList(),
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
)*/


/**


@HiltViewModel
class ProfileViewModel @Inject constructor(
private val accountService: AccountService,
logService: LogService,
//    private val editTypeRepository: EditTypeRepository,
): QChatViewModel(logService){
val displayName = accountService.currentUserDisplayName




fun refresh() {
_isLoading.value = true
launchCatching {
_isLoading.value = false
}
}



/**
fun onPhotoClick(openScreen: (String) -> Unit){
launchCatching {
editTypeRepository.saveEditTypeState(PHOTO)
openScreen(EDIT_SCREEN)
}
}
fun onDisplayNameClick(openScreen: (String) -> Unit){
launchCatching {
editTypeRepository.saveEditTypeState(DISPLAY_NAME)
openScreen(EDIT_SCREEN)
}
}
fun onNameSurnameClick(openScreen: (String) -> Unit){
launchCatching {
editTypeRepository.saveEditTypeState(NAME_SURNAME)
openScreen(EDIT_SCREEN)
}
}
fun onGenderClick(openScreen: (String) -> Unit){
launchCatching {
editTypeRepository.saveEditTypeState(GENDER)
openScreen(EDIT_SCREEN)
}
}
fun onBirthdayClick(openScreen: (String) -> Unit){
launchCatching {
editTypeRepository.saveEditTypeState(BIRTHDAY)
openScreen(EDIT_SCREEN)
}
}
fun onDescriptionClick(openScreen: (String) -> Unit){
launchCatching {
editTypeRepository.saveEditTypeState(DESCRIPTION)
openScreen(EDIT_SCREEN)
}
}

fun onSettingsClick(openScreen: (String) -> Unit) = openScreen(SETTINGS_SCREEN)
fun onLoginClick(openScreen: (String) -> Unit) = openScreen(LOG_IN_SCREEN)
fun onRegisterClick(openScreen: (String) -> Unit) = openScreen(REGISTER_SCREEN)*/
}*/



data class ProfileUiState(
    val profile: Profile? = null,
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
)

data class SelectedUserPhotosUiState(
    val items: List<UserPhotos> = emptyList(),
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
)


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    private val profileDao: ProfileDao,
    private val editTypeRepository: EditTypeRepository,
    logService: LogService
) : QChatViewModel(logService) {
    val hasUser = accountService.hasUser

    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _isLoading = MutableStateFlow(false)
    private val _profileAsync = profileDao.getProfile()
        .map { handleProfile(it) }
        .catch { emit(Async.Error(AppText.loading_profile_error)) }


    val uiState: StateFlow<ProfileUiState> = combine(
        _userMessage, _isLoading, _profileAsync
    ) { userMessage, isLoading, taskAsync ->
        when (taskAsync) {
            Async.Loading -> {
                ProfileUiState(isLoading = true)
            }
            is Async.Error -> {
                ProfileUiState(
                    userMessage = taskAsync.errorMessage,
                )
            }
            is Async.Success -> {
                ProfileUiState(
                    profile = taskAsync.data,
                    isLoading = isLoading,
                    userMessage = userMessage,
                )
            }
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = ProfileUiState(isLoading = true)
        )



    private val _userMessagePhotos: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _isLoadingPhotos = MutableStateFlow(false)
    private val _isTaskDeletedPhotos = MutableStateFlow(false)
    private val _taskAsyncPhotos = firestoreService.userPhotos
        .map { handleUserPhotos(it) }
        .catch { emit(Async.Error(AppText.loading_user_photos_error)) }

    val selectedPhotos: StateFlow<SelectedUserPhotosUiState> = combine(
        _userMessagePhotos, _isLoadingPhotos, _isTaskDeletedPhotos, _taskAsyncPhotos
    ) { userMessage, isLoading, isTaskDeleted, taskAsync ->
        when (taskAsync) {
            Async.Loading -> {
                SelectedUserPhotosUiState(isLoading = true)
            }

            is Async.Error -> {
                SelectedUserPhotosUiState(
                    userMessage = taskAsync.errorMessage,
                )
            }

            is Async.Success -> {
                SelectedUserPhotosUiState(
                    items = taskAsync.data,
                    isLoading = isLoading,
                    userMessage = userMessage,
                )
            }
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = SelectedUserPhotosUiState(isLoading = true)
        )

    private fun handleProfile(profile: Profile?): Async<Profile?> {
        if (profile == null) {
            return Async.Error(AppText.profile_not_found)
        }
        return Async.Success(profile)
    }
    private fun handleUserPhotos(task: List<UserPhotos>?): Async<List<UserPhotos>> {
        if (task == null) {
            return Async.Error(AppText.user_photos_not_found)
        }
        return Async.Success(task)
    }

    fun refresh() { launchCatching {  } }

    fun onAddClick(navigateEdit: () -> Unit){
        launchCatching {
            editTypeRepository.saveEditTypeState(PHOTO)
            navigateEdit()
        }
    }
}
  /*

    private val _userMessageAsset: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _isLoadingAsset = MutableStateFlow(false)
    private val _isTaskDeletedAsset = MutableStateFlow(false)
    private val _taskAsyncAsset = firestoreService.myAssets
        .map { handleTask(it) }
        .catch { emit(Async.Error(AppText.loading_assets_error)) }

    val selectedAsset: StateFlow<SelectedAssetUiState> = combine(
        _userMessageAsset, _isLoadingAsset, _isTaskDeletedAsset, _taskAsyncAsset
    ) { userMessage, isLoading, isTaskDeleted, taskAsync ->
        when (taskAsync) {
            Async.Loading -> {
                SelectedAssetUiState(isLoading = true)
            }

            is Async.Error -> {
                SelectedAssetUiState(
                    userMessage = taskAsync.errorMessage,
                )
            }

            is Async.Success -> {
                SelectedAssetUiState(
                    items = taskAsync.data,
                    isLoading = isLoading,
                    userMessage = userMessage,
                )
            }
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = SelectedAssetUiState(isLoading = true)
        )

    private fun handleTask(task: List<Asset>?): Async<List<Asset>> {
    if (task == null) {
    return Async.Error(AppText.assets_not_found)
    }
    return Async.Success(task)
    }
    fun refresh() {
    launchCatching {  }
    }

}



    private val topics by lazy {
        listOf(
            InterestSection("Android", listOf("Jetpack Compose", "Kotlin", "Jetpack")),
            InterestSection(
                "Programming",
                listOf("Kotlin", "Declarative UIs", "Java", "Unidirectional Data Flow", "C++")
            ),
            InterestSection("Technology", listOf("Pixel", "Google"))
        )
    }

    private val people by lazy {
        listOf(
            "Kobalt Toral",
            "K'Kola Uvarek",
            "Kris Vriloc",
            "Grala Valdyr",
            "Kruel Valaxar",
            "L'Elij Venonn",
            "Kraag Solazarn",
            "Tava Targesh",
            "Kemarrin Muuda"
        )
    }



    // UI state exposed to the UI   Favorites(R.string.interests_section_topics),
    //    Assets(R.string.interests_section_people),
    private val _uiState = MutableStateFlow(InterestsUiState(loading = true))
    val uiState: StateFlow<InterestsUiState> = _uiState.asStateFlow()





    init {
        refreshAll()
    }

 /**   fun toggleTopicSelection(topic: TopicSelection) {
        viewModelScope.launch {
            interestsRepository.toggleTopicSelection(topic)
        }
    }

    fun togglePersonSelected(person: String) {
        viewModelScope.launch {
            interestsRepository.togglePersonSelected(person)
        }
    }*/


    private fun refreshAll() {
        _uiState.update { it.copy(loading = true) }

        viewModelScope.launch {
            // Trigger repository requests in parallel
            /**       val topicsDeferred = async { interestsRepository.getTopics() }
            val peopleDeferred = async { interestsRepository.getPeople() }
            val publicationsDeferred = async { interestsRepository.getPublications() }

            // Wait for all requests to finish
            val topics = topicsDeferred.await().successOr(emptyList())
            val people = peopleDeferred.await().successOr(emptyList())
            val publications = publicationsDeferred.await().successOr(emptyList())*/

            _uiState.update {
                it.copy(
                    loading = false,
                    topics = topics,
                    people = people,
                )
            }
        }
    }
}
    */
