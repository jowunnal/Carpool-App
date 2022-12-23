package com.mate.carpool.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mate.carpool.R
import com.mate.carpool.data.viewmodel.RegisterViewModel
import com.mate.carpool.databinding.FragmentRegisterInfoBinding
import com.mate.carpool.ui.adapter.RegisterViewAdapter
import com.mate.carpool.ui.binder.BindFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterInfoFragment : BindFragment<FragmentRegisterInfoBinding>(R.layout.fragment_register_info) {
    @Inject lateinit var studentInfoAdapter:RegisterViewAdapter
    val registerViewModel:RegisterViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnConfirm.setOnClickListener {
            registerViewModel.rcvFlag.value = registerViewModel.rcvFlag.value?.plus(1)
        }

        binding.rcvStudentInfo.apply {
            adapter=studentInfoAdapter
            layoutManager=LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)
        }

        registerViewModel.rcvFlag.observe(viewLifecycleOwner, Observer {
            when(it){
                0->{
                    studentInfoAdapter.addItem(hashMapOf<String, String>().apply {
                        this["kind"]="이름"
                    })
                }
                1->{
                    studentInfoAdapter.addItem(hashMapOf<String, String>().apply {
                        this["kind"]="학번"
                    })
                }
                2->{
                    studentInfoAdapter.addItem(hashMapOf<String, String>().apply {
                        this["kind"]="학과"
                    })
                }
                else->{
                    CheckDialogFragment().show(requireActivity().supportFragmentManager,"대화상자")
                }
            }
            studentInfoAdapter.notifyDataSetChanged()
        })

    }
}