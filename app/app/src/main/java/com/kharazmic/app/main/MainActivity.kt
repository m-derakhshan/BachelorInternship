package com.kharazmic.app.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.kharazmic.app.R
import com.kharazmic.app.Utils
import com.kharazmic.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var utils: Utils
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.bottomMenu.selectedItemId = R.id.home

        utils = Utils(this)


        utils.isLoggedIn = true


    }
}
