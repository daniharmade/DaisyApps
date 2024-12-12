package com.example.daisyapp.view.ui.scan

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.daisyapp.R
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
            resultImage.setImageURI(cancerImage?.toUri()) // Pastikan URI ini valid
            predictionText.text = firstLabelResult

            when (firstLabelResult) {
                "Cacar Air" -> {
                    recommendationImage.setImageResource(R.drawable.obatcacarair)
                    namaObat.text = getString(R.string.nama_obat_cacarair)
                    recommendationText.text = getString(R.string.cara_pemakaian_obat_cacarair)
                    catatanText.text = getString(R.string.catatan_obat_cacarair)
                }
                "Biduran" -> {
                    recommendationImage.setImageResource(R.drawable.obatbiduran)
                    namaObat.text = getString(R.string.nama_obat_biduran)
                    recommendationText.text = getString(R.string.cara_pemakaian_obat_biduran)
                    catatanText.text = getString(R.string.catatan_obat_biduran)
                }
                "Bisul" -> {
                    recommendationImage.setImageResource(R.drawable.obatbisul)
                    namaObat.text = getString(R.string.nama_obat_bisul)
                    recommendationText.text = getString(R.string.cara_pemakaian_obat_bisul)
                    catatanText.text = getString(R.string.catatan_obat_bisul)
                }
                "Jerawat" -> {
                    recommendationImage.setImageResource(R.drawable.obatjerawat)
                    namaObat.text = getString(R.string.nama_obat_jerawat)
                    recommendationText.text = getString(R.string.cara_pemakaian_obat_jerawat)
                    catatanText.text = getString(R.string.catatan_obat_jerawat)
                }
                "Kurap" -> {
                    recommendationImage.setImageResource(R.drawable.obatkurap)
                    namaObat.text = getString(R.string.nama_obat_kurap)
                    recommendationText.text = getString(R.string.cara_pemakaian_obat_kurap)
                    catatanText.text = getString(R.string.catatan_obat_kurap)
                }
                "Panu" -> {
                    recommendationImage.setImageResource(R.drawable.obatpanu)
                    namaObat.text = getString(R.string.nama_obat_panu)
                    recommendationText.text = getString(R.string.cara_pemakaian_obat_panu)
                    catatanText.text = getString(R.string.catatan_obat_panu)
                }
                else -> {
                    recommendationImage.setImageResource(R.drawable.logo)
                    recommendationText.text = getString(R.string.normal)
                    carapakaiLabel.visibility = View.GONE
                    catatanLabel.visibility = View.GONE
                    catatanText.visibility = View.GONE
                }
            }
        }

    }
}