package com.kharazmic.app.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
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
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                } else {
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
                if (isLoading) {
                    binding.resend.isEnabled = false
                    binding.check.startAnimation()
                } else {
                    binding.resend.isEnabled = true
                    binding.check.revertAnimation()
                    binding.check.background = ContextCompat.getDrawable(this, R.drawable.login_btn)
                }
            }
        })


        binding.code.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length!! == 6) {
                    val imm =
                        baseContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                    viewModel.code = s.toString()
                    viewModel.sendCode()
                }
            }
        })


        viewModel.internetStatus.observe(this, Observer { isConnect ->
            if (!isConnect) {
                Utils(this).showSnackBar(
                    color = ContextCompat.getColor(this, R.color.black),
                    msg = getString(R.string.no_connection),
                    snackView = binding.root
                )
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
