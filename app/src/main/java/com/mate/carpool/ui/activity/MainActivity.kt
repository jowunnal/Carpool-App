package com.mate.carpool.ui.activity

import android.Manifest
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.mate.carpool.R
import com.mate.carpool.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
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
        checkPermissionResultLauncher.launch(REQUIRED_PERMISSIONS)
    }

    private fun initTopBar() {
        setSupportActionBar(binding.toolBar)

        // set nav controller to action bar
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    //    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
    //        val focusView: View? = currentFocus
    //        if (focusView != null) {
    //            val rect = Rect()
    //            focusView.getGlobalVisibleRect(rect)
    //            val x = ev.x.toInt()
    //            val y = ev.y.toInt()
    //            if (!rect.contains(x, y)) {
    //                hideKeyboard()
    //            }
    //        }
    //        return super.dispatchTouchEvent(ev)
    //    }

    //    override fun onBackPressed() {
    //        if (isTaskRoot && supportFragmentManager.backStackEntryCount == 0) {
    //            if (System.currentTimeMillis() - backPressedTime >= EXIT_INTERVAL) {
    //                backPressedTime = System.currentTimeMillis()
    //                Toast.makeText(
    //                    this,
    //                    "뒤로 가기 버튼을 한 번 더 누르면 종료됩니다.",
    //                    Toast.LENGTH_SHORT
    //                ).show()
    //
    //            } else if (System.currentTimeMillis() - backPressedTime < EXIT_INTERVAL) { // 뒤로 가기 한번 더 눌렀을때의 시간간격 텀이 1초
    //                finishAffinity()
    //                System.runFinalization()
    //                exitProcess(0)
    //            }
    //
    //        } else {
    //            super.onBackPressed()
    //        }
    //    }

    companion object {
        const val TAG = "MainActivity"
        const val EXIT_INTERVAL = 2000L

        val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }
}