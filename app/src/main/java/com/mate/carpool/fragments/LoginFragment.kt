package com.mate.carpool.fragments

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mate.carpool.R

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnLogin = view.findViewById<Button>(R.id.btn_confirm)

        btnLogin.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToLoginStartFragment1())
        }

        val loginString = "로그인 하기"
        val content = SpannableString(loginString)
        content.setSpan(UnderlineSpan(), 0, loginString.length, 0)

        view.findViewById<TextView>(R.id.text_login_start).text = content

        val layoutLoginStart = view.findViewById<LinearLayout>(R.id.layout_login_start)

        layoutLoginStart.setOnClickListener {
//            findNavController().navigate()
        }
    }

}