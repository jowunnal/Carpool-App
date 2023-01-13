package com.mate.carpool.ui.us.register.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mate.carpool.R
import com.mate.carpool.data.model.DTO.MemberTimeTableResponseDTO
import com.mate.carpool.data.model.domain.item.WeekItem
import com.mate.carpool.ui.utils.SettingToolbarUtils
import com.mate.carpool.databinding.FragmentRegisterSelectDayBinding
import com.mate.carpool.ui.us.register.WeekViewAdapter
import com.mate.carpool.ui.binder.BindFragment
import com.mate.carpool.ui.us.register.vm.RegisterViewModel
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
            registerViewModel.mutableUserModel.value?.studentDayCodes = arrayListOf()
            for(item in weekViewAdapter.getItems()){
                if(item.weekFlag){
                    registerViewModel.mutableUserModel.value?.studentDayCodes =
                        listOf(MemberTimeTableResponseDTO((weekViewAdapter.getItems().indexOf(item)+1).toString()).dayCode)
                }
            }
            Navigation.findNavController(view).navigate(R.id.action_RegisterSelectDayFragment_to_loginFragment)
            registerViewModel.signUpStudentMember()
        }

        SettingToolbarUtils.setActionBar(requireActivity(), binding.appbarBack)
    }
}