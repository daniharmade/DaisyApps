package com.example.daisyapp.view.ui.scan

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.daisyapp.R
import com.example.daisyapp.databinding.FragmentScanBinding

class ScanFragment : Fragment() {
    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.upload.setOnClickListener{
            Log.d("ScanFragment", "Add Image Button Clicked")
            showPopup()
        }
    }

    private fun showPopup() {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.pop_up_action_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        val btnCamera = dialog.findViewById<Button>(R.id.camera_btn)
        val btnGalery = dialog.findViewById<Button>(R.id.gallery_btn)

        btnCamera.setOnClickListener {
//            startCamera()
            dialog.dismiss()
        }

        btnGalery.setOnClickListener {
//            startGallery()
            dialog.dismiss()
        }
    }
}