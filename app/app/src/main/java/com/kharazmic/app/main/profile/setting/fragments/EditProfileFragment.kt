package com.kharazmic.app.main.profile.setting.fragments


import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection
import com.kharazmic.app.R
import com.kharazmic.app.Utils
import com.kharazmic.app.database.MyDatabase
import com.kharazmic.app.databinding.FragmentEditProfileBinding
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import kotlinx.coroutines.*
import lv.chi.photopicker.PhotoPickerFragment
import java.io.File
import java.io.IOException


class EditProfileFragment : Fragment(), PhotoPickerFragment.Callback {


    private lateinit var viewModel: EditProfileViewModel
    private lateinit var binding: FragmentEditProfileBinding
    private val scope = CoroutineScope(Dispatchers.Main)
    private var imageData: ByteArray? = null
    private var worth = "0"
    private var education = "0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val database = MyDatabase.getInstance(context!!)
        val factory = EditProfileViewModelFactory(database, context!!)
        viewModel = ViewModelProvider(this, factory).get(EditProfileViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        binding.worthGroup.setOnCheckedChangeListener { _, checkedId ->
            worth = when (checkedId) {
                R.id.level1 -> "1"
                R.id.level2 -> "2"
                R.id.level3 -> "3"
                R.id.level4 -> "4"
                R.id.level5 -> "5"
                else -> "6"
            }
        }

        binding.educationCategory.setOnCheckedChangeListener { _, checkedId ->
            education = when (checkedId) {
                R.id.phd -> "1"
                R.id.master -> "2"
                R.id.bachelor -> "3"
                else -> "4"
            }
        }


        database.userDAO.getInfo().observe(viewLifecycleOwner, Observer {
            it?.let { userInfo ->
                viewModel.bindInfo(userInfo)
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            it?.let { isLoading ->
                if (isLoading)
                    binding.submit.startAnimation()
                else {
                    binding.submit.revertAnimation()
                    binding.submit.background =
                        ContextCompat.getDrawable(context!!, R.drawable.login_btn)
                }

            }
        })
        viewModel.updateStatus.observe(viewLifecycleOwner, Observer {
            it?.let { status ->
                Utils(context!!).showSnackBar(
                    color = ContextCompat.getColor(context!!, R.color.black),
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
            this.findNavController().popBackStack()
        }
        binding.chooseImage.setOnClickListener {

            PhotoPickerFragment.newInstance(
                multiple = false,
                allowCamera = true,
                maxSelection = 1,
                theme = R.style.ChiliPhotoPicker_Dark
            ).show(childFragmentManager, "choose image")


        }

        val expandPanel = ExpansionLayoutCollection()
        expandPanel.add(binding.educationLayout)
        expandPanel.add(binding.worthLayout)
        expandPanel.openOnlyOne(true)


        binding.submit.setOnClickListener {
            viewModel.uploadImage(imageData)
        }

    }


    override fun onImagesPicked(photos: ArrayList<Uri>) {
        scope.launch {
            binding.profile.setImageURI(photos.first())
            async(Dispatchers.IO, CoroutineStart.DEFAULT, block = {
                val compressedImageFile =
                    Compressor.compress(context!!, File(photos.first().path!!)) {
                        resolution(1024, 1024)
                        quality(80)
                        format(Bitmap.CompressFormat.JPEG)
                    }
                createImageData(Uri.fromFile(compressedImageFile))
            }).await()
        }

    }


    @Throws(IOException::class)
    private fun createImageData(uri: Uri) {
        val inputStream = activity?.contentResolver?.openInputStream(uri)
        inputStream?.buffered()?.let {
            imageData = it.readBytes()
        }
    }

}
