package com.kharazmic.app.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kharazmic.app.R
import com.kharazmic.app.Utils
import com.kharazmic.app.database.MyDatabase
import com.kharazmic.app.databinding.FragmentProfileBinding
import com.kharazmic.app.login.LoginActivity
import com.kharazmic.app.main.profile.setting.SettingClickListener
import com.kharazmic.app.main.profile.setting.SettingModel
import com.kharazmic.app.main.profile.setting.SettingRecyclerAdapter
import com.kharazmic.app.main.profile.setting.activities.EditProfileActivity
import kotlinx.coroutines.*


class ProfileFragment : Fragment(), ProfileViewModel.TokenExpired, SettingClickListener {


    private lateinit var binding: FragmentProfileBinding
    private lateinit var database: MyDatabase
    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
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

        database.userDAO.getInfo().observe(viewLifecycleOwner, Observer {
            it?.let { info ->
                viewModel.bindInfo(info)
                binding.remainingSubscription.progress = info.remainingDays
                binding.remainingSubscription.progressMax = info.maxDays
            }


        })


        val adapter = SettingRecyclerAdapter().apply {
            this.clickListener = this@ProfileFragment
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }


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


    override fun expired(isExpired: Boolean) {
        if (isExpired) {
            CoroutineScope(Dispatchers.Main).launch {
                async(Dispatchers.Default, CoroutineStart.DEFAULT, block = {
                    Utils(requireContext()).apply {
                        isLoggedIn = false
                        token = ""
                    }
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

    override fun onClick(id: Int) {
        when (id) {
            0 -> {
                startActivity(Intent(activity, EditProfileActivity::class.java))
                activity?.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
            1 -> {
            }
            2 -> {
            }
            3 -> {
            }
            4 -> {
            }
            else -> {
                scope.launch {
                    async(context = Dispatchers.Default, start = CoroutineStart.DEFAULT, block = {
                        database.userDAO.deleteAll()
                        database.signalDAO.deleteAll()
                    }).await()
                    Utils(requireContext()).apply {
                        isLoggedIn = false
                        token = ""
                    }
                    activity?.startActivity(Intent(activity!!, LoginActivity::class.java))
                    activity?.finish()
                    activity?.overridePendingTransition(R.anim.fade_in, R.anim.no_effect)
                }
            }
        }
    }
}
