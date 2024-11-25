package com.example.daisyapp.view.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.daisyapp.R
import com.example.daisyapp.databinding.ActivityRequirementBinding

class RequirementActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRequirementBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequirementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonBack.setOnClickListener {
            onBackPressed()
        }

        // aksi klik pada image_email
        binding.imageEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:daniharmade@gmail.com")
            startActivity(intent)
        }

        // aksi klik pada image_contact
        binding.imageContact.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("tel:+6287796622516")
            startActivity(intent)
        }
    }

    // intent email
    fun sendEmail(view: View) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:daniharmade@gmail.com")
        startActivity(intent)
    }

    // intent kontak
    fun openContacts(view: View) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("tel:+6287796622516")
        startActivity(intent)
    }

}