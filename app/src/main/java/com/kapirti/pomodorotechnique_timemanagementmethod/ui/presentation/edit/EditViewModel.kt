package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.edit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.Timestamp
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.Cons.DEFAULT_COUNTRY
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.COUNTRY
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.DELETE
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.DESCRIPTION
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.DISPLAY_NAME
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.FEEDBACK
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.JOB
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.POMO
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.PROFILE
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.PROFILE_PHOTO
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.CountryRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.EditTypeRepository
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
    private val countryRepository: CountryRepository,
    private val pomoService: PomoService,
    logService: LogService,
): PomodoroViewModel(logService) {
    val uid = accountService.currentUserId

    private val _editType = mutableStateOf<String?>(null)
    val editType: String?
        get() = _editType.value

    private val _country = mutableStateOf<String?>(null)
    val country: String?
        get() = _country.value

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
                countryRepository.readCountryState().collect { itLang ->
                    _country.value = itLang
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
            SurveyQuestion.DESCRIPTION,
        )
        PROFILE_PHOTO -> listOf(SurveyQuestion.TAKE_SELFIE)
        DISPLAY_NAME -> listOf(SurveyQuestion.DISPLAY_NAME)
        DESCRIPTION -> listOf(SurveyQuestion.DESCRIPTION)

        JOB -> listOf()

        DELETE -> listOf(SurveyQuestion.DELETE)
        FEEDBACK -> listOf(SurveyQuestion.FEEDBACK)
        COUNTRY -> listOf(SurveyQuestion.COUNTRY)
        POMO -> listOf(SurveyQuestion.POMO)
        else -> emptyList()
    }


    private var questionIndex = 0

    // ----- Responses exposed as State -----


    private val _displayName = mutableStateOf<String?>(null)
    val displayName: String?
        get() = _displayName.value

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

            DESCRIPTION -> {
                saveDescription(restartApp = restartApp)
            }

            FEEDBACK -> {
                feedbackSave(popUp)
            }

            COUNTRY -> {
                saveCountry(popUp)
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
                    description = _description.value ?: "",
                    country = _country.value ?: DEFAULT_COUNTRY,
                    online = true,
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

    private fun saveCountry(popUp: () -> Unit) {
        launchCatching {
            countryRepository.saveCountryState(_country.value ?: DEFAULT_COUNTRY)
            popUp()
        }
    }

    fun onDisplayNameChange(newValue: String) {
        _displayName.value = newValue
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

    fun onCountryChange(newValue: String) {
        _country.value = newValue
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun pomoIncrease(){ launchCatching { pomoService.increase() } }
    fun pomoDecrease(){ launchCatching { pomoService.decrease() } }


    private fun getIsNextEnabled(): Boolean {
        return when (questionOrder[questionIndex]) {
            SurveyQuestion.DISPLAY_NAME -> _displayName.value != null
            SurveyQuestion.DESCRIPTION -> _description.value != null
            SurveyQuestion.TAKE_SELFIE -> _selfieUri.value != null

            SurveyQuestion.DELETE -> _description.value != null
            SurveyQuestion.FEEDBACK -> _description.value != null

            SurveyQuestion.COUNTRY -> _country.value != null
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
    DESCRIPTION,
    TAKE_SELFIE,

    COUNTRY,

    DELETE,
    FEEDBACK,

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
