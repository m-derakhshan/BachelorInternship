package com.kharazmic.app.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kharazmic.app.Arrange
import com.kharazmic.app.main.MainActivity
import com.kharazmic.app.R
import com.kharazmic.app.Utils
import com.kharazmic.app.databinding.ActivityValidateBinding

class ValidateActivity : AppCompatActivity() {

    private var phone: String = ""
    private lateinit var binding: ActivityValidateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_validate)

        val factory = LoginViewModelFactory(this)
        val viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        intent.getStringExtra("phone")?.let {
            phone = it
            viewModel.phoneNumber = phone
        }

        binding.viewModel = viewModel

        viewModel.timer()

        viewModel.validateStatus.observe(this, Observer {
            it?.let { status ->
                if (status) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
                }
                else {
                    binding.check.revertAnimation()
                    Utils(this).showSnackBar(
                        ContextCompat.getColor(this, R.color.black),
                        getString(R.string.wrong_code),
                        binding.root
                    )
                }

            }
        })

        viewModel.loginStatus.observe(this, Observer {
            it?.let {
                if (it) {
                    binding.resend.visibility = View.GONE
                    viewModel.timer()
                }
            }
        })

        viewModel.counter.observe(this, Observer {
            it?.let { progress ->
                binding.counterText.text = Arrange().persianConverter((progress.toInt()).toString())
                binding.counter.progress = progress
            }
        })

        viewModel.timeUp.observe(this, Observer {
            it?.let { isTimeUp ->
                if (isTimeUp)
                    binding.resend.visibility = View.VISIBLE
                else
                    binding.resend.visibility = View.GONE
            }
        })

        binding.resend.setOnClickListener {
            viewModel.sendSMS()
        }

        binding.wrongNumber.setOnClickListener {
            onBackPressed()
        }

        viewModel.isLoading.observe(this, Observer { isLoading ->
            isLoading?.let {
                if (isLoading)
                    binding.check.startAnimation()
                else
                    binding.check.revertAnimation()
            }
        })

    }


    override fun onBackPressed() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("phone", phone)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
