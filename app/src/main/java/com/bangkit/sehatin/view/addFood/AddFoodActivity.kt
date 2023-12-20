package com.bangkit.sehatin.view.addFood

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.bangkit.sehatin.R
import com.bangkit.sehatin.data.network.response.FoodResponse
import com.bangkit.sehatin.data.network.retrofit.ApiConfig
import com.bangkit.sehatin.data.network.retrofit.ApiService
import com.bangkit.sehatin.databinding.ActivityAddFoodBinding
import com.bangkit.sehatin.utils.Utils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class AddFoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddFoodBinding
    private var currentImageUri: Uri? = null

    private var apiService: ApiService? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { startCamera() }
        binding.uploadUpload.setOnClickListener { uploadImage() }

    }

    suspend fun addImage(imageFile: File): FoodResponse {
        apiService = ApiConfig.getApiService()
        val requestImageFile = imageFile.asRequestBody("image/jpg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "file",
            imageFile.name,
            requestImageFile
        )

        val response = apiService!!.uploadImage(multipartBody)

        return response

    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = Utils().uriToFile(uri, this).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")

            runBlocking {
                val response = addImage(imageFile)
                if (response.code == 200) {
                    // Pass image data to FoodResultActivity
                    val intent = Intent(this@AddFoodActivity, FoodResultActivity::class.java)
                    intent.putExtra("imageUri", currentImageUri.toString())  // Pass image URI as a string
                    intent.putExtra("data", response)
                    startActivity(intent)
                }
            }
        }
    }


    private fun startCamera() {
        currentImageUri = Utils().getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }


    private fun showDialogUpload(message: String) {
        AlertDialog.Builder(this@AddFoodActivity).apply {
            setTitle("Error")
            setMessage(message)
            setPositiveButton("Ulang") { _, _ ->
            }
            create()
            show()
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            showDialogUpload("No media selected")
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun File.reduceFileImage(): File {
        val maximalSize = 1000000
        val file = this
        val bitmap = BitmapFactory.decodeFile(file.path)
        var compressQuality = 100
        var streamLength: Int
        do {
            val bmpStream = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
            val bmpPicByteArray = bmpStream.toByteArray()
            streamLength = bmpPicByteArray.size
            compressQuality -= 5
        } while (streamLength > maximalSize)
        bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
        return file
    }


}