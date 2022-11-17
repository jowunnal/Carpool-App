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
import com.mate.carpool.R

class LoginStartFragment3 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_start3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnNext = view.findViewById<Button>(R.id.btn_confirm)
        btnNext.setOnClickListener {
            showDialog(it.context)
        }
    }

    private fun showDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle("입력하신 정보가 정확한가요?")
            .setMessage("이름,학번과 학과는 추후 수정이 불가능합니다. \n다음으로 넘어가시겠어요?")
            .setPositiveButton("확인") { _: DialogInterface, _ ->
//                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToRegisterDetailFragment())
            }
            .setNegativeButton("취소") { dialog: DialogInterface, _ -> dialog.dismiss() }
            .show()
    }
}