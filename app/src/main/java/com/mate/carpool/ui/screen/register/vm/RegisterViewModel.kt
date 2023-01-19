package com.mate.carpool.ui.screen.register.vm

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.Result
import com.mate.carpool.data.model.domain.UserModel
import com.mate.carpool.data.model.domain.item.RegisterItem
import com.mate.carpool.data.model.domain.item.StudentItem
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.model.response.LoginResponse
import com.mate.carpool.data.repository.MemberRepository
import com.mate.carpool.data.repository.RegisterRepository
import com.mate.carpool.data.service.APIService
import com.mate.carpool.ui.base.BaseViewModel
import com.mate.carpool.util.map
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: APIService,
    private val memberRepository: MemberRepository,
    private val registerRepository: RegisterRepository
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


    val mutableUserModel = MutableLiveData<UserModel>(UserModel())
    val userModel: LiveData<UserModel> get() = mutableUserModel

    val loginFlag: MutableLiveData<Boolean> = MutableLiveData(false)

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
        when (File(userModel.value!!.profile).mkdir()) {
            true -> {
                file = File(userModel.value!!.profile)
            }

            false -> {
                val inputStream = context.assets.open("defaultImg.png")
                file = convertInputStreamToFile(inputStream)
            }
        }

        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file!!)
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

        viewModelScope.launch {
            registerRepository.signUp(
                userModel.value!!,
                body
            ).collectLatest {
                when (it) {
                    is ApiResponse.Loading -> {
                    }
                    is ApiResponse.SuccessResponse -> {
                        Toast.makeText(context, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                    is ApiResponse.FailResponse -> {
                        when (it.responseMessage.code) {
                            "400" -> {
                                Toast.makeText(
                                    context,
                                    "회원가입 실패 : 값이 잘못 입력되었습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            "409" -> {
                                Toast.makeText(
                                    context,
                                    "회원가입 실패 : 이미 존재하는 회원입니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                    is ApiResponse.ExceptionResponse -> {
                        Toast.makeText(context, "에러 발생 : ${it.e.message}", Toast.LENGTH_SHORT)
                            .show()
                        Log.d("test", "에러 발생 : ${it.e.message}")
                    }
                }
            }
        }
    }

    fun loginStudentMember(studentNumber: String, memberName: String, phoneNumber: String) {
        viewModelScope.launch {
            apiService.postLogin(StudentItem(studentNumber, memberName, phoneNumber))
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        when (response.code()) {
                            200 -> {
                                Toast.makeText(
                                    context,
                                    "로그인 성공! $studentNumber 님 환영합니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                context.applicationContext.getSharedPreferences(
                                    "accessToken",
                                    Context.MODE_PRIVATE
                                ).edit().putString("accessToken", response.body()?.accessToken)
                                    .apply()
                                loginFlag.value = true
                            }

                            400 -> {
                                Toast.makeText(
                                    context,
                                    "로그인 실패 : 값이 잘못 입력되었습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            404 -> {
                                Toast.makeText(
                                    context,
                                    "로그인 실패 : 존재하지 않는 회원입니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
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