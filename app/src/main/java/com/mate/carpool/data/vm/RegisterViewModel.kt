package com.mate.carpool.data.vm

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.Result
import com.mate.carpool.data.model.*
import com.mate.carpool.data.repository.MemberRepository
import com.mate.carpool.data.service.APIService
import com.mate.carpool.ui.base.BaseViewModel
import com.mate.carpool.util.map
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.*
import java.io.File
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: APIService,
    private val memberRepository: MemberRepository,
) : BaseViewModel() {

    val name = MutableStateFlow("")
    val validNameInput: StateFlow<Boolean> = name.map(viewModelScope) { input ->
        input.length >= 2 && input.matches("^[ㄱ-ㅎ|가-힣]+".toRegex())
    }

    val studentId = MutableStateFlow("")
    val validStudentIdInput = studentId.map(viewModelScope) { input ->
        input.isNotEmpty() && input.matches("^[a-zA-Z0-9]+".toRegex())
    }
    private val _studentIdErrorMessage = MutableStateFlow("")
    val studentIdErrorMessage = _studentIdErrorMessage.asStateFlow()

    val department = MutableStateFlow("")
    val validDepartmentInput = department.map(viewModelScope) { input ->
        input.length >= 3 && input.matches("^[ㄱ-ㅎ|가-힣]+".toRegex())  // TODO 활성화 조건 확인
    }

    val phone = MutableStateFlow("")
    val validPhoneInput = phone.map(viewModelScope) { input ->
        input.length >= 12 && input.matches("^[0-9\\-]+".toRegex())
    }


    val mutableUserModel = MutableLiveData<UserModel>()
    val userModel get() = mutableUserModel

    val rcvFlag = MutableLiveData<Int>(0)
    var rcvItems: ArrayList<RegisterItem>? = null
    private val rcvItemsMutableLiveData: MutableLiveData<ArrayList<RegisterItem>> = MutableLiveData()
    val rcvItemsLiveData get() = rcvItemsMutableLiveData
    val studentNumberIsExistsHelperText: MutableLiveData<String> = MutableLiveData("")
    val loginFlag: MutableLiveData<Boolean> = MutableLiveData(false)

    fun clearRCVItems() {
        rcvItems?.clear()
        rcvItems = ArrayList<RegisterItem>().apply { add(RegisterItem(ObservableField("이름"), ObservableField(""), ObservableField(""))) }
        rcvItemsMutableLiveData.value = rcvItems
        studentNumberIsExistsHelperText.value = ""
    }

    fun addItemToRegisterRCV() {
        rcvFlag.value = rcvFlag.value?.plus(1)
        rcvItems?.reverse()
        when (rcvFlag.value) {

            1 -> {
                rcvItems?.add(RegisterItem(ObservableField("학번"), ObservableField(""), ObservableField("")))
            }

            2 -> {
                rcvItems?.add(RegisterItem(ObservableField("학과"), ObservableField(""), ObservableField("")))
            }
        }
        rcvItems?.reverse()
        rcvItemsMutableLiveData.value = rcvItems
    }


    fun checkIsStudentNumberExists() {
        memberRepository.checkIsDupStudentId(studentId = studentId.value)
            .onEach { result ->
                when (result) {
                    is Result.Loading -> {

                    }
                    is Result.Success -> {
                        _studentIdErrorMessage.update { "" }
                        emitEvent(EVENT_MOVE_TO_NEXT_STEP)
                    }
                    is Result.Error -> {
                        _studentIdErrorMessage.update { result.message }
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun convertInputStreamToFile(input: InputStream?): File? {
        val tempFile = File.createTempFile(java.lang.String.valueOf(input.hashCode()), ".tmp")
        tempFile.deleteOnExit()
        return tempFile
    }

    fun signUpStudentMember() {
        var file: File? = null
        when (File(userModel.value!!.studentProfile!!).mkdir()) {
            true -> {
                file = File(userModel.value!!.studentProfile!!)
            }

            false -> {
                val inputStream = context.assets.open("defaultImg.png")
                file = convertInputStreamToFile(inputStream)
            }
        }

        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file!!)
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

        viewModelScope.launch(Dispatchers.IO) {
            apiService.postSingUp(MemberRequestDTO(userModel.value!!),
                body
            ).enqueue(object : Callback<ResponseMessage> {
                override fun onResponse(
                    call: Call<ResponseMessage>,
                    response: Response<ResponseMessage>
                ) {
                    when (response.code()) {
                        200 -> {
                            Toast.makeText(context, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                        }

                        400 -> {
                            Toast.makeText(context, "회원가입 실패 : 값이 잘못 입력되었습니다.", Toast.LENGTH_SHORT).show()
                        }

                        409 -> {
                            Toast.makeText(context, "회원가입 실패 : 이미 존재하는 회원입니다.", Toast.LENGTH_SHORT).show()
                        }
                    }

                }

                override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                    Log.d("test", "실패: " + t.message)
                }

            })
        }

    }

    fun loginStudentMember(studentNumber: String, memberName: String, phoneNumber: String) {
        viewModelScope.launch {
            apiService.postLogin(StudentInfo(studentNumber, memberName, phoneNumber)).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            Toast.makeText(context, "로그인 성공! $studentNumber 님 환영합니다.", Toast.LENGTH_SHORT).show()
                            context.applicationContext.getSharedPreferences("accessToken", Context.MODE_PRIVATE).edit().putString("accessToken", response.body()?.accessToken).apply()
                            loginFlag.value = true
                        }

                        400 -> {
                            Toast.makeText(context, "로그인 실패 : 값이 잘못 입력되었습니다.", Toast.LENGTH_SHORT).show()
                        }

                        404 -> {
                            Toast.makeText(context, "로그인 실패 : 존재하지 않는 회원입니다.", Toast.LENGTH_SHORT).show()
                        }
                    }

                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("test", "실패: " + t.message)
                }
            })
        }
    }

    companion object {
        const val EVENT_MOVE_TO_NEXT_STEP = "EVENT_MOVE_TO_NEXT_STEP"
    }
}