package com.kharazmic.app.main.profile.setting.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.kharazmic.app.Address
import com.kharazmic.app.R
import com.kharazmic.app.Utils
import com.kharazmic.app.databinding.FragmentContactBinding
import org.json.JSONObject


class ContactFragment : Fragment() {


    private lateinit var binding: FragmentContactBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        binding.back.setOnClickListener {
            this.findNavController().popBackStack()
        }
        binding.submit.setOnClickListener { sendTicket() }
    }


    private fun sendTicket() {
        if (binding.subject.text.isNullOrEmpty())
            YoYo.with(Techniques.Shake)
                .duration(1000)
                .playOn(binding.subject)
        else if (binding.description.text.isNullOrEmpty())
            YoYo.with(Techniques.Shake)
                .duration(1000)
                .playOn(binding.description)
        else {
            binding.submit.startAnimation()
            val info = JSONObject()
            info.put("subject", binding.subject.text.toString())
            info.put("description", binding.description.text.toString())

            val request =
                JsonObjectRequest(
                    Request.Method.POST,
                    Address().TicketAPI,
                    info,
                    Response.Listener {
                        Utils(context!!).showSnackBar(
                            color = ContextCompat.getColor(
                                context!!,
                                R.color.black
                            ),
                            snackView = binding.root,
                            msg = getString(R.string.success)
                        )
                        binding.subject.setText("")
                        binding.description.setText("")


                        binding.submit.revertAnimation()
                        binding.submit.background =
                            ContextCompat.getDrawable(context!!, R.drawable.login_btn)
                    },
                    Response.ErrorListener {
                        val error =
                            if (it is NoConnectionError || it is TimeoutError || it is NetworkError) {
                                getString(R.string.no_connection)
                            } else
                                getString(R.string.error)
                        Utils(context!!).showSnackBar(
                            color = ContextCompat.getColor(
                                context!!,
                                R.color.black
                            ),
                            snackView = binding.root,
                            msg = error
                        )
                        binding.submit.revertAnimation()
                        binding.submit.background =
                            ContextCompat.getDrawable(context!!, R.drawable.login_btn)

                    })

            val queue = Volley.newRequestQueue(context)
            queue.add(request)
        }

    }

}
