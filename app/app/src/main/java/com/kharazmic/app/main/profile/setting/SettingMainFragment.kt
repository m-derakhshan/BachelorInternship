package com.kharazmic.app.main.profile.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kharazmic.app.R
import com.kharazmic.app.Utils
import com.kharazmic.app.database.MyDatabase
import com.kharazmic.app.databinding.FragmentSettingMainBinding
import com.kharazmic.app.login.LoginActivity
import kotlinx.coroutines.*


class SettingMainFragment : Fragment(), SettingClickListener {

    private lateinit var database: MyDatabase
    private lateinit var binding: FragmentSettingMainBinding
    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_setting_main, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        database = MyDatabase.getInstance(context!!)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = SettingRecyclerAdapter()
        adapter.clickListener = this
        binding.recyclerView.adapter = adapter

//        binding.back.setOnClickListener {
//            activity?.onBackPressed()
//        }


        val titles = listOf(
            R.string.editProfile, R.string.wallet, R.string.invitation,
            R.string.contact_us, R.string.about_us, R.string.exit
        )
        val icons = listOf(
            R.drawable.ic_edit_information_icon,
            R.drawable.ic_wallet_icon,
            R.drawable.ic_invitation_icon,
            R.drawable.ic_contact_us_icon,
            R.drawable.ic_information_icon,
            R.drawable.ic_exit_icon
        )
        val list = ArrayList<SettingModel>()
        for (i in icons.indices) {
            list.add(
                SettingModel(
                    id = i,
                    title = getString(titles[i]),
                    icon = ContextCompat.getDrawable(context!!, icons[i])!!
                )
            )
        }
        adapter.add(list)

    }

    override fun onClick(id: Int) {
        when (id) {
            0 -> this.findNavController()
                .navigate(R.id.action_settingMainFragment_to_editProfileFragment)
            1 -> this.findNavController()
                .navigate(R.id.action_settingMainFragment_to_walletFragment)
            2 -> this.findNavController()
                .navigate(R.id.action_settingMainFragment_to_inviteFragment)
            3 -> this.findNavController()
                .navigate(R.id.action_settingMainFragment_to_contactFragment)
            4 -> this.findNavController().navigate(R.id.action_settingMainFragment_to_aboutFragment)
            else -> {
                scope.launch {
                    async(context = Dispatchers.Default, start = CoroutineStart.DEFAULT, block = {
                        database.userDAO.deleteAll()
                        database.signalDAO.deleteAll()
                    }).await()
                    Utils(requireContext()).isLoggedIn = false
                    activity?.startActivity(Intent(activity!!, LoginActivity::class.java))
                    activity?.finish()
                    activity?.overridePendingTransition(R.anim.fade_in, R.anim.no_effect)
                }
            }
        }

    }
}


