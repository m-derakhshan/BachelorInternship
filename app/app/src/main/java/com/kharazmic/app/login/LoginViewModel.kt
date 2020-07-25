package com.kharazmic.app.login

import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.kharazmic.app.Address
import com.kharazmic.app.Arrange
import org.json.JSONObject

class LoginViewModel(private val context: Context) : ViewModel() {


    var phoneNumber: String = ""
    var code: String = ""
    var counter = MutableLiveData<Float>()
    val loginStatus = MutableLiveData<Boolean>()
    val validateStatus = MutableLiveData<Boolean>()
    val timeUp = MutableLiveData<Boolean>()

    init {
        timeUp.value = false
    }


    fun sendSMS() {
        Log.i("Log", "phone is $phoneNumber")
        if (Arrange().validatePhone(phoneNumber)) {
            val data = JSONObject()
            data.put("phone", phoneNumber)
            val request =
                JsonObjectRequest(Request.Method.POST, Address().LoginAPI, data, Response.Listener {

                    loginStatus.value = true
                    loginStatus.value = it.getBoolean("status")


                }, Response.ErrorListener {
                    //------------------------****************** loginStatus.value = true **********//
                    loginStatus.value = true
                })

            val queue = Volley.newRequestQueue(context)
            queue.add(request)
        } else {
            loginStatus.value = false

        }
    }

    fun sendCode() {

        if (Arrange().validatePhone(code)) {
            val data = JSONObject()
            data.put("phone", phoneNumber)
            data.put("code", code)
            val request =
                JsonObjectRequest(Request.Method.POST, Address().LoginAPI, data, Response.Listener {
                    validateStatus.value = it.getBoolean("status")
                }, Response.ErrorListener {
                    validateStatus.value = false
                })

            val queue = Volley.newRequestQueue(context)
            queue.add(request)
        } else {

            validateStatus.value = false

        }
    }


    fun timer() {
        val timer = object : CountDownTimer(6000, 1000) {
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