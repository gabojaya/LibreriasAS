package com.gabriel.jaya.libreriasimagenes

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.yalantis.ucrop.UCrop
import java.io.File

class UCropActivity : AppCompatActivity() {

    private lateinit var imagePickerLauncher: ActivityResultLauncher<String>
    private lateinit var imageViewOriginal: ImageView
    private lateinit var imageViewResult: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ucrop)
        title = "Librería uCrop"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val btnSelectImage: Button = findViewById(R.id.btnSelectImageUCrop)
        imageViewOriginal = findViewById(R.id.imageViewOriginalUCrop)
        imageViewResult = findViewById(R.id.imageViewResultUCrop)

        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { selectedUri ->
                imageViewOriginal.setImageURI(selectedUri)
                startCrop(selectedUri)
            }
        }

        btnSelectImage.setOnClickListener {
            imageViewOriginal.setImageDrawable(null)
            imageViewResult.setImageDrawable(null)
            imagePickerLauncher.launch("image/*")
        }
    }

    private fun startCrop(sourceUri: Uri) {
        val destinationFileName = "UcropSample_${System.currentTimeMillis()}.jpg"
        val destinationUri = Uri.fromFile(File(cacheDir, destinationFileName))

        val options = UCrop.Options().apply {
            setCompressionQuality(90)
            setToolbarTitle("Recortar Imagen")
            setToolbarColor(ContextCompat.getColor(this@UCropActivity, R.color.purple_500))
            setStatusBarColor(ContextCompat.getColor(this@UCropActivity, R.color.purple_700))
            setActiveControlsWidgetColor(ContextCompat.getColor(this@UCropActivity, R.color.purple_200))
        }

        UCrop.of(sourceUri, destinationUri)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(1080, 1080)
            .withOptions(options)
            .start(this)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == UCrop.REQUEST_CROP) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val resultUri = UCrop.getOutput(data)
                imageViewResult.setImageURI(resultUri)
                Toast.makeText(this, "Imagen recortada con éxito", Toast.LENGTH_SHORT).show()
            } else if (resultCode == UCrop.RESULT_ERROR) {
                val cropError = UCrop.getError(data!!)
                Log.e("UCropError", "Error al recortar: ", cropError)
                Toast.makeText(this, "Error al recortar: ${cropError?.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}