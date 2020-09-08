package com.kharazmic.app.main.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.kharazmic.app.R
import com.kharazmic.app.databinding.FragmentAnalysisCategoryBinding


class AnalysisCategoryFragment : Fragment() {

    private lateinit var binding: FragmentAnalysisCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_analysis_category, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fundamental.setOnClickListener {
            binding.fundamentalLine.visibility = View.VISIBLE
            binding.technicalLine.visibility = View.INVISIBLE

        }

        binding.technical.setOnClickListener {
            binding.fundamentalLine.visibility = View.INVISIBLE
            binding.technicalLine.visibility = View.VISIBLE
        }

    }
}