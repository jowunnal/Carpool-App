package com.mate.carpool.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mate.carpool.R
import com.mate.carpool.data.model.UserModel
import com.mate.carpool.data.vm.RegisterViewModel
import com.mate.carpool.databinding.FragmentRegisterInfoBinding
import com.mate.carpool.ui.adapter.RegisterViewAdapter
import com.mate.carpool.ui.binder.BindFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterInfoFragment : BindFragment<FragmentRegisterInfoBinding>(R.layout.fragment_register_info) {
    @Inject lateinit var studentInfoAdapter:RegisterViewAdapter
    val registerViewModel:RegisterViewModel by activityViewModels()
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registerViewModel=registerViewModel
        binding.lifecycleOwner=this
        studentInfoAdapter.setButton(binding)

        binding.rcvStudentInfo.apply {
            adapter=studentInfoAdapter
            layoutManager=LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)
        }

        // dialog 를 띄우기 위해 activityContext 필요하기 때문에 분리
        registerViewModel.rcvFlag.observe(viewLifecycleOwner, Observer {
            if(it>=3){
                CheckDialogFragment().show(requireActivity().supportFragmentManager,"대화상자")
                val items = studentInfoAdapter.getItems()
                registerViewModel.mutableUserModel.value=(UserModel(items[2],items[1],items[0],
                    ObservableField(),"","",null))
                Log.d("test",registerViewModel.userModel.value.toString())
            }
        })

        registerViewModel.rcvItemsLiveData.observe(viewLifecycleOwner, Observer {
            studentInfoAdapter.setItems(it)
            studentInfoAdapter.notifyDataSetChanged()
            binding.btnConfirm.isSelected=false
        })
    }
}