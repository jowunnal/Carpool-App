package com.mate.carpool.data.vm

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.model.*
import com.mate.carpool.data.service.APIService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(@ApplicationContext private val context:Context,private val apiService: APIService) :ViewModel() {
    val mutableUserModel = MutableLiveData<UserModel>()
    val userModel get() = mutableUserModel
    val rcvFlag = MutableLiveData<Int>(0)
    var rcvItems:ArrayList<RegisterItem> ?= null
    private val rcvItemsMutableLiveData : MutableLiveData<ArrayList<RegisterItem>> = MutableLiveData()
    val rcvItemsLiveData get() = rcvItemsMutableLiveData

    fun clearRCVItems(){
        rcvItems?.clear()
        rcvItems = ArrayList<RegisterItem>().apply{add(RegisterItem(ObservableField("이름"), ObservableField("")))}
        rcvItemsMutableLiveData.value = rcvItems
    }
    fun addItemToRegisterRCV(){
        rcvFlag.value = rcvFlag.value?.plus(1)
        rcvItems?.reverse()
        when(rcvFlag.value){
            1->{
                rcvItems?.add(RegisterItem(ObservableField("학번"),ObservableField("")))
            }
            2->{
                rcvItems?.add(RegisterItem(ObservableField("학과"),ObservableField("")))
            }
        }
        rcvItems?.reverse()
        rcvItemsMutableLiveData.value = rcvItems
    }

    fun signUpStudentMember(){
        viewModelScope.launch(Dispatchers.IO) {

            apiService.postSingUp(MemberRequestDTO(userModel.value!!),null).enqueue(object : Callback<ResponseMessage> {
                override fun onResponse(
                    call: Call<ResponseMessage>,
                    response: Response<ResponseMessage>
                ) {
                    when(response.code()){
                        200->{
                            Toast.makeText(context,"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show()
                        }
                        400->{
                            Toast.makeText(context,"회원가입 실패 : 값 입력 오류",Toast.LENGTH_SHORT).show()
                        }
                        409->{
                            Toast.makeText(context,"회원가입 실패 : 이미 존재하는 회원",Toast.LENGTH_SHORT).show()
                        }
                    }

                }

                override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                    Log.d("test","실패: "+ t.message)
                }

            })
        }
    }

    fun loginStudentMember(studentNumber:String,memberName:String,phoneNumber:String){
        viewModelScope.launch {
            apiService.postLogin(StudentInfo(studentNumber,memberName,phoneNumber)).enqueue(object : Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    when(response.code()){
                        200->{
                            Toast.makeText(context,"로그인 성공",Toast.LENGTH_SHORT).show()
                            context.applicationContext.getSharedPreferences("accessToken",Context.MODE_PRIVATE).edit().putString("accessToken",response.body()?.accessToken).apply()
                        }
                        400->{
                            Toast.makeText(context,"로그인 실패 : 값 입력 오류",Toast.LENGTH_SHORT).show()
                        }
                        404->{
                            Toast.makeText(context,"로그인 실패 : 존재하지 않는 회원",Toast.LENGTH_SHORT).show()
                        }
                    }

                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("test","실패: "+ t.message)
                }

            })
        }
    }



}