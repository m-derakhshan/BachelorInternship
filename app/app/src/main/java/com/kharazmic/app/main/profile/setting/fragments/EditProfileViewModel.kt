package com.kharazmic.app.main.profile.setting.fragments

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.kharazmic.app.R
import com.kharazmic.app.database.MyDatabase
import com.kharazmic.app.database.model.UserInfoModel
import com.kharazmic.app.main.profile.setting.FileDataPart
import com.kharazmic.app.main.profile.setting.VolleyFileUploadRequest
import kotlinx.coroutines.*


class EditProfileViewModel(private val database: MyDatabase, private val context: Context) :
    ViewModel() {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    val name = MutableLiveData<String>()
    val image = MutableLiveData<String>()
    val experience = MutableLiveData<String>()
    val education = MutableLiveData<Int>()
    val worth = MutableLiveData<Int>()
    val isLoading = MutableLiveData<Boolean>()
    val updateStatus = MutableLiveData<Int>()


    init {
        isLoading.value = false
    }


    fun bindInfo(userInfo: UserInfoModel) {
        name.value = userInfo.name
        image.value = userInfo.image
        experience.value = userInfo.experience.toString()
        education.value = when (userInfo.education) {
            1 -> R.id.phd
            2 -> R.id.master
            3 -> R.id.bachelor
            4 -> R.id.less_education
            else -> -1
        }
        worth.value = when (userInfo.worth) {
            1 -> R.id.level1
            2 -> R.id.level2
            3 -> R.id.level3
            4 -> R.id.level4
            6 -> R.id.level5
            else -> -1
        }
    }


    fun uploadImage(imageData: ByteArray? = null) {

        isLoading.value = true

        val request = object : VolleyFileUploadRequest(
            context = context,
            method = Method.POST,
            url = "https://ptsv2.com/t/2lh87-1599027066/post",
            listener = Response.Listener {

                scope.launch {
                    async(Dispatchers.Default, CoroutineStart.DEFAULT, block = {


                    }).await()
                }

                isLoading.value = false
                println("response is: $it")
            },
            errorListener = Response.ErrorListener {
                isLoading.value = false
                try {
                    Log.i("Log", "error is ${String(it.networkResponse.data, Charsets.UTF_8)}")
                } catch (e: Exception) {
                    Log.i("Log", "error is $it")
                }
            }
        ) {
            override fun getByteData(): MutableMap<String, FileDataPart> {
                val params = HashMap<String, FileDataPart>()
                if (imageData != null)
                    params["avatar"] = FileDataPart("image", imageData, "jpeg")
                return params
            }

            override fun getParams(): MutableMap<String, String> {
                val info = HashMap<String, String>()
                info["name"] = name.value.toString()
                info["education"] = education.value.toString()
                info["experience"] = experience.value.toString()
                info["worth"] = worth.value.toString()
                return info
            }
        }
        request.retryPolicy = DefaultRetryPolicy(10000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        Volley.newRequestQueue(context).add(request)
    }


    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

}