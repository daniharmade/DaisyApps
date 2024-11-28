package com.example.daisyapp.view.ui.profile

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.daisyapp.databinding.FragmentProfileBinding
import android.provider.Settings
import android.widget.Button
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.daisyapp.R
import com.example.daisyapp.view.viewmodel.factory.AuthViewModelFactory
import com.example.daisyapp.view.viewmodel.model.LoginViewModel
import com.example.daisyapp.view.viewmodel.model.MainViewModel

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MainViewModel> {
        AuthViewModelFactory.getInstance(requireContext())
    }

    private val nameviewModel by viewModels<LoginViewModel> {
        AuthViewModelFactory.getInstance(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayProfile()

        binding.profileCardAbout.setOnClickListener{
            showPopupAbout()
        }

        binding.profileCardLanguange.setOnClickListener{
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        binding.profileCardPrivacy.setOnClickListener{
            startActivity(Intent(requireContext(), PrivacyActivity::class.java))
        }

        binding.profileCardRequirement.setOnClickListener{
            startActivity(Intent(requireContext(), RequirementActivity::class.java))
        }

        binding.cardLogout.setOnClickListener{
            showPopupLogout()
        }
    }

    private fun loadUsername(): String? {
        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("username", null)
    }

    private fun loadEmail(): String? {
        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("email", null)
    }

    private fun displayProfile() {
        val username = loadUsername()
        val email = loadEmail()

        if (username != null) {
            binding.nameProfile.text = username
        } else {
            binding.nameProfile.text = getString(R.string.username_not_found)
        }

        if (email != null) {
            binding.emailProfile.text = email
        } else {
            binding.emailProfile.text = getString(R.string.email_not_found)
        }
    }


    private fun showPopupAbout() {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.pop_up_about_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnClose = dialog.findViewById<View>(R.id.button_close)

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showPopupLogout() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.pop_up_logout_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnCancel = dialog.findViewById<Button>(R.id.btn_cancel)
        val btnLogout = dialog.findViewById<Button>(R.id.btn_logout)

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnLogout.setOnClickListener {
            viewModel.logout()
            dialog.dismiss()
        }

        dialog.show()
    }
}