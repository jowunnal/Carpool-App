package com.mate.carpool.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mate.carpool.R

class RegisterFragment4 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register4, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnNext = view.findViewById<Button>(R.id.btn_confirm)
        btnNext.setOnClickListener {
            findNavController().navigate(RegisterFragment4Directions.actionRegisterFragment4ToRegisterTypeFragment())
        }

        val numberEdit = view.findViewById<EditText>(R.id.edit_number)
        val confirmButton = view.findViewById<Button>(R.id.btn_confirm)
        var phoneNumber = ""

//        confirmButton.isEnabled = false

        numberEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                confirmButton.isEnabled = numberEdit.length() >= 4

            }

            override fun afterTextChanged(p0: Editable?) {}

        })


    }
}