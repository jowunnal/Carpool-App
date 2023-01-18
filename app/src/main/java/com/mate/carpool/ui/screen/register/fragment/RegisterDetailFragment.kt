package com.mate.carpool.ui.screen.register.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.mate.carpool.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnConfirm = view.findViewById<Button>(R.id.btn_confirm)
        btnConfirm.setOnClickListener {
//            findNavController().navigate(RegisterDetailFragmentDirections.actionRegisterDetailFragmentToMainFragment())
        }

    }
}