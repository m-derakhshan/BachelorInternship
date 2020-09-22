package com.kharazmic.app.main.profile.setting.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection
import com.kharazmic.app.R
import com.kharazmic.app.Utils
import com.kharazmic.app.database.MyDatabase
import com.kharazmic.app.databinding.ActivityEditProfileBinding
import java.io.IOException

class EditProfileActivity : AppCompatActivity() {


    private lateinit var viewModel: EditProfileViewModel
    private lateinit var binding: ActivityEditProfileBinding
    private var imageData: ByteArray? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)


        val database = MyDatabase.getInstance(this)
        val factory = EditProfileViewModelFactory(database, this)
        viewModel = ViewModelProvider(this, factory).get(EditProfileViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        database.userDAO.getInfo().observe(this, Observer {
            it?.let { userInfo ->
                viewModel.bindInfo(userInfo)
            }
        })

        viewModel.isLoading.observe(this, Observer {
            it?.let { isLoading ->
                if (isLoading)
                    binding.submit.startAnimation()
                else {
                    binding.submit.revertAnimation()
                    binding.submit.background =
                        ContextCompat.getDrawable(this, R.drawable.login_btn)
                }

            }
        })
        viewModel.isImageLoading.observe(this, Observer {
            it?.let { isLoading ->
                binding.imageLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        })
        viewModel.updateStatus.observe(this, Observer {
            it?.let { status ->
                Utils(this).showSnackBar(
                    color = ContextCompat.getColor(this, R.color.black),
                    snackView = binding.root,
                    msg = when (status) {
                        0 -> getString(R.string.success)
                        1 -> getString(R.string.no_connection)
                        else -> getString(R.string.error)
                    }
                )


            }
        })


        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.chooseImage.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }

        val expandPanel = ExpansionLayoutCollection()
        expandPanel.add(binding.educationLayout)
        expandPanel.add(binding.worthLayout)
        expandPanel.openOnlyOne(true)


        binding.submit.setOnClickListener {
            viewModel.updateInfo()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            binding.profile.setImageURI(data.data)
            data.data?.let { createImageData(it) }
            viewModel.uploadImage(imageData)
        }
    }


    @Throws(IOException::class)
    private fun createImageData(uri: Uri) {
        val inputStream = contentResolver.openInputStream(uri)
        inputStream?.buffered()?.let {
            imageData = it.readBytes()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

}