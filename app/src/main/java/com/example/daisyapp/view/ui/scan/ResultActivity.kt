package com.example.daisyapp.view.ui.scan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.daisyapp.data.utils.FIRST_LABEL_RESULT_EXTRA
import com.example.daisyapp.data.utils.IMAGE_URI_EXTRA
import com.example.daisyapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cancerImage = intent.getStringExtra(IMAGE_URI_EXTRA)
        val firstLabelResult = intent.getStringExtra(FIRST_LABEL_RESULT_EXTRA)

        with(binding) {
            resultImage.setImageURI(cancerImage?.toUri())  // Pastikan URI ini valid
            predictionText.text = firstLabelResult
        }
    }
}
