package com.kharazmic.app.main.home.stock.search


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SearchViewModelFactory(private val context: Context) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java))
            return modelClass.getConstructor(Context::class.java).newInstance(context)
        throw IllegalArgumentException("ViewModelClass is not recognized")
    }
}