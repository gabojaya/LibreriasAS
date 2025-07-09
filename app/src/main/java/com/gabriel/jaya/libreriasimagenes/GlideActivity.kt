package com.gabriel.jaya.libreriasimagenes

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

class GlideActivity : AppCompatActivity() {

    private lateinit var imagePickerLauncher: ActivityResultLauncher<String>
    private lateinit var imageViewOriginal: ImageView
    private lateinit var imageViewTransformed: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glide)
        title = "Libreria Glide"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val btnSelectImage: Button = findViewById(R.id.btnSelectImageGlide)
        imageViewOriginal = findViewById(R.id.imageViewOriginal)
        imageViewTransformed = findViewById(R.id.imageViewTransformed)

        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->

            uri?.let { selectedUri ->
                loadImages(selectedUri)
            }
        }

        btnSelectImage.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }
    }

    private fun loadImages(imageUri: Uri) {

        Glide.with(this)
            .load(imageUri)
            .into(imageViewOriginal)

        Glide.with(this)
            .load(imageUri)
            .apply(
                RequestOptions()
                    .error(R.drawable.ic_launcher_foreground)
                    .circleCrop()
            )
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageViewTransformed)
    }
}