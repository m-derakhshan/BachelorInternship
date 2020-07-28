package com.kharazmic.app.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager

import com.kharazmic.app.R
import com.kharazmic.app.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = RecyclerViewAdapter()
        binding.recyclerView.layoutManager = GridLayoutManager(context, 3)
        binding.recyclerView.adapter = adapter
        adapter.add(itemMenu())


    }


    private fun itemMenu(): ArrayList<ItemMenuModel> {
        val data = ArrayList<ItemMenuModel>()
        val nameList = listOf(
            R.string.hagh_taghadom, R.string.oragh_moshtaghe, R.string.oragh_bedehi,
            R.string.energy, R.string.ati, R.string.ekhtyar,
            R.string.saham, R.string.arz, R.string.sandogh
        )
        val iconList = listOf(
            R.drawable.ic_taghadom_icon, R.drawable.ic_moshtaghe_icon, R.drawable.ic_debit_icon,
            R.drawable.ic_energy_icon, R.drawable.ic_ekhtiar_icon, R.drawable.ic_ekhtiar_icon,
            R.drawable.ic_saham_icon, R.drawable.ic_arz_icon, R.drawable.ic_sandogh_icon
        )

        for (i in iconList.indices) {
            data.add(
                ItemMenuModel(
                    icon = ContextCompat.getDrawable(context!!, iconList[i])!!,
                    text = getString(nameList[i])
                )
            )
        }

        return data
    }
}
