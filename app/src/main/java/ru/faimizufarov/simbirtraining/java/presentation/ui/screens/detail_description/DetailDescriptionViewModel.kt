package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.detail_description

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.faimizufarov.data.repository.NewsRepositoryImpl
import ru.faimizufarov.domain.models.News

class DetailDescriptionViewModel(
    private val newsRepositoryImpl: NewsRepositoryImpl,
) : ViewModel() {
    private val _newsData: MutableLiveData<News> = MutableLiveData()
    val newsData: LiveData<News> = _newsData

    fun getNewsDetailDescription(id: String) {
        viewModelScope.launch {
            newsRepositoryImpl.newsListFlow.collect { newsList ->
                _newsData.value =
                    newsList.firstOrNull { news ->
                        news.id == id
                    }
            }
        }
    }
}
