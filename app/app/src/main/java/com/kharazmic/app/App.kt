package com.kharazmic.app

import android.app.Application
import com.kharazmic.app.main.profile.setting.GlideImageLoader
import lv.chi.photopicker.ChiliPhotoPicker

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        ChiliPhotoPicker.init(
            loader = GlideImageLoader(),
            authority = "com.kharazmic.app"
        )
    }
}