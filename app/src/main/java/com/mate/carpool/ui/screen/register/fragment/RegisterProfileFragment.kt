package com.mate.carpool.ui.screen.register.fragment

import android.content.Intent
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.graphics.Color
import androidx.core.view.setPadding
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.mate.carpool.databinding.FragmentRegisterProfileBinding
import com.mate.carpool.ui.base.BaseFragment
import com.mate.carpool.ui.screen.register.vm.RegisterViewModel
import com.mate.carpool.ui.utils.FileUtils.getAbsolutelyFilePath
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterProfileFragment : BaseFragment<RegisterViewModel,FragmentRegisterProfileBinding>() {

    override val viewModel: RegisterViewModel by activityViewModels()

    private val getImageResultCallback = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
        val imgUri = it.data?.data ?: return@registerForActivityResult
        Glide.with(this).load(imgUri).into(binding.imgProfile)
        binding.imgProfile.setPadding(0)
        binding.imgProfile.background=null
        viewModel.profile.value = getAbsolutelyFilePath(imgUri,requireActivity())
    }

    override fun getViewBinding(): FragmentRegisterProfileBinding = FragmentRegisterProfileBinding.inflate(layoutInflater)

    override fun initViews() = with(binding){
        imgProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
            getImageResultCallback.launch(intent)
        }
    }

}