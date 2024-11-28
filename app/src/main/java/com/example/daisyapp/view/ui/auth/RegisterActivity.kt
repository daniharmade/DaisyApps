package com.example.daisyapp.view.ui.auth

import android.app.AlertDialog
import android.app.ProgressDialog.show
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.daisyapp.R
import com.example.daisyapp.databinding.ActivityRegisterBinding
import com.example.daisyapp.view.viewmodel.factory.AuthViewModelFactory
import com.example.daisyapp.view.viewmodel.model.RegisterViewModel
import com.example.daisyapp.data.preference.Result

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val viewModel by viewModels<RegisterViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()

    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            processRegister()
        }

        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.passwordConfirmationEditText.setPasswordToMatch(s.toString())
            }
            override fun afterTextChanged(s: Editable) {}
        })

    }

    private fun processRegister() {
        val name = binding.nameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val confirmPassword = binding.passwordConfirmationEditText.text.toString()

        if (password != confirmPassword) {
            binding.passwordConfirmationEditTextLayout.error = getString(R.string.error_pw_mismatch)
            return
        } else {
            binding.passwordConfirmationEditTextLayout.error = null
        }

        viewModel.register(name, email, password)
        viewModel.registerResult.observe(this) { result ->
            when (result) {
                is Result.DataLoading -> {
                    showLoading(true)
                }
                is Result.DataSuccess -> {
                    showLoading(false)
                    AlertDialog.Builder(this).apply {
                        setTitle("Berhasil")
                        setMessage("Registrasi berhasil")
                        setNeutralButton("OK") { _, _ ->
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        create()
                        show()
                    }
                }
                is Result.DataError -> {
                    showLoading(false)
                    AlertDialog.Builder(this).apply {
                        setTitle("Gagal")
                        setMessage("Registrasi gagal: ${result.error}")
                        setNeutralButton("Coba Lagi", null)
                        create()
                        show()
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}