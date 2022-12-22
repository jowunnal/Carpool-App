package com.mate.carpool.data.vm

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mate.carpool.data.model.RegisterItem
import com.mate.carpool.data.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(@ApplicationContext private val context:Context) :ViewModel() {
    val mutableUserModel = MutableLiveData<UserModel>()
    val userModel get() = mutableUserModel
    val rcvFlag = MutableLiveData<Int>(0)
    val rcvItems:ArrayList<RegisterItem> = ArrayList<RegisterItem>().apply{add(RegisterItem(ObservableField("이름"), ObservableField("")))}
    private val rcvItemsMutableLiveData : MutableLiveData<ArrayList<RegisterItem>> = MutableLiveData(rcvItems)
    val rcvItemsLiveData get() = rcvItemsMutableLiveData

    fun addItemToRegisterRCV(){
        rcvFlag.value = rcvFlag.value?.plus(1)
        rcvItems.reverse()
        when(rcvFlag.value){
            1->{
                rcvItems.add(RegisterItem(ObservableField("학번"),ObservableField("")))
            }
            2->{
                rcvItems.add(RegisterItem(ObservableField("학과"),ObservableField("")))
            }
        }
        rcvItems.reverse()
        rcvItemsMutableLiveData.value = rcvItems
    }



}