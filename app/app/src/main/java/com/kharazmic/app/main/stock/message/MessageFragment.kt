package com.kharazmic.app.main.stock.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kharazmic.app.R
import com.kharazmic.app.databinding.FragmentMessageBinding


class MessageFragment : Fragment() {

    private var page = 0
    private lateinit var binding: FragmentMessageBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_message, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = MessageViewModelFactory(requireContext())
        val viewModel = ViewModelProvider(this, factory).get(MessageViewModel::class.java)

        val adapter = MessageRecyclerAdapter()


        viewModel.fetchMessage(page)

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            it?.let { isLoading ->
                binding.loading.visibility = if (isLoading)
                    View.VISIBLE
                else
                    View.GONE
            }
        })

        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = false
            page = 0
            viewModel.isFetched = false
            viewModel.fetchMessage(page)
        }

        binding.recyclerView.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(requireContext())
        }


        viewModel.messages.observe(viewLifecycleOwner, Observer {
            it?.let { messages ->
                adapter.submitList(messages)
            }
        })


        binding.back.setOnClickListener {
            this.findNavController().navigateUp()
        }

    }

}