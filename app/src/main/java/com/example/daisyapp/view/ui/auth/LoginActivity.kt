package com.example.daisyapp.view.ui.auth

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.daisyapp.MainActivity
import com.example.daisyapp.data.model.UserModel
import com.example.daisyapp.data.preference.Result
import com.example.daisyapp.databinding.ActivityLoginBinding
import com.example.daisyapp.view.viewmodel.factory.AuthViewModelFactory
import com.example.daisyapp.view.viewmodel.model.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<LoginViewModel> {
        AuthViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        setupView()
        setupAction()

    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            login()
        }

        binding.resetButton.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.login(email, password)
        viewModel.loginResult.observe(this) { result ->
            when (result) {
                is Result.DataLoading -> {
                    showLoading(true)
                }
                is Result.DataSuccess -> {
                    showLoading(false)
                    val name = result.data.loginResult.name
                    val token = result.data.loginResult.token
                    val image = result.data.loginResult.photoURL
                    // Save session information
                    viewModel.saveSession(UserModel(email, token))
                    saveUserInfo(name, email, image)
                    // Directly navigate to MainActivity without showing a dialog
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
                is Result.DataError -> {
                    showLoading(false)
                    // Show a toast instead of the dialog for login failure
                    Toast.makeText(this, "Login failed, please check your email and password again.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveUserInfo(name: String, email: String, image: String) {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("username", name)
        editor.putString("email", email)
//        editor.putString("image", image )
        editor.apply()
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
