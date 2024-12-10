package com.example.daisyapp.view.ui.scan

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.daisyapp.R
import com.example.daisyapp.data.helper.ImageClassifierHelper
import com.example.daisyapp.data.model.CancerHistory
import com.example.daisyapp.data.utils.FIRST_LABEL_RESULT_EXTRA
import com.example.daisyapp.data.utils.IMAGE_URI_EXTRA
import com.example.daisyapp.data.utils.getImageUri
import com.example.daisyapp.databinding.FragmentScanBinding
import com.example.daisyapp.view.viewmodel.factory.HistoryViewModelFactory
import com.example.daisyapp.view.viewmodel.model.HistoryViewModel
import com.example.daisyapp.view.viewmodel.model.ScanViewModel
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.tensorflow.lite.support.label.Category
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.io.File
import java.text.NumberFormat
import java.util.Date

class ScanFragment : Fragment() {

    private lateinit var binding: FragmentScanBinding
    private val viewModel: ScanViewModel by viewModels()

    private var currentImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScanBinding.inflate(inflater, container, false)

        viewModel.currentImageUri.observe(viewLifecycleOwner) { uri ->
            if (uri != null) {
                binding.uploadedImagePreview.setImageURI(uri)
                currentImageUri = uri
            }
        }

        val cancerHistoryViewModelFactory = HistoryViewModelFactory.getInstance(requireContext())
        val cancerHistoryViewModel: HistoryViewModel by viewModels { cancerHistoryViewModelFactory }

        with(binding) {
            upload.setOnClickListener { showPopup() }
            analyze.setOnClickListener {
                currentImageUri?.let { uri ->
                    analyzeImage(uri, cancerHistoryViewModel)
                } ?: showToast("Image Uri is null")
            }
        }

        return binding.root
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
            startCamera()
            dialog.dismiss()
        }

        btnGalery.setOnClickListener {
            startGallery()
            dialog.dismiss()
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireContext())
        if (currentImageUri != null) {
            launcherIntentCamera.launch(currentImageUri)
        } else {
            showToast("Failed to get image URI for camera")
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            currentImageUri?.let { imageUri ->
                // Pastikan URI sudah benar dan diproses
                imageCropper(imageUri)
            }
        } else {
            showToast("Gagal mengambil gambar")
        }
    }

    private val startCropper =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val resultUri = UCrop.getOutput(result.data!!)
                if (resultUri != null) {
                    currentImageUri = resultUri
                    showImage()
                }
            } else if (result.resultCode == UCrop.RESULT_ERROR) {
                val error = UCrop.getError(result.data!!)
                showToast("Error: ${error?.localizedMessage}")
            }
        }

    private fun imageCropper(uri: Uri) {
        val timestamp = Date().time
        val cachedImage = File(requireContext().cacheDir, "cropped_image_${timestamp}.jpg")
        val destinationUri = Uri.fromFile(cachedImage)

        val cropper = UCrop.of(uri, destinationUri).withAspectRatio(1f, 1f)
        cropper.getIntent(requireContext()).apply {
            startCropper.launch(this)
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.uploadedImagePreview.setImageURI(it)
        }
    }

    private fun startGallery() =
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            launchUCrop(uri)
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun launchUCrop(uri: Uri) {
        val timestamp = Date().time
        val cachedImage = File(requireContext().cacheDir, "cropped_image_${timestamp}.jpg")

        val destinationUri = Uri.fromFile(cachedImage)

        val uCrop = UCrop.of(uri, destinationUri).withAspectRatio(1f, 1f)

        uCrop.getIntent(requireContext()).apply {
            launcherUCrop.launch(this) // "this" keyword is reference to intent, not activity
        }
    }

    private val launcherUCrop =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val resultUri = UCrop.getOutput(result.data!!)
                if (resultUri != null) {
                    viewModel.setCurrentImageUri(resultUri)
                    currentImageUri = resultUri
                }
            } else if (result.resultCode == UCrop.RESULT_ERROR) {
                val error = UCrop.getError(result.data!!)
                showToast("Error: ${error?.localizedMessage}")
            }
        }

    private fun analyzeImage(imageUri: Uri, cancerHistoryViewModel: HistoryViewModel) {
        CoroutineScope(Dispatchers.Main).launch {
            showProgressAndDisableButtons(true)
            var cancerHistory = CancerHistory(label = "", confidenceScore = "", image = "")

            try {
                withContext(Dispatchers.IO) {
                    val imageClassifierHelper = ImageClassifierHelper(context = requireContext(),
                        classifierListener = object : ImageClassifierHelper.ClassifierListener {
                            override fun onError(error: String) {
                                showToast("Error: $error")
                            }

                            override fun onResults(
                                results: List<Classifications>?,
                                inferenceTime: Long
                            ) {
                                results?.let { listClassification ->
                                    if (listClassification.isNotEmpty() && listClassification[0].categories.isNotEmpty()) {
                                        val sortedCategories =
                                            listClassification[0].categories.sortedByDescending { it?.score }
                                        cancerHistory = CancerHistory(
                                            label = sortedCategories[0].label,
                                            confidenceScore = formatNumberToPercent(sortedCategories[0].score),
                                            image = imageUri.toString()
                                        )
                                        moveToResult(sortedCategories)
                                    }
                                }
                            }
                        })

                    imageClassifierHelper.classifyStaticImage(imageUri)
                    cancerHistoryViewModel.insertCancerHistory(cancerHistory)
                    withContext(Dispatchers.Main) {
                        showProgressAndDisableButtons(false)
                    }
                }
            } catch (e: Exception) {
                Log.d("ScanFragment", e.message.toString())
            }
        }
    }

    private fun moveToResult(analyzeResult: List<Category>) {
        val intent = Intent(requireContext(), ResultActivity::class.java)
        intent.putExtra(IMAGE_URI_EXTRA, currentImageUri.toString())
        intent.putExtra(FIRST_LABEL_RESULT_EXTRA, analyzeResult[0].label)
        intent.putExtra("score", analyzeResult[0].score)
        startActivity(intent)
    }

    private fun showProgressAndDisableButtons(isActive: Boolean) {
        with(binding) {
            progressBar.visibility = if (isActive) View.VISIBLE else View.INVISIBLE
//            galleryButton.isEnabled = !isActive
            analyze.isEnabled = !isActive
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun formatNumberToPercent(score: Float): String =
        NumberFormat.getPercentInstance().format(score)
}
