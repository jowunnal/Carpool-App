package com.mate.carpool.ui.util

import android.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import com.mate.carpool.R
import com.mate.carpool.ui.screen.CheckDialogFragment

object SettingToolbarUtils {
    /*
    해당 fragmentActivity 내에 있는 toolbar 에 ' < ' 모양의 메뉴가 있는 action bar 생성
     */
    @JvmStatic
    fun setActionBar(fragmentActivity: FragmentActivity, toolbar: Toolbar){
        fragmentActivity.setActionBar(toolbar)
        fragmentActivity.actionBar!!.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
            setDisplayShowTitleEnabled(false)
        }
    }

    @JvmStatic
    fun showBottomSheetFragment(fragmentActivity:FragmentActivity, title:String, message:String, tag:String, onDoSomething: CheckDialogFragment.Listener){
        CheckDialogFragment(
            title,
            message,
            onDoSomething,
        ).show(fragmentActivity.supportFragmentManager,tag)
    }
}