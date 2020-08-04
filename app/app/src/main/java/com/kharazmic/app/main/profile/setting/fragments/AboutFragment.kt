package com.kharazmic.app.main.profile.setting.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import com.kharazmic.app.R
import com.kharazmic.app.databinding.FragmentAboutBinding


class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        binding.webView.loadUrl("http://google.com")
        val client = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                binding.loading.visibility = View.GONE
                super.onPageFinished(view, url)
            }
        }
        binding.webView.webViewClient = client

        binding.back.setOnClickListener {
            this.findNavController().navigateUp()
        }
    }

}
