package com.kharazmic.app.main.profile.setting.activities

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.kharazmic.app.Address
import com.kharazmic.app.Arrange
import com.kharazmic.app.R
import com.kharazmic.app.Utils
import com.kharazmic.app.database.MyDatabase
import com.kharazmic.app.database.model.UserInfoModel
import com.kharazmic.app.main.profile.setting.FileDataPart
import com.kharazmic.app.main.profile.setting.VolleyFileUploadRequest
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject


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
    val isImageLoading = MutableLiveData<Boolean>()
    val updateStatus = MutableLiveData<Int>()


    init {
        isLoading.value = false
    }


    fun bindInfo(userInfo: UserInfoModel) {
        name.value = userInfo.name
        image.value = userInfo.image
        experience.value = userInfo.experience.toString()
        education.value = when (userInfo.education) {
            0 -> R.id.phd
            1 -> R.id.master
            2 -> R.id.bachelor
            3 -> R.id.less_education
            else -> -1
        }
        worth.value = when (userInfo.worth) {
            0 -> R.id.level1
            1 -> R.id.level2
            2 -> R.id.level3
            3 -> R.id.level4
            4 -> R.id.level5
            5 -> R.id.level6
            else -> -1
        }
    }


    fun uploadImage(imageData: ByteArray? = null) {

        isImageLoading.value = true

        val request = object : VolleyFileUploadRequest(
            context = context,
            method = Method.POST,
            url = Address().updateAvatar,
            listener = Response.Listener {

                scope.launch {
                    async(Dispatchers.Default, CoroutineStart.DEFAULT, block = {
                        val info = JSONArray(String(it.data, Charsets.UTF_8))
                        val image = Address().base + info.getJSONObject(0).getString("url")
                        database.userDAO.updateImage(image)
                    }).await()
                }

                isImageLoading.value = false
            },
            errorListener = Response.ErrorListener {
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
                    params["files"] = FileDataPart("image", imageData, "png")
                return params
            }


            override fun getParams(): MutableMap<String, String> {
                val info = HashMap<String, String>()
                info["refId"] = Utils(context).profileID!!
                info["ref"] = "person"
                info["field"] = "avatar"
                return info
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["Authorization"] = "Bearer ${Utils(context = context).token}"
                params["Accept"] = "Application/json"
                return params
            }

        }
        request.retryPolicy = DefaultRetryPolicy(10000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        Volley.newRequestQueue(context).add(request)
    }


    fun updateInfo() {
        val info = JSONObject()
        info.put("name", name.value.toString())
        info.put("experience", experience.value.toString().toInt())

        info.put(
            "education", when (education.value) {
                R.id.phd -> 0
                R.id.master -> 1
                R.id.bachelor -> 2
                else -> 3
            }
        )

        info.put(
            "worth", when (worth.value) {
                R.id.level1 -> 0
                R.id.level2 -> 1
                R.id.level3 -> 2
                R.id.level4 -> 3
                R.id.level5 -> 4
                else -> 5
            }
        )

        isLoading.value = true
        val request = object :
            JsonObjectRequest(Method.POST, Address().updateInfoAPI, info, Response.Listener {
                scope.launch {
                    updateStatus.value = 0
                    isLoading.value = false
                    async(Dispatchers.IO, CoroutineStart.DEFAULT, block = {
                        database.userDAO.deleteAll()
                    }).await()
                    async(Dispatchers.IO, CoroutineStart.DEFAULT, block = {
                        val userInformation =
                            UserInfoModel(
                                name = it.optString("name"),
                                image = Address().base + it.optJSONObject("avatar")?.getString("url"),
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
                isLoading.value = false
                updateStatus.value = if (it is NetworkError || it is TimeoutError) 1 else 2

                try {
                    Log.i(
                        "Log",
                        "error in EditProfileUpdateInfo is ${String(
                            it.networkResponse.data,
                            Charsets.UTF_8
                        )}"
                    )
                } catch (e: Exception) {
                    Log.i("Log", "error in EditProfileUpdateInfo is $it")
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
        request.retryPolicy = DefaultRetryPolicy(10000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        Volley.newRequestQueue(context).add(request)
    }


    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

}
