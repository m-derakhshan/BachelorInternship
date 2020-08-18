package com.kharazmic.app.main.stock.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kharazmic.app.database.model.BestSignalDAO
import java.lang.IllegalArgumentException

class MainStockViewModelFactory(private val context: Context, private val database: BestSignalDAO) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainStockViewModel::class.java))
            return MainStockViewModel(context = context, database = database) as T
        throw IllegalArgumentException("model class is not recognized")
    }
}