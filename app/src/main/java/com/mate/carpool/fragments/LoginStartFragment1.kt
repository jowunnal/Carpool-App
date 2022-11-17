package com.mate.carpool.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mate.carpool.R

class LoginStartFragment1 : Fragment() {

//    private val args: LoginStartFragment1Args by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_start1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val btnLogin = view.findViewById<Button>(R.id.btn_confirm)
        btnLogin.setOnClickListener {
            val name = view.findViewById<EditText>(R.id.edit_name)
//            args.userInfo.name = name.toString()

            findNavController().navigate(
                LoginStartFragment1Directions.actionLoginStartFragment1ToLoginStartFragment2(
//                    args.userInfo
                )
            )
        }
    }
}