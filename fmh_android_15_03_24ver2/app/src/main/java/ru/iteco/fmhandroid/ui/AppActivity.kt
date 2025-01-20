package ru.iteco.fmhandroid.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.iteco.fmhandroid.R
import ru.iteco.fmhandroid.dto.News
import ru.iteco.fmhandroid.viewmodel.NewsModel

@AndroidEntryPoint
class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)

        val newsModel: NewsModel by viewModels()

        val categories =
            listOf(
                News.Category(1, "Информация", false),
                News.Category(2, "Юбилей", false),
                News.Category(3, "Оплата труда", false),
                News.Category(4, "Трудовой союз", false),
                News.Category(5, "Торжество", false),
                News.Category(6, "Расслабление", false),
                News.Category(7, "Признательность", false),
                News.Category(8, "Требуется помощь", false)
            )

        newsModel.retreiveListNewsCategories(categories)
    }
}
