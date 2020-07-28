package com.kharazmic.app.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.kharazmic.app.R
import com.kharazmic.app.Utils
import com.kharazmic.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var utils: Utils
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.bottomMenu.selectedItemId = R.id.home
        utils = Utils(this)
        utils.isLoggedIn = true

        val adapter = RecyclerViewAdapter()
        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
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
                    icon = ContextCompat.getDrawable(this, iconList[i])!!,
                    text = getString(nameList[i])
                )
            )
        }

        return data
    }
}
