package com.kharazmic.app.main.profile

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
import com.kharazmic.app.R
import com.kharazmic.app.Utils
import com.kharazmic.app.database.MyDatabase
import com.kharazmic.app.databinding.FragmentProfileBinding
import com.kharazmic.app.login.LoginActivity
import kotlinx.coroutines.*


class ProfileFragment : Fragment(), ProfileViewModel.TokenExpired {

    private val settingCode = 123
    private lateinit var binding: FragmentProfileBinding
    private lateinit var database: MyDatabase

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
