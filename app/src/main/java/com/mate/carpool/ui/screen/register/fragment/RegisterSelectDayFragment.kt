package com.mate.carpool.ui.screen.register.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mate.carpool.R
import com.mate.carpool.data.model.DTO.MemberTimeTableResponseDTO
import com.mate.carpool.data.model.domain.item.WeekItem
import com.mate.carpool.ui.utils.SettingToolbarUtils
import com.mate.carpool.databinding.FragmentRegisterSelectDayBinding
import com.mate.carpool.ui.screen.register.WeekViewAdapter
import com.mate.carpool.ui.base.BindFragment
import com.mate.carpool.ui.screen.register.vm.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterSelectDayFragment : BindFragment<FragmentRegisterSelectDayBinding>(R.layout.fragment_register_select_day) {
    @Inject lateinit var weekViewAdapter: WeekViewAdapter
    val registerViewModel: RegisterViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner=viewLifecycleOwner
        binding.navController = Navigation.findNavController(view)

        binding.rcvWeek.apply {
            adapter=weekViewAdapter.apply {
                setItems(arrayListOf(
                WeekItem("월",false),
                WeekItem("화",false),
                WeekItem("수",false),
                WeekItem("목",false),
                WeekItem("금",false)
                ))
                notifyDataSetChanged()
            }
            layoutManager=LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
        }

        binding.btnConfirm.setOnClickListener {
            val mutableList = arrayListOf<String>()
            for(item in weekViewAdapter.getItems()){
                if(item.weekFlag){
                    mutableList.add((weekViewAdapter.getItems().indexOf(item)+1).toString())
                }
            }
            registerViewModel.mutableUserModel.value!!.studentDayCodes = mutableList.toList()
            Navigation.findNavController(view).navigate(R.id.action_RegisterSelectDayFragment_to_loginFragment)
            registerViewModel.signUpStudentMember()
        }

        SettingToolbarUtils.setActionBar(requireActivity(), binding.appbarBack)
    }
}