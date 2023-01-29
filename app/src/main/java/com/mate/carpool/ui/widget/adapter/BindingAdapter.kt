package com.mate.carpool.ui.widget.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toolbar
import androidx.annotation.IdRes
import androidx.appcompat.widget.AppCompatButton
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.google.android.material.textfield.TextInputEditText
import com.mate.carpool.R
import com.mate.carpool.data.model.domain.item.*
import com.mate.carpool.ui.screen.createCarpool.vm.CreateTicketViewModel
import java.util.*

@BindingAdapter("navController", "navi_ID")
fun isButtonSelectedNavigate(button: AppCompatButton, navController: NavController, @IdRes navi_ID: Int) {
    button.setOnClickListener {
        if (button.isSelected) {
            navController.navigate(navi_ID)
        }
    }
}

@BindingAdapter("navController")
fun navigateBackStackOnToolbar(toolbar: Toolbar, navController: NavController) {
    toolbar.setNavigationOnClickListener {
        navController.popBackStack()
    }
}

@BindingAdapter("areaItems", "context", "ticketViewModel")
fun setAreaItemsToStartingAreaAdapter(tv: AutoCompleteTextView, areaItems: ArrayList<String>, context: Context, ticketViewModel: CreateTicketViewModel) {
    tv.apply {
        setAdapter(ArrayAdapter(context, R.layout.itemview_drop_down_menu, areaItems))
        setOnItemClickListener { adapterView, view, i, l ->
            val selectedItem = adapterView.adapter.getItem(i).toString()

            when (selectedItem) {
                "인동", "옥계", "대구", "그 외", "광운대학교" -> {
                    ticketViewModel.mutableTicketModel.value!!.startArea = selectedItem
                    setTicketButtonSelected("옥", ticketViewModel.boardingAreaButtonFlag, 0)
                }

                "오전" -> {
                    ticketViewModel.mutableTicketModel.value!!.dayStatus= DayStatus.Morning
                    setTicketButtonSelected(" ", ticketViewModel.boardingTimeButtonFlag, 1)
                }

                "오후" -> {
                    ticketViewModel.mutableTicketModel.value!!.dayStatus= DayStatus.Afternoon
                    setTicketButtonSelected(" ", ticketViewModel.boardingTimeButtonFlag, 1)
                }

                "0", "1", "2", "3" -> {
                    ticketViewModel.mutableTicketModel.value!!.recruitPerson = selectedItem.toInt()
                    setTicketButtonSelected(" ", ticketViewModel.openChatButtonFlag, 1)
                }
                "유료"-> {
                    ticketViewModel.mutableTicketModel.value!!.ticketType=TicketType.Cost
                    setTicketButtonSelected(" ",ticketViewModel.openChatButtonFlag,2)
                }
                "무료"->{
                    ticketViewModel.mutableTicketModel.value!!.ticketType=TicketType.Free
                    setTicketButtonSelected(" ",ticketViewModel.openChatButtonFlag,2)
                }
            }

        }
    }

}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("ticketViewModel","buttonFlag","context")
fun setBoardingAreaTextInputListener(et: TextInputEditText, ticketViewModel: CreateTicketViewModel, buttonFlag: MutableLiveData<ArrayList<Boolean>>, context: Context){
    et.doOnTextChanged { text, start, before, count ->

        when (et.hint) {
            context.getString(R.string.탑승장소) -> {
                ticketViewModel.mutableTicketModel.value?.boardingPlace = text.toString()
                setTicketButtonSelected(text.toString(), buttonFlag, 1)
            }

            context.getString(R.string.출발날짜) -> {
                ticketViewModel.mutableTicketModel.value?.startDayMonth = text.toString()
                setTicketButtonSelected(text.toString(), buttonFlag, 0)
            }

            context.getString(R.string.출발시간) -> {
                ticketViewModel.mutableTicketModel.value?.startTime = text.toString()
                setTicketButtonSelected(text.toString(), buttonFlag, 2)
            }

            context.getString(R.string.오픈채팅방링크) -> {
                ticketViewModel.mutableTicketModel.value?.openChatUrl = text.toString()
                setTicketButtonSelected(text.toString(), buttonFlag, 0)
            }
        }
    }
}

fun setTicketButtonSelected(text: String, buttonFlag: MutableLiveData<ArrayList<Boolean>>, num: Int) {
    val arrayList = buttonFlag.value
    if (text == "") {
        arrayList?.set(num, false)
    } else
        arrayList?.set(num, true)
    buttonFlag.value = arrayList
}

@BindingAdapter("ticketType")
fun setTicketType(textView:TextView,ticketType: TicketType?){
    textView.text = ticketType?.getTicketType()?:""
}


