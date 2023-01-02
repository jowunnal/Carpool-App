package com.mate.carpool.ui.fragment.us2

import android.os.Bundle
import android.view.View
import androidx.databinding.ObservableField
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mate.carpool.R
import com.mate.carpool.data.model.domain.UserModel
import com.mate.carpool.data.vm.RegisterViewModel
import com.mate.carpool.databinding.FragmentRegisterInfoBinding
import com.mate.carpool.ui.activity.MainActivity
import com.mate.carpool.ui.adapter.us2.RegisterViewAdapter
import com.mate.carpool.ui.binder.BindFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterInfoFragment : BindFragment<FragmentRegisterInfoBinding>(R.layout.fragment_register_info){
    @Inject lateinit var studentInfoAdapter: RegisterViewAdapter
    val registerViewModel:RegisterViewModel by activityViewModels()
    lateinit var mainActivity:MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registerViewModel=registerViewModel
        binding.lifecycleOwner=viewLifecycleOwner
        studentInfoAdapter.setButton(binding)

        binding.rcvStudentInfo.apply {
            adapter=studentInfoAdapter
            layoutManager=LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)
        }

        // dialog 를 띄우기 위해 activityContext 필요하기 때문에 분리
        registerViewModel.rcvFlag.observe(viewLifecycleOwner, Observer {
            if(it==0){
                registerViewModel.clearRCVItems()

            }
            else if(it>=3){
                val items = studentInfoAdapter.getItems()
                registerViewModel.mutableUserModel.value=(UserModel(items[2],items[1],items[0],
                    ObservableField(),"","",null))
                registerViewModel.rcvFlag.value=0
                NavHostFragment.findNavController(this).navigate(R.id.action_RegisterInfoFragment_to_RegisterPhoneFragment)
            }
        })
        mainActivity = activity as MainActivity

        mainActivity.setOnBackPressedListener(object : MainActivity.OnBackPressedListener{
            override fun onBack() {
                registerViewModel.rcvFlag.value=0
                mainActivity.setOnBackPressedListener(null)
                mainActivity.onBackPressed()
            }
        })

        registerViewModel.rcvItemsLiveData.observe(viewLifecycleOwner, Observer {
            studentInfoAdapter.setItems(it)
            studentInfoAdapter.notifyDataSetChanged()
            binding.btnConfirm.isSelected=false
        })

        registerViewModel.studentNumberIsExistsHelperText.observe(viewLifecycleOwner, Observer {
            studentInfoAdapter.setStudentNumberIsExistsHelperText(it)
            studentInfoAdapter.notifyDataSetChanged()
        })
    }
}