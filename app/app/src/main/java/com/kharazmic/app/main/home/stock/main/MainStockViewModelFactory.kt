package com.kharazmic.app.main.home.stock.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kharazmic.app.database.model.BestSignalDAO

class MainStockViewModelFactory(private val context: Context, private val database: BestSignalDAO) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainStockViewModel::class.java))
            return modelClass.getConstructor(Context::class.java, BestSignalDAO::class.java)
                .newInstance(context, database)
        throw IllegalArgumentException("model class is not recognized")
    }
}