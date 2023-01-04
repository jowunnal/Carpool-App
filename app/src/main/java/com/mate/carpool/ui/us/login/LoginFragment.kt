package com.mate.carpool.ui.us.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation
import com.mate.carpool.R
import com.mate.carpool.databinding.FragmentLoginBinding
import com.mate.carpool.ui.activity.MainActivity
import com.mate.carpool.ui.binder.BindFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BindFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private var backPressedTime = 0L
    lateinit var mainActivity:MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner=viewLifecycleOwner
        binding.navController=Navigation.findNavController(view)
        mainActivity = activity as MainActivity

        mainActivity.setOnBackPressedListener(object : MainActivity.OnBackPressedListener{
            override fun onBack() {
                if (backPressedTime == 0L) {
                    Toast.makeText(
                        requireActivity(),
                        " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG
                    ).show()
                    backPressedTime = System.currentTimeMillis()
                } else {
                    val seconds = (System.currentTimeMillis() - backPressedTime)
                    backPressedTime = if (seconds < 2000) {
                        mainActivity.setOnBackPressedListener(null)
                        mainActivity.onBackPressed()
                        0L
                    } else {
                        0L
                    }
                }
                mainActivity.setOnBackPressedListener(null)
            }
        })

        /*binding.btnConfirm.setOnClickListener {
            ReserveDriverFragment().show(requireActivity().supportFragmentManager,"bottom sheet")
            reserveDriverViewModel.getTicketDetailFromId()
        }*/
    }

}