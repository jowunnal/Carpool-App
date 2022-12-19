package com.mate.carpool.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mate.carpool.data.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() :ViewModel() {
    val mutableUserModel = MutableLiveData<UserModel>()
    val userModel get() = mutableUserModel
    val rcvFlag = MutableLiveData<Int>(0)


}