package com.mate.carpool.ui.screen.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.mate.carpool.R
import com.mate.carpool.databinding.ActivitySplashBinding
import com.mate.carpool.ui.screen.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        subscribeUi()
    }

    private fun subscribeUi() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.event.collect { event ->
                    when (event.type) {
                        SplashViewModel.EVENT_GO_TO_LOGIN_SCREEN -> {
                            moveToLoginScreen()
                        }

                        SplashViewModel.EVENT_GO_TO_HOME_SCREEN -> {
                            moveToHomeScreen()
                        }
                    }
                }
            }
            launch {
                viewModel.snackbarMessage.collect{
                    Toast.makeText(this@SplashActivity,it,Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun moveToLoginScreen() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
        finish()
    }

    private fun moveToHomeScreen() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.putExtra(MainActivity.KEY_SUCCESS_AUTO_LOGIN, true)
        startActivity(intent)
        finish()
    }
}