package com.mate.carpool.ui.widget.adapter

import android.content.Context
import android.widget.*
import androidx.annotation.IdRes
import androidx.appcompat.widget.AppCompatButton
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.mate.carpool.R
import com.mate.carpool.data.model.item.*
import com.mate.carpool.ui.screen.createCarpool.vm.CreateTicketViewModel
import java.util.*

@BindingAdapter("navController", "navi_ID")
fun isButtonEnabledNavigate(button: AppCompatButton, navController: NavController, @IdRes navi_ID: Int) {
    button.setOnClickListener {
        if (button.isEnabled) {
            navController.navigate(navi_ID)
        }
    }
}

@BindingAdapter("updateText")
fun setUpdateStateFlowFromText(tv: EditText, updateText: (String) -> Unit) {
    tv.doOnTextChanged { text, start, before, count ->
        updateText(text.toString())
    }
}

@BindingAdapter("image", "context")
fun setProfileImage(imageView:ImageView, image:String, context:Context) {
    Glide
        .with(context)
        .load(image)
        .placeholder(R.drawable.ic_profile)
        .error(R.drawable.ic_profile)
        .into(imageView)
}

@BindingAdapter("areaItems", "context", "ticketViewModel")
fun setDropDownMenu(tv: AutoCompleteTextView, areaItems: List<String>, context: Context, ticketViewModel: CreateTicketViewModel) {
    tv.apply {
        setAdapter(ArrayAdapter(context, R.layout.itemview_drop_down_menu, areaItems))

        setOnItemClickListener { adapterView, view, i, l ->

            when (val selectedItem = adapterView.adapter.getItem(i).toString()) {
                "인동", "옥계", "대구", "기타" -> {
                    ticketViewModel.setStartArea(selectedItem)
                }

                "0", "1", "2", "3" -> {
                    ticketViewModel.setRecruitNumber(selectedItem)
                }
            }
        }
    }
}

