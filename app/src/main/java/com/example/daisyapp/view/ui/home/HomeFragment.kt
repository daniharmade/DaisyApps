package com.example.daisyapp.view.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.daisyapp.R
import com.example.daisyapp.databinding.FragmentHomeBinding
import com.example.daisyapp.view.ui.scan.ScanFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.quickDiagnosisButton.setOnClickListener {
            // Memulai FragmentScan menggunakan FragmentTransaction
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container_fragment, ScanFragment())
            fragmentTransaction.addToBackStack(null) // Menambahkan transaksi ke back stack
            fragmentTransaction.commit()
        }
    }

}