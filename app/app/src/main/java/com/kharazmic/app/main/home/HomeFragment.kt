package com.kharazmic.app.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.kharazmic.app.R
import com.kharazmic.app.databinding.FragmentHomeBinding
import com.kharazmic.app.main.stock.StockActivity


class HomeFragment : Fragment(), HomeOnClickListener {

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
        val adapter = HomeRecyclerViewAdapter()
        adapter.onClick = this
        binding.recyclerView.layoutManager = GridLayoutManager(context, 3)
        binding.recyclerView.adapter = adapter
        adapter.add(itemMenu())
    }


    private fun itemMenu(): ArrayList<ItemMenuModel> {
        val data = ArrayList<ItemMenuModel>()
        val nameList = listOf(
            R.string.sandogh, R.string.arz, R.string.saham,
            R.string.energy, R.string.ati, R.string.ekhtyar,
            R.string.hagh_taghadom, R.string.oragh_moshtaghe, R.string.oragh_bedehi
        )
        val iconList = listOf(
            R.drawable.ic_sandogh_icon, R.drawable.ic_arz_icon, R.drawable.ic_saham_icon,
            R.drawable.ic_energy_icon, R.drawable.ic_ati_icon, R.drawable.ic_ekhtiar_icon,
            R.drawable.ic_taghadom_icon, R.drawable.ic_moshtaghe_icon, R.drawable.ic_debit_icon
        )

        for (i in iconList.indices) {
            data.add(
                ItemMenuModel(
                    id = i,
                    icon = ContextCompat.getDrawable(context!!, iconList[i])!!,
                    text = getString(nameList[i])
                )
            )
        }

        return data
    }

    override fun onClick(id: Int) {
        activity?.startActivity(
            Intent(
                activity, when (id) {
                    0 -> StockActivity::class.java

                    1 -> StockActivity::class.java

                    2 -> StockActivity::class.java

                    3 -> StockActivity::class.java

                    4 -> StockActivity::class.java

                    5 -> StockActivity::class.java

                    6 -> StockActivity::class.java

                    7 -> StockActivity::class.java

                    else -> StockActivity::class.java
                }
            )
        )
        activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

    }
}
