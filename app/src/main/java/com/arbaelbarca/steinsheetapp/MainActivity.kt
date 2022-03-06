package com.arbaelbarca.steinsheetapp

import android.view.LayoutInflater
import com.arbaelbarca.steinsheetapp.data.base.BaseActivityBinding
import com.arbaelbarca.steinsheetapp.databinding.ActivityMainBinding
import com.arbaelbarca.steinsheetapp.ui.fragment.home.HomeSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivityBinding<ActivityMainBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setupView(binding: ActivityMainBinding) {
        initial()
    }

    private fun initial() {
        loadFragment()
    }

    private fun loadFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameMain, HomeSheetFragment())
            .commit()
    }

}