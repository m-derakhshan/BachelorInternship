package com.kharazmic.app.main.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ActivityNavigatorExtras
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.tabs.TabLayoutMediator
import com.kharazmic.app.R
import com.kharazmic.app.Utils
import com.kharazmic.app.databinding.FragmentProfileBinding
import com.kharazmic.app.main.MainActivity
import com.kharazmic.app.main.profile.setting.SettingActivity


class ProfileFragment : Fragment() {

    private val settingCode = 123
    private lateinit var binding: FragmentProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory = ProfileViewModelFactory(context!!)
        val viewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)


        viewModel.getUserInfo()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        val adapter = MainActivity.ViewPagerAdapter(activity!!)
        binding.viewPager.adapter = adapter
        binding.viewPager.offscreenPageLimit = 2
        val buyFragment = SignalsFragment("buy")
        val sellFragment = SignalsFragment("sell")
        adapter.add(buyFragment)
        adapter.add(sellFragment)


        val icons = listOf(R.drawable.ic_buy_signal_icon, R.drawable.ic_sell_signal_icon)
        TabLayoutMediator(binding.title, binding.viewPager) { tab, position ->
            tab.setIcon(icons[position])
            binding.viewPager.setCurrentItem(tab.position, true)
        }.attach()




        viewModel.remainingDays.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.remainingSubscription.progress = it
                binding.remainingSubscription.progressMax = viewModel.maxDays.value ?: 0F
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {

            it?.let { isLoading ->
                if (!isLoading) {
                    YoYo.with(Techniques.FadeOut)
                        .duration(500)
                        .onEnd {
                            binding.loading.visibility = View.GONE
                        }
                        .playOn(binding.loading)

                } else {
                    binding.loading.visibility = View.VISIBLE
                    YoYo.with(Techniques.FadeIn)
                        .duration(500)
                        .playOn(binding.loading)
                }
            }
        })

        binding.refresh.setOnRefreshListener {
            viewModel.getUserInfo()
            buyFragment.fetchData()
            sellFragment.fetchData()
            binding.refresh.isRefreshing = false
        }



        binding.menu.setOnClickListener {
            startActivityForResult(Intent(activity, SettingActivity::class.java), settingCode)
            activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == settingCode && resultCode == Activity.RESULT_OK && data != null) {
            if (data.getBooleanExtra("exit", true)) {
                Utils(context!!).isLoggedIn = false
                activity?.finish()
            }
        }
    }
}
