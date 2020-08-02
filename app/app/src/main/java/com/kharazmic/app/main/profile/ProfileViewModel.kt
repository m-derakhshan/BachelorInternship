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
import kotlinx.coroutines.*

class ProfileViewModel(val context: Context) : ViewModel() {

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

                            name.value = it.getString("name")
                            image.value = it.getString("image")
                            maxDays.value = it.getInt("maxDays").toFloat()
                            remainingDays.value = it.getInt("remainingDays").toFloat()
                            subscription.value =
                                Arrange().persianConverter(it.getString("subscription"))
                            followers.value = Arrange().persianConverter(it.getString("followers"))
                            following.value = Arrange().persianConverter(it.getString("following"))
                            signals.value = Arrange().persianConverter(it.getString("signals"))
                            isLoading.value = false
                        }, Response.ErrorListener {
                            Log.i("Log", "error $it")
                        })

                val queue = Volley.newRequestQueue(context)
                queue.add(request)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}