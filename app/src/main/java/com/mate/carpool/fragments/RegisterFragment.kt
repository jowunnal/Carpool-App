package com.mate.carpool.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mate.carpool.R

class RegisterFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnRegister = view.findViewById<Button>(R.id.btn_register)
        btnRegister.setOnClickListener {
            showDialog(it.context)
        }
    }

    private fun showDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle("입력하신 정보가 정확한가요?")
            .setMessage("한번 입력하신 정보는 수정 및 변경 불가합니다. 정확히 확인 후 입력해주세요!")
            .setPositiveButton("확인") { _: DialogInterface, _ ->
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToRegisterDetailFragment())
            }
            .setNegativeButton("수정") { dialog: DialogInterface, _ -> dialog.dismiss() }
            .show()
    }
}