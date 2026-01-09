package com.example.catclickerapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.catclickerapp.databinding.ActivityClickerBinding

class ClickerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClickerBinding

    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityClickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() = with(binding) {
        setupCatImage()
        updateCounter()
        backBtn.setOnClickListener { finish() }
    }

    private fun setupCatImage() = with(binding) {
        val imageRes = intent.getIntExtra(Constants.IMAGE_RES, -1)
        if (imageRes != -1) catClickerImage.setImageResource(imageRes)
    }

    @SuppressLint("SetTextI18n")
    private fun updateCounter() = with(binding) {
        catClickerImage.setOnClickListener { countClick.text = "Счет: ${counter++}" }
    }
}