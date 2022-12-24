package com.mate.carpool.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mate.carpool.R
import com.mate.carpool.data.model.MemberTimetableRequestDTO
import com.mate.carpool.data.model.WeekModel
import com.mate.carpool.data.vm.RegisterViewModel
import com.mate.carpool.databinding.FragmentRegisterSelectDayBinding
import com.mate.carpool.ui.adapter.WeekViewAdapter
import com.mate.carpool.ui.binder.BindFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterSelectDayFragment : BindFragment<FragmentRegisterSelectDayBinding>(R.layout.fragment_register_select_day) {
    @Inject lateinit var weekViewAdapter: WeekViewAdapter
    val registerViewModel:RegisterViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner=this

        binding.rcvWeek.apply {
            adapter=weekViewAdapter.apply {
                setItems(arrayListOf(
                WeekModel("월",false),
                WeekModel("화",false),
                WeekModel("수",false),
                WeekModel("목",false),
                WeekModel("금",false)))
                notifyDataSetChanged()
            }
            layoutManager=LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
        }

        binding.btnConfirm.setOnClickListener {
            registerViewModel.mutableUserModel.value?.studentDayCodes = arrayListOf()
            for(item in weekViewAdapter.getItems()){
                if(item.weekFlag){
                    registerViewModel.mutableUserModel.value?.studentDayCodes?.add(
                        MemberTimetableRequestDTO((weekViewAdapter.getItems().indexOf(item)+1).toString()))
                }
            }
            Navigation.findNavController(view).navigate(R.id.action_RegisterSelectDayFragment_to_loginFragment)
            registerViewModel.signUpStudentMember()
        }
    }
}