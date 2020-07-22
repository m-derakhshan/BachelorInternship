package com.kharazmic.app.login

import android.content.Context
import android.os.CountDownTimer
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
    val status = MutableLiveData<Boolean>()


    fun sendSMS() {
        if (Arrange().validatePhone(phoneNumber)) {
            val data = JSONObject()
            data.put("phone", phoneNumber)
            val request =
                JsonObjectRequest(Request.Method.POST, Address().LoginAPI, data, Response.Listener {

                    status.value = it.getBoolean("status")


                }, Response.ErrorListener {
                    status.value = true
                })

            val queue = Volley.newRequestQueue(context)
            queue.add(request)
        } else {
            status.value = false

        }
    }

    fun sendCode() {
        if (Arrange().validatePhone(code)) {
            val data = JSONObject()
            data.put("phone", phoneNumber)
            data.put("code", code)
            val request =
                JsonObjectRequest(Request.Method.POST, Address().LoginAPI, data, Response.Listener {

                    status.value = it.getBoolean("status")


                }, Response.ErrorListener {
                    status.value = false
                })

            val queue = Volley.newRequestQueue(context)
            queue.add(request)
        } else {

            status.value = false

        }
    }


    fun timer() {
        val timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                counter.value = (millisUntilFinished / 1000).toFloat()
            }

            override fun onFinish() {
                counter.value = 0F
            }
        }
        timer.start()
    }

}