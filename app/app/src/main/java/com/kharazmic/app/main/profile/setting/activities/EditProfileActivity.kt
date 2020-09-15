package com.kharazmic.app.main.profile.setting.activities

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection
import com.kharazmic.app.R
import com.kharazmic.app.Utils
import com.kharazmic.app.database.MyDatabase
import com.kharazmic.app.databinding.ActivityEditProfileBinding
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import kotlinx.coroutines.*
import lv.chi.photopicker.PhotoPickerFragment
import java.io.File
import java.io.IOException

class EditProfileActivity : AppCompatActivity(), PhotoPickerFragment.Callback {


    private lateinit var viewModel: EditProfileViewModel
    private lateinit var binding: ActivityEditProfileBinding
    private val scope = CoroutineScope(Dispatchers.Main)
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

            PhotoPickerFragment.newInstance(
                multiple = false,
                allowCamera = true,
                maxSelection = 1,
                theme = R.style.ChiliPhotoPicker_Dark
            ).show(supportFragmentManager, "choose image")


        }

        val expandPanel = ExpansionLayoutCollection()
        expandPanel.add(binding.educationLayout)
        expandPanel.add(binding.worthLayout)
        expandPanel.openOnlyOne(true)


        binding.submit.setOnClickListener {
            viewModel.updateInfo()
        }


    }


    override fun onImagesPicked(photos: ArrayList<Uri>) {
        scope.launch {
            binding.profile.setImageURI(photos.first())
            async(Dispatchers.IO, CoroutineStart.DEFAULT, block = {
                try {
                    val compressedImageFile = Compressor.compress(
                        this@EditProfileActivity,
                        File(this@EditProfileActivity.cacheDir, photos.first().path!!)
                    ) {
                        resolution(1024, 1024)
                        quality(80)
                        format(Bitmap.CompressFormat.JPEG)
                    }
                    createImageData(Uri.fromFile(compressedImageFile))
                } catch (e: Exception) {
                    createImageData(photos.first())
                }
            }).await()
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