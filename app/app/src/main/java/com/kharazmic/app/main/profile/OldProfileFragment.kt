package com.kharazmic.app.main.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.kharazmic.app.R
import com.kharazmic.app.Utils
import com.kharazmic.app.database.MyDatabase
import com.kharazmic.app.databinding.FragmentProfileOldBinding
import com.kharazmic.app.login.LoginActivity
import com.kharazmic.app.main.MainActivity
import com.kharazmic.app.main.profile.setting.SettingActivity
import com.kharazmic.app.main.profile.signals.SignalsFragment
import kotlinx.coroutines.*


class OldProfileFragment : Fragment(), ProfileViewModel.TokenExpired {

    private val settingCode = 123
    private lateinit var binding: FragmentProfileOldBinding
    private lateinit var database: MyDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_old, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        database = MyDatabase.getInstance(context!!)
        val factory = ProfileViewModelFactory(context!!, database)
        val viewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)

        viewModel.isTokenExpired = this
        viewModel.getUserInfo()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        val adapter = MainActivity.ViewPagerAdapter(activity!!)
        binding.viewPager.adapter = adapter
        binding.viewPager.offscreenPageLimit = 2
        val buyFragment =
            SignalsFragment("buy")
        val sellFragment =
            SignalsFragment("sell")
        adapter.add(buyFragment)
        adapter.add(sellFragment)


        val icons = listOf(R.drawable.ic_buy_signal_icon, R.drawable.ic_sell_signal_icon)
        TabLayoutMediator(binding.title, binding.viewPager) { tab, position ->
            tab.setIcon(icons[position])
            binding.viewPager.setCurrentItem(tab.position, true)
        }.attach()


        database.userDAO.getInfo().observe(viewLifecycleOwner, Observer {
            it?.let { info ->
                viewModel.bindInfo(info)
                binding.remainingSubscription.progress = info.remainingDays
                binding.remainingSubscription.progressMax = info.maxDays
            }


        })

        binding.menu.setOnClickListener {
            startActivityForResult(Intent(activity, SettingActivity::class.java), settingCode)
            activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == settingCode && resultCode == Activity.RESULT_OK && data != null) {
            if (data.getBooleanExtra("exit", true)) {
                CoroutineScope(Dispatchers.Default).launch {
                    async(Dispatchers.IO, CoroutineStart.DEFAULT, block = {
                        Utils(context!!).isLoggedIn = false
                        database.userDAO.deleteAll()
                        activity?.startActivity(Intent(activity!!, LoginActivity::class.java))
                        activity?.finish()
                    }).await()
                }
            }
        }
    }

    override fun expired(isExpired: Boolean) {
        if (isExpired) {
            CoroutineScope(Dispatchers.Main).launch {
                async(Dispatchers.Default, CoroutineStart.DEFAULT, block = {
                    Utils(context!!).isLoggedIn = false
                    database.userDAO.deleteAll()
                    activity?.startActivity(Intent(activity!!, LoginActivity::class.java))
                    activity?.finish()
                    activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                }).await()
                Toast.makeText(
                    context!!,
                    "خطای اعتبارسنجی، لطفا مجددا وارد شوید.",
                    Toast.LENGTH_LONG
                ).show()
            }


        }
    }
}
