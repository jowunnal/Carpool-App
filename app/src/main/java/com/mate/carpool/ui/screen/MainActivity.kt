package com.mate.carpool.ui.screen

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.mate.carpool.R
import com.mate.carpool.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private var backPressedTime = 0L

    private val checkPermissionResultLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->

        if (result.any { permission -> permission.value.not() }) {
            Toast.makeText(applicationContext, "권한 동의가 필요합니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        checkPermissions()
        initTopBar()
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            checkPermissionResultLauncher.launch(REQUIRED_PERMISSIONS)
        }
    }

    private fun initTopBar() {
        setSupportActionBar(binding.toolBar)

        // set nav controller to action bar
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController


        binding.btnBack.setOnClickListener { navController.popBackStack() }
        navHostFragment.childFragmentManager.addOnBackStackChangedListener {
            binding.btnBack.isVisible = navHostFragment.childFragmentManager.backStackEntryCount > 0
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (isTaskRoot && navHostFragment.childFragmentManager.backStackEntryCount == 0) {
            if (System.currentTimeMillis() - backPressedTime >= EXIT_INTERVAL) {
                backPressedTime = System.currentTimeMillis()
                Snackbar.make(
                    binding.root,
                    "뒤로 가기 버튼을 한 번 더 누르면 종료됩니다.",
                    Snackbar.LENGTH_SHORT
                ).show()

            } else if (System.currentTimeMillis() - backPressedTime < EXIT_INTERVAL) { // 뒤로 가기 한번 더 눌렀을때의 시간간격 텀이 1초
                finishAffinity()
                System.runFinalization()
                exitProcess(0)
            }

        } else {
            super.onBackPressed()
        }
    }

    companion object {
        const val TAG = "MainActivity"
        const val EXIT_INTERVAL = 2000L

        val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
        )
    }
}