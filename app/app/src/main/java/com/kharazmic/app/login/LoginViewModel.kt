package com.kharazmic.app.login

import android.content.Context
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


    lateinit var phoneNumber: String
    val status = MutableLiveData<Boolean>()


    fun sendSMS() {
        if (Arrange().validatePhone(phoneNumber)) {
            val data = JSONObject()
            data.put("phone", phoneNumber)
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

}