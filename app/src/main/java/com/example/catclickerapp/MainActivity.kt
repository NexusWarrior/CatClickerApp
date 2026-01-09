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

    // ViewBinding для доступа к элементам activity_main.xml
    private lateinit var binding: ActivityMainBinding

    // Список названий котов для Spinner
    private val cats = listOf(
        "Выберите кота",   // позиция 0 — заглушка
        "Черный кот",
        "Белый кот",
        "Рыжий кот"
    )

    // Список изображений котов (индексы совпадают с cats)
    private val catsImages = listOf(
        R.drawable.ic_launcher_background, // изображение по умолчанию
        R.drawable.black_cat,
        R.drawable.white_cat,
        R.drawable.orange_cat
    )

    // Выбранное изображение кота (null — если кот не выбран)
    private var selectedCatImage: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Включение edge-to-edge режима (контент под системными барами)
        enableEdgeToEdge()

        // Инициализация ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Настройка Spinner
        setupSpinner()

        // Установка обработчиков событий
        setupListeners()
    }


    // Настройка Spinner со списком котов
    private fun setupSpinner() = with(binding) {
        val adapter = ArrayAdapter(
            this@MainActivity,
            android.R.layout.simple_spinner_item,
            cats
        ).apply {
            // Layout для выпадающего списка
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        // Привязка адаптера к Spinner
        catSpinner.adapter = adapter
    }

    // Настройка слушателей для Spinner и кнопки
    private fun setupListeners() = with(binding) {

        // Обработчик выбора элемента Spinner
        catSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Если выбран не первый элемент — показываем Toast
                if (position > 0) {
                    Toast.makeText(
                        this@MainActivity,
                        cats[position],
                        Toast.LENGTH_SHORT
                    ).show()
                }

                // Сохраняем выбранное изображение кота
                selectedCatImage = catsImages[position]

                // Обновляем изображение кота на экране
                updateCatImage()

                // Активируем / деактивируем кнопку
                updateButton()
            }

            // Если ничего не выбрано — ничего не делаем
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Обработчик нажатия на кнопку "Создать кота"
        createCatBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, ClickerActivity::class.java)

            // Если кот выбран — передаём его изображение в ClickerActivity
            if (selectedCatImage != null) {
                intent.putExtra(Constants.IMAGE_RES, selectedCatImage)
                startActivity(intent)
            }
        }
    }

    // Обновление изображения кота в ImageView
    private fun updateCatImage() = with(binding) {
        catImage.setImageResource(
            selectedCatImage ?: R.drawable.ic_launcher_background
        )
    }

    /**
     * Обновление состояния кнопки:
     * активна только если выбран кот
     */
    private fun updateButton() = with(binding) {
        createCatBtn.isEnabled = catSpinner.selectedItemPosition > 0
    }
}