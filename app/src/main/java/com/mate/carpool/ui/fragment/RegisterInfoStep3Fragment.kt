package com.mate.carpool.ui.fragment

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mate.carpool.data.vm.RegisterViewModel
import com.mate.carpool.databinding.FragmentRegisterInfoStep3Binding
import com.mate.carpool.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterInfoStep3Fragment : BaseFragment<RegisterViewModel, FragmentRegisterInfoStep3Binding>() {

    override val viewModel: RegisterViewModel by activityViewModels()

    override fun getViewBinding() = FragmentRegisterInfoStep3Binding.inflate(layoutInflater)

    override fun subscribeUI() {

    }

    override fun initViews() = with(binding) {
        viewModel = this@RegisterInfoStep3Fragment.viewModel
        navController = findNavController()
    }

//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//        // dialog 를 띄우기 위해 activityContext 필요하기 때문에 분리
//        registerViewModel.rcvFlag.observe(viewLifecycleOwner, Observer {
//            if (it == 0) {
//                registerViewModel.clearRCVItems()
//
//            } else if (it >= 3) {
//                val items = studentInfoAdapter.getItems()
//                registerViewModel.mutableUserModel.value = (UserModel(items[2], items[1], items[0],
//                    ObservableField(), "", "", null))
//                registerViewModel.rcvFlag.value = 0
//                NavHostFragment.findNavController(this).navigate(R.id.action_RegisterInfoFragment_to_RegisterPhoneFragment)
//            }
//        })
//        mainActivity = activity as MainActivity
//
//        //        mainActivity.setOnBackPressedListener(object : MainActivity.OnBackPressedListener{
//        //            override fun onBack() {
//        //                registerViewModel.rcvFlag.value=0
//        //                mainActivity.setOnBackPressedListener(null)
//        //                mainActivity.onBackPressed()
//        //            }
//        //        })
//
//        registerViewModel.rcvItemsLiveData.observe(viewLifecycleOwner, Observer {
//            studentInfoAdapter.setItems(it)
//            studentInfoAdapter.notifyDataSetChanged()
//            binding.btnConfirm.isSelected = false
//        })
//
//        registerViewModel.studentNumberIsExistsHelperText.observe(viewLifecycleOwner, Observer {
//            studentInfoAdapter.setStudentNumberIsExistsHelperText(it)
//            studentInfoAdapter.notifyDataSetChanged()
//        })
//    }
}