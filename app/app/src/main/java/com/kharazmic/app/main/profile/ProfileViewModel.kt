package com.kharazmic.app.main.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.kharazmic.app.Address
import com.kharazmic.app.Arrange
import com.kharazmic.app.database.MyDatabase
import com.kharazmic.app.database.UserInfoModel
import kotlinx.coroutines.*

class ProfileViewModel(val context: Context, private val database: MyDatabase) : ViewModel() {


    private val viewModelJob = Job()
    private val scope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val name = MutableLiveData<String>()
    val subscription = MutableLiveData<String>()
    val followers = MutableLiveData<String>()
    val following = MutableLiveData<String>()
    val signals = MutableLiveData<String>()
    val image = MutableLiveData<String>()


    fun getUserInfo() {
        val request =
            JsonObjectRequest(Request.Method.POST, Address().UserInfoAPI, null,
                Response.Listener {
                    scope.launch {
                        async(Dispatchers.IO, CoroutineStart.DEFAULT, block = {

                            val userInformation =
                                UserInfoModel(
                                    name = it.optString("name"),
                                    image = it.optString("image"),
                                    education = it.optInt("education"),
                                    experience = it.optInt("experience"),
                                    followers = Arrange().persianConverter(it.optString("followers")),
                                    following = Arrange().persianConverter(it.optString("following")),
                                    maxDays = it.optInt("maxDays").toFloat(),
                                    remainingDays = it.optInt("remainingDays").toFloat(),
                                    signals = Arrange().persianConverter(it.optString("signals")),
                                    subscription = Arrange().persianConverter(it.optString("subscription")),
                                    worth = it.optInt("worth")
                                )
                            database.userDAO.add(userInformation)
                        }).await()
                    }

                }, Response.ErrorListener {
                    Log.i("Log", "error $it")
                })

        val queue = Volley.newRequestQueue(context)
        queue.add(request)


    }


    fun bindInfo(info: UserInfoModel) {
        name.value = info.name
        subscription.value = info.subscription
        followers.value = info.followers
        following.value = info.following
        signals.value = info.signals
        image.value = info.image
    }


    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}