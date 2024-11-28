package com.example.daisyapp.view.ui.auth

import android.app.AlertDialog
import android.app.ProgressDialog.show
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.daisyapp.MainActivity
import com.example.daisyapp.data.model.UserModel
import com.example.daisyapp.databinding.ActivityLoginBinding
import com.example.daisyapp.view.viewmodel.factory.AuthViewModelFactory
import com.example.daisyapp.view.viewmodel.model.LoginViewModel
import com.example.daisyapp.data.preference.Result

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

        viewModel.login(email, password)
        viewModel.loginResult.observe(this){result ->
            when(result){
                is Result.DataLoading -> {
                    showLoading(true)
                }
                is Result.DataSuccess -> {
                    showLoading(false)
                    val name = result.data.loginResult.name
                    val token = result.data.loginResult.token
                    val image = result.data.loginResult.photoURL
//                    viewModel.saveSession(UserModel(email, token, image))
                    viewModel.saveSession(UserModel(email, token))
                    saveUserInfo(name, email, image)
                    AlertDialog.Builder(this).apply {
                        setTitle("Yeah!")
                        setMessage("$name Sudah Berhasil Login Nih. Yuk, login dan jaga kesehatan kulitmu.")
                        setPositiveButton("Lanjut") { _, _ ->
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
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
                        setTitle("Ups!")
                        setMessage("Login Gagal, Harap Periksa Kembali Email dan Password Anda.")
                        setNeutralButton("Kembali") { _, _ ->
                            val intent = Intent(this@LoginActivity, LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            showLoading(false)
                            finish()
                        }
                        create()
                        show()
                    }
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
