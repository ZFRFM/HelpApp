package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.detail_description

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.faimizufarov.data.repository.NewsRepositoryImpl
import ru.faimizufarov.domain.repository.NewsRepository
import javax.inject.Inject

class DetailDescriptionViewModelFactory
    @Inject
    constructor(
        val newsRepository: NewsRepository,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            DetailDescriptionViewModel(
                newsRepository as NewsRepositoryImpl,
            ) as T
    }
