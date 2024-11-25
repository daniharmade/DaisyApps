package com.example.daisyapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.daisyapp.R
import com.example.daisyapp.view.ui.home.HomeFragment
import com.example.daisyapp.view.ui.profile.ProfileFragment
import com.example.daisyapp.view.ui.scan.ScanFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Set default fragment to HomeFragment
        openFragment(HomeFragment())

        // Set up BottomNavigationView
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.navigate_menu)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    openFragment(HomeFragment())
                    true
                }

                R.id.action_scan -> {
                    openFragment(ScanFragment())
                    true
                }

                R.id.action_history -> {
//                    openFragment(HistoryFragment())
                    true
                }

                R.id.action_profile -> {
                    openFragment(ProfileFragment())
                    true
                }

                else -> false
            }
        }

        // Handle WindowInsets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Function to replace the fragment
    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_fragment, fragment) // ID dari FrameLayout
            .commit()
    }
}
