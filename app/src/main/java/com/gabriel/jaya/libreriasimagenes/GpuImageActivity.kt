package com.gabriel.jaya.libreriasimagenes

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.GPUImageView
import jp.co.cyberagent.android.gpuimage.filter.*

class GpuImageActivity : AppCompatActivity() {

    private var currentFilter: GPUImageFilter = GPUImageFilter()

    private lateinit var gpuImageView: GPUImageView
    private lateinit var seekBar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gpu_image)
        title = "Libreria GPUImage"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        gpuImageView = findViewById(R.id.gpuImageView)
        seekBar = findViewById(R.id.seekBar)
        val btnFilterNone: Button = findViewById(R.id.btnFilterNone)
        val btnFilterSepia: Button = findViewById(R.id.btnFilterSepia)
        val btnFilterGrayscale: Button = findViewById(R.id.btnFilterGrayscale)
        val btnFilterVignette: Button = findViewById(R.id.btnFilterVignette)
        val btnFilterInvert: Button = findViewById(R.id.btnFilterInvert)

        val sampleBitmap = BitmapFactory.decodeResource(resources, R.drawable.placeholder_image)
        gpuImageView.setScaleType(GPUImage.ScaleType.CENTER_INSIDE)
        gpuImageView.setImage(sampleBitmap)

        btnFilterNone.setOnClickListener { switchFilterTo(GPUImageFilter()) }
        btnFilterSepia.setOnClickListener { switchFilterTo(GPUImageSepiaToneFilter()) }
        btnFilterGrayscale.setOnClickListener { switchFilterTo(GPUImageGrayscaleFilter()) }
        btnFilterVignette.setOnClickListener { switchFilterTo(GPUImageVignetteFilter()) }
        btnFilterInvert.setOnClickListener { switchFilterTo(GPUImageColorInvertFilter()) }

        setupSeekBar()
    }

    private fun switchFilterTo(filter: GPUImageFilter) {
        currentFilter = filter
        gpuImageView.filter = currentFilter
        seekBar.progress = 50
        adjustFilter(50)
    }

    private fun setupSeekBar() {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    adjustFilter(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        seekBar.progress = 50
    }

    private fun adjustFilter(progress: Int) {
        val intensity = progress / 100f
        when (val filter = currentFilter) {
            is GPUImageSepiaToneFilter -> filter.setIntensity(intensity * 2.0f)
            is GPUImageVignetteFilter -> {
                filter.setVignetteCenter(android.graphics.PointF(0.5f, 0.5f))
                filter.setVignetteColor(floatArrayOf(0.0f, 0.0f, 0.0f))
                filter.setVignetteStart(intensity * 0.5f)
                filter.setVignetteEnd(0.75f)
            }
        }
        gpuImageView.requestRender()
    }
}