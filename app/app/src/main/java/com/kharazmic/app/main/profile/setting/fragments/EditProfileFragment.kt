package com.kharazmic.app.main.profile.setting.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.kharazmic.app.R
import com.kharazmic.app.databinding.FragmentEditProfileBinding


class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory = EditProfileViewModelFactory(activity?.intent?.getParcelableExtra("userInfo"))
        val viewModel = ViewModelProvider(this, factory).get(EditProfileViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this




        binding.back.setOnClickListener {
            this.findNavController().popBackStack()
        }
    }
}
