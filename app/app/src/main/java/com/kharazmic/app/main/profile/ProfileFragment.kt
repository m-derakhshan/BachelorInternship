package com.kharazmic.app.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.tabs.TabLayoutMediator
import com.kharazmic.app.Address

import com.kharazmic.app.R
import com.kharazmic.app.Utils
import com.kharazmic.app.databinding.FragmentProfileBinding
import com.kharazmic.app.main.MainActivity


class ProfileFragment : Fragment() {

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

        binding.exit.setOnClickListener {
            Utils(context!!).isLoggedIn = false
        }

        val adapter = MainActivity.ViewPagerAdapter(activity!!)
        binding.viewPager.adapter = adapter
        binding.viewPager.offscreenPageLimit = 2
        val buyFragment = SignalsFragment("buy")
        val sellFragment = SignalsFragment("sell")
        adapter.add(buyFragment)
        adapter.add(sellFragment)


        val titles = listOf(R.string.buy_signals, R.string.sell_signals)
        TabLayoutMediator(binding.title, binding.viewPager) { tab, position ->
            tab.text = getString(titles[position])
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
                        .duration(1000)
                        .onEnd {
                            binding.loading.visibility = View.GONE
                        }
                        .playOn(binding.loading)

                } else
                    binding.loading.visibility = View.VISIBLE
            }
        })

        binding.refresh.setOnRefreshListener {
            viewModel.getUserInfo()
            buyFragment.fetchData()
            sellFragment.fetchData()
            binding.refresh.isRefreshing = false
        }


    }


}
