package com.kharazmic.app.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kharazmic.app.MainActivity
import com.kharazmic.app.R
import com.kharazmic.app.databinding.ActivityValidateBinding

class ValidateActivity : AppCompatActivity() {


    private lateinit var binding: ActivityValidateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validate)

        val factory = LoginViewModelFactory(this)
        val viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        binding.viewModel = viewModel


        viewModel.status.observe(this, Observer {
            it?.let { status ->
                if (status)
                    startActivity(Intent(this, MainActivity::class.java))
                else
                    Toast.makeText(this, "code is not correct", Toast.LENGTH_SHORT).show()

            }
        })


    }
}
