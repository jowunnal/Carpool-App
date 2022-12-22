package com.mate.carpool.ui.adapter

import android.util.Log
import android.widget.Button
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import androidx.navigation.NavController
import com.google.android.material.textfield.TextInputEditText
import com.mate.carpool.data.vm.RegisterViewModel

@BindingAdapter("isSelectedAddItemToRegisterRCV")
fun isSelectedAddItemToRegisterRCV(button:Button,registerViewModel:RegisterViewModel){
    button.setOnClickListener {
        if(button.isSelected){
            registerViewModel.addItemToRegisterRCV()
        }
    }
}

@BindingAdapter("navController","navi_ID")
fun isSelectedNavigate(button:Button,navController:NavController,@IdRes navi_ID:Int){
    button.setOnClickListener {
        if(button.isSelected){
            navController.navigate(navi_ID)
        }
    }
}
