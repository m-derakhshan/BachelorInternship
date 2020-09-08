package com.kharazmic.app.main.profile.setting.activities

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kharazmic.app.database.MyDatabase

class EditProfileViewModelFactory(private val database: MyDatabase, private val context: Context) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditProfileViewModel::class.java))
            return modelClass.getConstructor(MyDatabase::class.java, Context::class.java)
                .newInstance(database, context)
        throw IllegalArgumentException("model class is not true")
    }

}
