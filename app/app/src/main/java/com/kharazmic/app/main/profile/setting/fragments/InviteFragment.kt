package com.kharazmic.app.main.profile.setting.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController

import com.kharazmic.app.R
import com.kharazmic.app.databinding.FragmentInviteBinding


class InviteFragment : Fragment() {

    private lateinit var binding: FragmentInviteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_invite, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        binding.back.setOnClickListener {
            this.findNavController().popBackStack()
        }
    }





}
