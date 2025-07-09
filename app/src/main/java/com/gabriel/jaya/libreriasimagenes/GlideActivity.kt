package com.gabriel.jaya.libreriasimagenes

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

class GlideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glide)
        title = "Demo de Glide"
        val imageViewGlide: ImageView = findViewById(R.id.imageViewGlide)

        // URL de una imagen de ejemplo
        val imageUrl = "https://picsum.photos/seed/picsum/800/800"

        Glide.with(this)
            .load(imageUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .circleCrop()
            )
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageViewGlide) // Usar la variable del ImageView
    }
}