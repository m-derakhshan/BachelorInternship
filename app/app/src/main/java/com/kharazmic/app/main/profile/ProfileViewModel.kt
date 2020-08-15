package com.kharazmic.app.main.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.kharazmic.app.Address
import com.kharazmic.app.Arrange
import com.kharazmic.app.Utils
import com.kharazmic.app.database.MyDatabase
import com.kharazmic.app.database.model.UserInfoModel
import id.zelory.compressor.overWrite
import kotlinx.coroutines.*
import java.nio.charset.Charset

class ProfileViewModel(val context: Context, private val database: MyDatabase) : ViewModel() {


    private val viewModelJob = Job()
    private val scope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val name = MutableLiveData<String>()
    val subscription = MutableLiveData<String>()
    val followers = MutableLiveData<String>()
    val following = MutableLiveData<String>()
    val signals = MutableLiveData<String>()
    val image = MutableLiveData<String>()
    lateinit var isTokenExpired: TokenExpired


    fun getUserInfo() {
        val request = object :
            JsonObjectRequest(Method.GET, Address().UserInfoAPI, null,
                Response.Listener {
                    scope.launch {
                        async(Dispatchers.IO, CoroutineStart.DEFAULT, block = {

                            val userInformation =
                                UserInfoModel(
                                    name = Arrange().persianConverter(it.optString("name")),
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
                    try {
                        if (it.networkResponse.statusCode == 401)
                            isTokenExpired.expired(true)
                        else
                            Log.i("Log", "error in ProfileViewModel $it")
                    } catch (e: Exception) {
                        Log.i("Log", "error in ProfileViewModel $it")
                    }

                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["Authorization"] = "Bearer ${Utils(context = context).token}"
                params["Accept"] = "Application/json"
                return params
            }
        }


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


    interface TokenExpired {
        fun expired(isExpired: Boolean)
    }
}