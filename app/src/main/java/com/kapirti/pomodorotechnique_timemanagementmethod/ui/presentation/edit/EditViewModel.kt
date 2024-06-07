package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.edit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.Timestamp
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.Cons.DEFAULT_LANGUAGE_CODE
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.DELETE
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.DESCRIPTION
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.DISPLAY_NAME
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.FEEDBACK
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.LANG
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.NAME_SURNAME
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.POMO
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.PROFILE
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.PROFILE_PHOTO
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.EditTypeRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.LangRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.PomoService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.Delete
import com.kapirti.pomodorotechnique_timemanagementmethod.model.Feedback
import com.kapirti.pomodorotechnique_timemanagementmethod.model.User
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.AccountService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.FirestoreService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.LogService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.StorageService
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.PomodoroViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.ByteArrayOutputStream
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    private val storageService: StorageService,
    private val editTypeRepository: EditTypeRepository,
    private val langRepository: LangRepository,
    private val pomoService: PomoService,
    logService: LogService,
): PomodoroViewModel(logService) {
    val uid = accountService.currentUserId

    private val _editType = mutableStateOf<String?>(null)
    val editType: String?
        get() = _editType.value

    private val _lang = mutableStateOf<String?>(null)
    val lang: String?
        get() = _lang.value

    private val _pomo = mutableStateOf<Int?>(20)
    val pomo: Int?
        get() = _pomo.value

    private val _increaseBtnState = mutableStateOf<Boolean?>(true)
    val increaseBtnState: Boolean?
        get() = _increaseBtnState.value


    var uiState = mutableStateOf(SettingsUiState())
        private set

    private val password
        get() = uiState.value.password

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    init {
        launchCatching {
            editTypeRepository.readEditTypeState().collect {
                _editType.value = it
                langRepository.readLangState().collect { itLang ->
                    _lang.value = itLang
                    pomoService.pomo().collect { scored ->
                        _pomo.value = scored
                    }
                }
            }
        }
    }


    private val questionOrder: List<SurveyQuestion> = when (_editType.value) {
        PROFILE -> listOf(
            SurveyQuestion.DISPLAY_NAME,
            SurveyQuestion.NAME_SURNAME,
            SurveyQuestion.AVATAR,
            SurveyQuestion.DESCRIPTION,
        )
        PROFILE_PHOTO -> listOf(SurveyQuestion.TAKE_SELFIE)
        DISPLAY_NAME -> listOf(SurveyQuestion.DISPLAY_NAME)
        NAME_SURNAME -> listOf(SurveyQuestion.NAME_SURNAME)
        DESCRIPTION -> listOf(SurveyQuestion.DESCRIPTION)
        DELETE -> listOf(SurveyQuestion.DELETE)
        FEEDBACK -> listOf(SurveyQuestion.FEEDBACK)
        LANG -> listOf(SurveyQuestion.LANG)
        POMO -> listOf(SurveyQuestion.POMO)
        else -> emptyList()
    }


    private var questionIndex = 0

    // ----- Responses exposed as State -----


    private val _displayName = mutableStateOf<String?>(null)
    val displayName: String?
        get() = _displayName.value

    private val _name = mutableStateOf<String?>(null)
    val name: String?
        get() = _name.value

    private val _surname = mutableStateOf<String?>(null)
    val surname: String?
        get() = _surname.value

    private val _birthday = mutableStateOf<String?>(null)
    val birthday: String?
        get() = _birthday.value

    private val _avatar = mutableStateOf<String?>(null)
    val avatar: String?
        get() = _avatar.value

    private val _description = mutableStateOf<String?>(null)
    val description: String?
        get() = _description.value

    private val _selfieUri = mutableStateOf<Uri?>(null)
    val selfieUri
        get() = _selfieUri.value

    private val _bitmap = mutableStateOf<Bitmap?>(null)
    val bitmap
        get() = _bitmap.value

    private val _showWarningDialog = mutableStateOf<Boolean?>(false)
    val showWarningDialog: Boolean?
        get() = _showWarningDialog.value

    // ----- Survey status exposed as State -----

    private val _surveyScreenData = mutableStateOf(createSurveyScreenData())
    val surveyScreenData: SurveyScreenData?
        get() = _surveyScreenData.value

    private val _isNextEnabled = mutableStateOf(false)
    val isNextEnabled: Boolean
        get() = _isNextEnabled.value


    fun onBackPressed(): Boolean {
        if (questionIndex == 0) {
            return false
        }
        changeQuestion(questionIndex - 1)
        return true
    }

    fun onPreviousPressed() {
        if (questionIndex == 0) {
            throw IllegalStateException("onPreviousPressed when on question 0")
        }
        changeQuestion(questionIndex - 1)
    }

    fun onNextPressed() {
        changeQuestion(questionIndex + 1)
    }

    private fun changeQuestion(newQuestionIndex: Int) {
        questionIndex = newQuestionIndex
        _isNextEnabled.value = getIsNextEnabled()
        _surveyScreenData.value = createSurveyScreenData()
    }

    fun onDonePressed(
        context: Context, popUp: () -> Unit,
        restartApp: () -> Unit,
    ) {
        when (_editType.value) {
            PROFILE -> {
                saveAll(restartApp = restartApp)
            }
            PROFILE_PHOTO -> {
                profilePhotoBitmapSave(context = context, restartApp = restartApp)
            }
            DISPLAY_NAME -> {
                saveDisplayName(restartApp = restartApp)
            }
            NAME_SURNAME -> {
                saveNameSurname(restartApp = restartApp)
            }

            DESCRIPTION -> {
                saveDescription(restartApp = restartApp)
            }

            FEEDBACK -> {
                feedbackSave(popUp)
            }

            LANG -> {
                saveLang(popUp)
            }
            POMO -> { popUp() }

            DELETE -> {
                openDelete()
            }
        }
    }

    private fun saveAll(restartApp: () -> Unit) {
        launchCatching {
            accountService.displayName(_displayName.value ?: "")
            firestoreService.saveUser(
                User(
                    displayName = _displayName.value ?: "",
                    name = _name.value ?: "",
                    surname = _surname.value ?: "",
                    birthday = _birthday.value ?: "",
                    photo = _avatar.value ?: "",
                    description = _description.value ?: "",
                    language = _lang.value ?: DEFAULT_LANGUAGE_CODE,
                    online = true,
                    uid = uid,
                    date = Timestamp.now()
                )
            )
            restartApp()
        }
    }
    private fun profilePhotoBitmapSave(context: Context, restartApp: () -> Unit) {
        launchCatching {
            _selfieUri?.let {
                if (Build.VERSION.SDK_INT < 28) {
                    _bitmap.value =
                        MediaStore.Images.Media.getBitmap(context.contentResolver, it.value)
                    profilePhotoSave(restartApp)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, it.value!!)
                    _bitmap.value = ImageDecoder.decodeBitmap(source)
                    profilePhotoSave(restartApp)
                }
            }
        }
    }
    private fun profilePhotoSave(restartAppProfile: () -> Unit) {
        launchCatching {
            _bitmap.value?.let { bitmapNew ->
                val kucukBitmap = kucukBitmapOlustur(bitmapNew!!, 300)
                val outputStream = ByteArrayOutputStream()
                kucukBitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
                val byteDizisi = outputStream.toByteArray()
                val randomUid = UUID.randomUUID().toString()

                storageService.savePhoto(byteDizisi, uid = randomUid)
                val link = storageService.getPhoto(randomUid)
                firestoreService.updateUserProfilePhoto(link)
                restartAppProfile()
            }
        }
    }

    private fun saveDisplayName(restartApp: () -> Unit) {
        launchCatching {
            accountService.displayName(_displayName.value!!)
            firestoreService.updateUserDisplayName(newValue = _displayName.value!!)
            restartApp()
        }
    }

    private fun saveNameSurname(restartApp: () -> Unit) {
        launchCatching {
            firestoreService.updateUserName(newValue = _name.value!!)
            firestoreService.updateUserSurname(newValue = _surname.value!!)
            restartApp()
        }
    }
    private fun saveDescription(restartApp: () -> Unit) {
        launchCatching {
            firestoreService.updateUserDescription(newValue = _description.value!!)
            restartApp()
        }
    }

    private fun openDelete() {
        launchCatching {
            _showWarningDialog.value = true
        }
    }

    fun onDeleteMyAccountClick(
        restartApp: () -> Unit,
        empty_password_error: String
    ) {
        if (password.isBlank()) {
            //launchCatching { onShowSnackbar(empty_password_error, "") }
            return
        }

        launchCatching {
            accountService.authenticate(accountService.currentUserEmail, password)
            firestoreService.deleteAccount(
                Delete(
                    id = accountService.currentUserId,
                    email = accountService.currentUserEmail,
                    text = _description.value ?: ""
                )
            )
            accountService.deleteAccount()
            restartApp()
        }
    }

    private fun feedbackSave(popUp: () -> Unit) {
        launchCatching {
            firestoreService.saveFeedback(
                feedback = Feedback(
                    text = _description.value ?: "",
                    uid = accountService.currentUserId,
                    email = accountService.currentUserEmail,
                )
            )
            popUp()
        }
    }

    private fun saveLang(popUp: () -> Unit) {
        launchCatching {
            langRepository.saveLangState(_lang.value ?: DEFAULT_LANGUAGE_CODE)
            popUp()
        }
    }

    fun onDisplayNameChange(newValue: String) {
        _displayName.value = newValue
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onNameChange(newValue: String) {
        _name.value = newValue
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onSurnameChange(newValue: String) {
        _surname.value = newValue
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onAvatarChange(newValue: String) {
        _avatar.value = newValue
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onSelfieResponse(uri: Uri) {
        _selfieUri.value = uri
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onDescriptionChange(newValue: String) {
        _description.value = newValue
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onLangChange(newValue: String) {
        _lang.value = newValue
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun pomoIncrease(){ launchCatching { pomoService.increase() } }
    fun pomoDecrease(){ launchCatching { pomoService.decrease() } }


    private fun getIsNextEnabled(): Boolean {
        return when (questionOrder[questionIndex]) {
            SurveyQuestion.DISPLAY_NAME -> _displayName.value != null
            SurveyQuestion.NAME_SURNAME -> _name.value != null && _surname.value != null
            SurveyQuestion.AVATAR -> _avatar.value != null
            SurveyQuestion.DESCRIPTION -> _description.value != null
            SurveyQuestion.TAKE_SELFIE -> _selfieUri.value != null
            SurveyQuestion.DELETE -> _description.value != null
            SurveyQuestion.FEEDBACK -> _description.value != null
            SurveyQuestion.LANG -> _lang.value != null
            SurveyQuestion.POMO -> _pomo.value != null
        }
    }

    private fun createSurveyScreenData(): SurveyScreenData {
        return SurveyScreenData(
            questionIndex = questionIndex,
            questionCount = questionOrder.size,
            shouldShowPreviousButton = questionIndex > 0,
            shouldShowDoneButton = questionIndex == questionOrder.size - 1,
            surveyQuestion = questionOrder[questionIndex],
        )
    }
}

enum class SurveyQuestion {
    DISPLAY_NAME,
    NAME_SURNAME,
    AVATAR,
    DESCRIPTION,
    TAKE_SELFIE,
    DELETE,
    FEEDBACK,
    LANG,
    POMO
}

data class SurveyScreenData(
    val questionIndex: Int,
    val questionCount: Int,
    val shouldShowPreviousButton: Boolean,
    val shouldShowDoneButton: Boolean,
    val surveyQuestion: SurveyQuestion,
)


private fun kucukBitmapOlustur(kullanicininSectigiBitmap: Bitmap, maximumBoyut: Int) : Bitmap {
    var width = kullanicininSectigiBitmap.width
    var height = kullanicininSectigiBitmap.height

    val bitmapOrani : Double = width.toDouble() / height.toDouble()

    if (bitmapOrani > 1) {
        width = maximumBoyut
        val kisaltilmisHeight = width / bitmapOrani
        height = kisaltilmisHeight.toInt()
    } else {
        height = maximumBoyut
        val kisaltilmisWidth = height * bitmapOrani
        width = kisaltilmisWidth.toInt()
    }

    return Bitmap.createScaledBitmap(kullanicininSectigiBitmap,width,height,true)
}
