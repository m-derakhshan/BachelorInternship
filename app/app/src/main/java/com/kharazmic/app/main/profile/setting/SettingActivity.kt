package com.kharazmic.app.main.profile.setting


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kharazmic.app.R




class SettingActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}
