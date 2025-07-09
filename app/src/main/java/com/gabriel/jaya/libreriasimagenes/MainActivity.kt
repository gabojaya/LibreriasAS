package com.gabriel.jaya.libreriasimagenes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnGlide: Button = findViewById(R.id.btnGlide)
        val btnUCrop: Button = findViewById(R.id.btnUCrop)
        val btnGpuImage: Button = findViewById(R.id.btnGpuImage)

        btnGlide.setOnClickListener {
            val intent = Intent(this, GlideActivity::class.java)
            startActivity(intent)
        }

        btnUCrop.setOnClickListener {
            val intent = Intent(this, UCropActivity::class.java)
            startActivity(intent)
        }

        btnGpuImage.setOnClickListener {
            val intent = Intent(this, GpuImageActivity::class.java)
            startActivity(intent)
        }
    }
}