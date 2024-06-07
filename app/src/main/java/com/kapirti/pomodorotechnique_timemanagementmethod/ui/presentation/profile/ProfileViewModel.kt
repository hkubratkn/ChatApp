package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.profile

import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.DESCRIPTION
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.DISPLAY_NAME
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.NAME_SURNAME
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.PROFILE_PHOTO
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.EditTypeRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.UserIdRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.model.User
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.AccountService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.FirestoreService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.LogService
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.PomodoroViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    private val userIdRepository: UserIdRepository,
    private val editTypeRepository: EditTypeRepository,
    logService: LogService,
): PomodoroViewModel(logService){
    val hasUser = accountService.hasUser

    private val _user = MutableStateFlow<User>(User())
    var user: StateFlow<User> = _user

    init {
        launchCatching {
            userIdRepository.saveUserIdState(accountService.currentUserId)
            firestoreService.getUser(accountService.currentUserId)?.let { itUser ->
                _user.value = itUser
            }
        }
    }

    fun onProfilePhotoClick(navigateEdit: () -> Unit){
        launchCatching {
            editTypeRepository.saveEditTypeState(PROFILE_PHOTO)
            navigateEdit()
        }
    }
    fun onDisplayNameClick(navigateEdit: () -> Unit){
        launchCatching {
            editTypeRepository.saveEditTypeState(DISPLAY_NAME)
            navigateEdit()
        }
    }
    fun onNameSurnameClick(navigateEdit: () -> Unit){
        launchCatching {
            editTypeRepository.saveEditTypeState(NAME_SURNAME)
            navigateEdit()
        }
    }
    fun onBirthdayClick(navigateEdit: () -> Unit){
        launchCatching {
           // editTypeRepository.saveEditTypeState(BIRTHDAY)
           // navigateEdit()
        }
    }
    fun onDescriptionClick(navigateEdit: () -> Unit){
        launchCatching {
            editTypeRepository.saveEditTypeState(DESCRIPTION)
            navigateEdit()
        }
    }
}




/**

data class SelectedUserPhotosUiState(
    val items: List<com.kapirti.video_food_delivery_shopping.model.UserPhotos> = emptyList(),
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
)

    private val editTypeRepository: EditTypeRepository,
    logService: LogService
) : ZepiViewModel(logService) {
    val uid = accountService.currentUserId


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


    private fun handleUserPhotos(task: List<com.kapirti.video_food_delivery_shopping.model.UserPhotos>?): Async<List<com.kapirti.video_food_delivery_shopping.model.UserPhotos>> {
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
*/

