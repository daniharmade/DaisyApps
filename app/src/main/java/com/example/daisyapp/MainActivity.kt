package com.example.daisyapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.daisyapp.R
import com.example.daisyapp.databinding.ActivityMainBinding
import com.example.daisyapp.view.ui.history.HistoryFragment
import com.example.daisyapp.view.ui.home.HomeFragment
import com.example.daisyapp.view.ui.profile.ProfileFragment
import com.example.daisyapp.view.ui.scan.ResultActivity
import com.example.daisyapp.view.ui.scan.ScanFragment
import com.example.daisyapp.view.ui.welcome.WelcomeActivity
import com.example.daisyapp.view.viewmodel.factory.AuthViewModelFactory
import com.example.daisyapp.view.viewmodel.model.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // ViewModel untuk session check
    private val viewModel by viewModels<MainViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Mengaktifkan edge-to-edge UI
        enableEdgeToEdge()

        // Inflate layout menggunakan ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Periksa session login
        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }

        // Setup tampilan layar dan fragment default
        setupView()
        switchFragment(HomeFragment())

        // Penanganan navigasi jika ada data tambahan dari intent
        handleIntentNavigation()

        // Setup navigasi BottomNavigationView
        binding.navigateMenu.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> switchFragment(HomeFragment())
                R.id.action_history -> switchFragment(HistoryFragment())
                R.id.action_scan -> switchFragment(ScanFragment())
                R.id.action_profile -> switchFragment(ProfileFragment())
                else -> false
            }
            true
        }
    }

    // Fungsi untuk mengganti fragment
    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_fragment, fragment)
            .commit()
    }

    // Setup tampilan layar penuh
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
    }

    // Penanganan navigasi dari intent
    private fun handleIntentNavigation() {
        val fromArticle = intent.getBooleanExtra("navigateToArticle", false)
        val fromHistory = intent.getBooleanExtra("navigateToHistory", false)
        val fromScan = intent.getBooleanExtra("navigateToScan", false)

        when {
//            fromArticle -> navigateToFragment(ArticleFragment(), R.id.action_article)
//            fromHistory -> navigateToFragment(HistoryFragment(), R.id.action_history)
            fromScan -> navigateToFragment(ScanFragment(), R.id.action_scan)
        }
    }

    // Navigasi langsung ke fragment tertentu
    private fun navigateToFragment(fragment: Fragment, menuItemId: Int) {
        switchFragment(fragment)
        setSelectedItem(menuItemId)
    }

    // Set item terpilih di BottomNavigationView
    private fun setSelectedItem(itemId: Int) {
        binding.navigateMenu.selectedItemId = itemId
    }
}
