package com.kharazmic.app.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.kharazmic.app.Address
import com.kharazmic.app.Arrange
import com.kharazmic.app.R
import com.kharazmic.app.databinding.ActivityLoginBinding
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        val factory = LoginViewModelFactory(this)
        val viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.status.observe(this, Observer {
            it?.let { status ->
                if (!status)
                    Toast.makeText(this, "some thing went Wrong!", Toast.LENGTH_LONG).show()
                else
                    Toast.makeText(this, "GO to next page!", Toast.LENGTH_LONG).show()
            }

        })
    }

}
