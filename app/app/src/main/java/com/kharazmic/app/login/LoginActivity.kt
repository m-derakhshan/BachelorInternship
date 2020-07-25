package com.kharazmic.app.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.kharazmic.app.R
import com.kharazmic.app.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

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
                if (status) {
                    val intent = Intent(this, ValidateActivity::class.java)
                    intent.putExtra("phone", binding.phoneNumber.text.toString())
                    startActivity(intent)
                    finish()
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                } else
                    YoYo.with(Techniques.Shake)
                        .duration(1200)
                        .playOn(binding.phoneNumber)
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
    }

}
