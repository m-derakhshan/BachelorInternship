package com.kharazmic.app.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.kharazmic.app.R
import com.kharazmic.app.Utils
import com.kharazmic.app.databinding.ActivityLoginBinding
import com.kharazmic.app.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var utils: Utils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        utils = Utils(this)
        if (utils.isLoggedIn) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        val factory = LoginViewModelFactory(this)
        val viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        binding.viewModel = viewModel
        intent.getStringExtra("phone")?.let {
            viewModel.phoneNumber = it
            binding.accept.isChecked = true
            binding.login.alpha = 1F
            binding.login.isEnabled = true
        }

        viewModel.loginStatus.observe(this, Observer {
            it?.let { status ->
                Log.i("Log", "status is $status")
                if (status) {
                    val intent = Intent(this, ValidateActivity::class.java)
                    intent.putExtra("phone", binding.phoneNumber.text.toString())
                    startActivity(intent)
                    finish()
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                } else {

                    YoYo.with(Techniques.Shake)
                        .duration(1200)
                        .playOn(binding.phoneNumber)
                }
            }

        })

        viewModel.internetStatus.observe(this, Observer { isConnect ->
            if (!isConnect) {
                utils.showSnackBar(
                    color = ContextCompat.getColor(this, R.color.black),
                    msg = getString(R.string.no_connection),
                    snackView = binding.root
                )
            }
        })

        binding.accept.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.login.alpha = 1F
                binding.login.isEnabled = true
            } else {
                binding.login.alpha = 0.8F
                binding.login.isEnabled = false
            }
        }


        viewModel.isLoading.observe(this, Observer { isLoading ->
            isLoading?.let {
                if (isLoading)
                    binding.login.startAnimation()
                else {
                    binding.login.revertAnimation()
                    binding.login.background = ContextCompat.getDrawable(this, R.drawable.login_btn)

                }
            }
        })
    }

}
