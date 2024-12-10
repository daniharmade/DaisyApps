package com.example.daisyapp.view.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daisyapp.data.adapter.HistoryAdapter
import com.example.daisyapp.databinding.FragmentHistoryBinding
import com.example.daisyapp.view.viewmodel.factory.HistoryViewModelFactory
import com.example.daisyapp.view.viewmodel.model.HistoryViewModel

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryViewModel by viewModels {
        HistoryViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.getAllCancerHistory().observe(viewLifecycleOwner) { historyList ->
            if (historyList != null) {
                val adapter = HistoryAdapter(historyList.toMutableList(), requireContext(), viewModel)
                binding.rvHistory.adapter = adapter
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
