package com.example.catclickerapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.catclickerapp.databinding.ActivityClickerBinding

class ClickerActivity : AppCompatActivity() {

    // ViewBinding для доступа к элементам activity_clicker.xml
    private lateinit var binding: ActivityClickerBinding

    // Счётчик кликов по коту
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Включение edge-to-edge режима
        enableEdgeToEdge()

        // Инициализация ViewBinding
        binding = ActivityClickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Настройка обработчиков и начального состояния экрана
        setupListeners()
    }

    /**
     * Настройка элементов интерфейса и слушателей
     */
    private fun setupListeners() = with(binding) {

        // Устанавливаем изображение выбранного кота
        setupCatImage()

        // Инициализируем счётчик кликов
        updateCounter()

        // Кнопка "Назад" — закрывает текущую Activity
        backBtn.setOnClickListener {
            finish()
        }
    }

    /**
     * Получение изображения кота из Intent и установка его в ImageView
     */
    private fun setupCatImage() = with(binding) {

        // Получаем id изображения, переданный из MainActivity
        val imageRes = intent.getIntExtra(Constants.IMAGE_RES, -1)

        // Если изображение передано корректно — устанавливаем его
        if (imageRes != -1) {
            catClickerImage.setImageResource(imageRes)
        }
    }

    /**
     * Обновление счётчика кликов по изображению кота
     */
    @SuppressLint("SetTextI18n") // подавление предупреждения о конкатенации строк
    private fun updateCounter() = with(binding) {

        // При каждом клике по коту увеличиваем счётчик
        catClickerImage.setOnClickListener {
            countClick.text = "Счет: ${counter++}"
        }
    }
}