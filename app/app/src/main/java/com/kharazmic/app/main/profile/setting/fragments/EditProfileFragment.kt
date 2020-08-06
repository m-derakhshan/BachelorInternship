package com.kharazmic.app.main.profile.setting.fragments

import android.app.Application
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.getBitmap
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
import java.io.ByteArrayOutputStream
import java.io.File


class EditProfileFragment : Fragment(), PhotoPickerFragment.Callback {


    private lateinit var viewModel: EditProfileViewModel
    private lateinit var binding: FragmentEditProfileBinding
    private val scope = CoroutineScope(Dispatchers.Main)

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
    }


    override fun onImagesPicked(photos: ArrayList<Uri>) {

        scope.launch {

            binding.profile.setImageURI(photos.first())


            async(Dispatchers.Default, CoroutineStart.DEFAULT, block = {
                val imageFile = File(photos.first().path!!)
                val compress = Compressor.compress(context = context!!, imageFile = imageFile) {
                    resolution(480, 640)
                    format(Bitmap.CompressFormat.JPEG)
                    quality(70)
                }
                val bitmap = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(
                        Application().contentResolver,
                        Uri.fromFile(compress)
                    )
                } else {
                    val source =
                        ImageDecoder.createSource(
                            activity!!.contentResolver,
                            Uri.fromFile(compress)
                        )
                    ImageDecoder.decodeBitmap(source)
                }

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, viewModel.newImage)
            }).await()


        }

    }
}
