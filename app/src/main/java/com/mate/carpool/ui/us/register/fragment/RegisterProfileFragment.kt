package com.mate.carpool.ui.us.register.fragment

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.mate.carpool.R
import com.mate.carpool.ui.utils.SettingToolbarUtils
import com.mate.carpool.databinding.FragmentRegisterProfileBinding
import com.mate.carpool.ui.binder.BindFragment
import com.mate.carpool.ui.us.register.vm.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterProfileFragment : BindFragment<FragmentRegisterProfileBinding>(R.layout.fragment_register_profile) {
    val registerViewModel: RegisterViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner=viewLifecycleOwner
        binding.navController=Navigation.findNavController(view)

        val getImageResultCallback = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            val imgUri = it.data?.data?: return@registerForActivityResult
            Glide.with(this).load(imgUri).into(binding.imgProfile)
            registerViewModel.mutableUserModel.value?.studentProfile=absolutelyPath(imgUri,requireActivity())

        }

        binding.imgProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
            getImageResultCallback.launch(intent)
        }
        binding.btnConfirm.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_RegisterProfileFragment_to_RegisterSelectDayFragment)
        }

        SettingToolbarUtils.setActionBar(requireActivity(), binding.appbarBack)
    }

    fun absolutelyPath(path: Uri?, context : Context): String {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        var result = c?.getString(index!!)

        return result!!
    }
}