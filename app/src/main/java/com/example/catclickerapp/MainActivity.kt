package com.example.catclickerapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.catclickerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding // binding


    private val cats = listOf(
        "Выберите кота",
        "Черный кот",
        "Белый кот",
        "Рыжий кот"
    )

    private val catsImages = listOf(
        R.drawable.ic_launcher_background,
        R.drawable.black_cat,
        R.drawable.white_cat,
        R.drawable.orange_cat
    )
    private var selectedCatImage: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinner()
        setupListeners()
    }

    private fun setupSpinner() = with(binding) {
        val adapter = ArrayAdapter(
            this@MainActivity,
            android.R.layout.simple_spinner_item,
            cats
        ).apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        catSpinner.adapter = adapter
    }

    private fun setupListeners() = with(binding) {
        catSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) Toast.makeText(
                    this@MainActivity,
                    cats[position],
                    Toast.LENGTH_SHORT
                ).show()

                selectedCatImage = catsImages[position]

                updateCatImage()
                updateButton()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        createCatBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, ClickerActivity::class.java)
            if (selectedCatImage != null) {
                intent.putExtra(Constants.IMAGE_RES, selectedCatImage)
                startActivity(intent)
            }
        }
    }

    private fun updateCatImage() = with(binding) {
        catImage.setImageResource(selectedCatImage ?: R.drawable.ic_launcher_background)
    }

    private fun updateButton() = with(binding) {
        createCatBtn.isEnabled = catSpinner.selectedItemPosition > 0
    }
}