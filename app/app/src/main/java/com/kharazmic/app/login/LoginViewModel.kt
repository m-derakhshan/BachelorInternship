package com.kharazmic.app.login

import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.kharazmic.app.Address
import com.kharazmic.app.Arrange
import com.kharazmic.app.Utils
import org.json.JSONObject

class LoginViewModel(private val context: Context) : ViewModel() {


    var phoneNumber: String = ""
    var code: String = ""
    var counter = MutableLiveData<Float>()
    val loginStatus = MutableLiveData<Boolean>()
    val validateStatus = MutableLiveData<Boolean>()
    val internetStatus = MutableLiveData<Boolean>()

    val timeUp = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()

    init {
        internetStatus.value = true
        timeUp.value = false
        isLoading.value = false
    }


    fun sendSMS() {
        if (Arrange().validatePhone(phoneNumber)) {
            isLoading.value = true
            val request =
                JsonObjectRequest(
                    Request.Method.GET,
                    Address().loginAPI(phoneNumber),
                    null,
                    {
                        loginStatus.value = it.getBoolean("status")
                        isLoading.value = !it.getBoolean("status")
                    },
                    {
                        isLoading.value = false
                        internetStatus.value = false
                        try {
                            Log.i(
                                "Log",
                                "Error in LoginViewModel_login ${
                                String(
                                    it.networkResponse.data,
                                    Charsets.UTF_8
                                )
                                }"
                            )
                        } catch (e: Exception) {
                            Log.i("Log", "Error in LoginViewModel_Login $it")
                        }
                    })


            Volley.newRequestQueue(context).add(request)

        } else {
            isLoading.value = false
            loginStatus.value = false

        }
    }

    fun sendCode() {
        if (code.isNotEmpty()) {
            isLoading.value = true
            val data = JSONObject()
            data.put("identifier", phoneNumber)
            data.put("password", code)

            val request =
                JsonObjectRequest(
                    Request.Method.POST,
                    Address().validatePhoneAPI,
                    data,
                    {
                        Utils(context).token = it.getString("jwt")
                        validateStatus.value = true
                        isLoading.value = true
                    },
                    {

                        validateStatus.value = false
                        isLoading.value = false
                        try {
                            internetStatus.value = it.networkResponse.statusCode == 400

                            Log.i(
                                "Log",
                                "Error in LoginViewModel_Validate ${
                                String(
                                    it.networkResponse.data,
                                    Charsets.UTF_8
                                )
                                }"
                            )
                        } catch (e: Exception) {
                            internetStatus.value = false
                            Log.i("Log", "Error in LoginViewModel_Validate $it")
                        }
                    })


            Volley.newRequestQueue(context).add(request)

        } else {

            validateStatus.value = false
            isLoading.value = false

        }
    }


    fun timer() {
        val timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                counter.value = (millisUntilFinished / 1000).toFloat()
            }

            override fun onFinish() {
                counter.value = 0F
                timeUp.value = true
            }
        }
        timer.start()
    }

}