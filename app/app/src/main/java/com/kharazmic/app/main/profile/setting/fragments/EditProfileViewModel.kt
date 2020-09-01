package com.kharazmic.app.main.profile.setting.fragments

import android.content.Context
import android.util.Base64
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.kharazmic.app.Address
import com.kharazmic.app.Arrange
import com.kharazmic.app.R
import com.kharazmic.app.database.MyDatabase
import com.kharazmic.app.database.model.UserInfoModel
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.ByteArrayOutputStream


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
    val newImage = ByteArrayOutputStream()


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


    fun updateInfo() {
        isLoading.value = true
        val info = JSONObject()
        info.put("name", name.value)
        info.put("education", education.value)
        info.put("experience", experience.value)
        info.put("worth", worth.value)
        info.put("image", Base64.encodeToString(newImage.toByteArray(), Base64.DEFAULT))


        val request = JsonObjectRequest(
            Request.Method.POST,
            Address().updateInfoAPI,
            info,
            Response.Listener {
                scope.launch(
                    context = Dispatchers.Default,
                    start = CoroutineStart.DEFAULT,
                    block = {
                        val userInformation =
                            UserInfoModel(
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
                        database.userDAO.add(userInformation)
                    })
                updateStatus.value = 0
                isLoading.value = false
            },
            Response.ErrorListener {
                updateStatus.value =
                    if (it is NetworkError || it is TimeoutError || it is NoConnectionError) 1
                    else 2
                isLoading.value = false
            })

        request.retryPolicy = DefaultRetryPolicy(
            30000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        val queue = Volley.newRequestQueue(context)
        queue.add(request)

    }


    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

}