package com.kharazmic.app.main.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.kharazmic.app.Address
import com.kharazmic.app.Arrange
import com.kharazmic.app.Utils
import com.kharazmic.app.database.MyDatabase
import com.kharazmic.app.database.model.UserInfoModel
import kotlinx.coroutines.*

class ProfileViewModel(val context: Context, private val database: MyDatabase) : ViewModel() {


    private val viewModelJob = Job()
    private val scope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val name = MutableLiveData<String>()
    val subscription = MutableLiveData<String>()
    private val followers = MutableLiveData<String>()
    private val following = MutableLiveData<String>()
    val signals = MutableLiveData<String>()
    val image = MutableLiveData<String>()
    lateinit var isTokenExpired: TokenExpired


    fun getUserInfo() {
        val request = object :
            JsonObjectRequest(Method.GET, Address().userInfoAPI, null,
                Response.Listener {
                    scope.launch {
                        async(Dispatchers.IO, CoroutineStart.DEFAULT, block = {
                            Utils(context).profileID = it.optString("_id")
                            val userInformation =
                                UserInfoModel(
                                    name = Arrange().persianConverter(it.optString("name")),
                                    image = Address().base + it.optJSONObject("avatar")
                                        ?.getString("url"),
                                    education = it.optInt("education"),
                                    experience = it.optInt("experience"),
                                    followers = Arrange().persianConverter(it.optString("followers")),
                                    following = Arrange().persianConverter(it.optString("following")),
                                    maxDays = it.optInt("maxDays").toFloat(),
                                    remainingDays = it.optInt("remainingDays").toFloat(),
                                    signals = Arrange().persianConverter(it.optString("signals")),
                                    subscription = Arrange().persianConcatenate(
                                        middle = " اتمام اشتراک",
                                        first = it.optString("subscription")
                                    ),
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
                            Log.i(
                                "Log",
                                "error in ProfileViewModel ${String(
                                    it.networkResponse.data,
                                    Charsets.UTF_8
                                )}"
                            )
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

        request.retryPolicy =
            DefaultRetryPolicy(10000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        Volley.newRequestQueue(context).add(request)


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