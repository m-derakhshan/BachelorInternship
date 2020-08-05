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
import com.kharazmic.app.main.profile.setting.fragments.UserInfoModel
import kotlinx.coroutines.*

class ProfileViewModel(val context: Context) : ViewModel() {

    lateinit var userInformation: UserInfoModel
    private val viewModelJob = Job()
    private val scope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val name = MutableLiveData<String>()
    val subscription = MutableLiveData<String>()
    val followers = MutableLiveData<String>()
    val following = MutableLiveData<String>()
    val signals = MutableLiveData<String>()
    val image = MutableLiveData<String>()
    val maxDays = MutableLiveData<Float>()
    val remainingDays = MutableLiveData<Float>()

    val isLoading = MutableLiveData<Boolean>()

    init {
        isLoading.value = true
        subscription.value = ""
    }


    fun getUserInfo() {
        scope.launch {

            isLoading.value = true
            withContext(Dispatchers.Default) {
                val request =
                    JsonObjectRequest(Request.Method.POST, Address().UserInfoAPI, null,
                        Response.Listener {
                            userInformation = UserInfoModel(
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
                            updateInfo()
                            isLoading.value = false
                        }, Response.ErrorListener {
                            Log.i("Log", "error $it")
                        })

                val queue = Volley.newRequestQueue(context)
                queue.add(request)
            }
        }
    }


    private fun updateInfo() {
        name.value = userInformation.name
        subscription.value = userInformation.subscription
        followers.value = userInformation.followers
        following.value = userInformation.following
        signals.value = userInformation.signals
        image.value = userInformation.image
        maxDays.value = userInformation.maxDays
        remainingDays.value = userInformation.remainingDays
    }


    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}