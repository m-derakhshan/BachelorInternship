package com.kharazmic.app.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kharazmic.app.Arrange
import com.kharazmic.app.MainActivity
import com.kharazmic.app.R
import com.kharazmic.app.databinding.ActivityValidateBinding

class ValidateActivity : AppCompatActivity() {

    private var phone: String = ""
    private lateinit var binding: ActivityValidateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_validate)
        intent.getStringExtra("phone")?.let { phone = it }
        val factory = LoginViewModelFactory(this)
        val viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        binding.viewModel = viewModel
        viewModel.timer()
        viewModel.status.observe(this, Observer {
            it?.let { status ->
                if (status)
                    startActivity(Intent(this, MainActivity::class.java))
                else
                    Toast.makeText(this, "code is not correct", Toast.LENGTH_SHORT).show()

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
            binding.resend.visibility = View.GONE
            viewModel.timer()
        }

        binding.wrongNumber.setOnClickListener {
            onBackPressed()
        }

    }


    override fun onBackPressed() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("phone", phone)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
