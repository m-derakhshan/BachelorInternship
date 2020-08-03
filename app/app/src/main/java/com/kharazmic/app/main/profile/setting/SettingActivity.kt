package com.kharazmic.app.main.profile.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.kharazmic.app.R
import com.kharazmic.app.databinding.ActivitySettingBinding
import com.kharazmic.app.main.profile.SignalsModel

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = SettingRecyclerAdapter()
        binding.recyclerView.adapter = adapter


        val titles = listOf(
            R.string.editProfile, R.string.wallet, R.string.about_us,
            R.string.contact_us, R.string.invitation
        )


        val icons = listOf(
            R.drawable.ic_profile_icon, R.drawable.ic_wallet_icon, R.drawable.ic_information_icon
            , R.drawable.ic_profile_icon, R.drawable.ic_invitation_icon
        )

        val list = ArrayList<SettingModel>()
        for (i in icons.indices) {
            list.add(
                SettingModel(
                    title = getString(titles[i]),
                    icon = ContextCompat.getDrawable(this, icons[i])!!
                )
            )
        }
        adapter.add(list)


    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}
