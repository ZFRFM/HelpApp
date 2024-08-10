package ru.faimizufarov.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import ru.faimizufarov.core.theme.HelpTheme
import ru.faimizufarov.domain.models.News
import ru.faimizufarov.news.databinding.FragmentNewsComposeBinding
import ru.faimizufarov.news.di.NewsComponentProvider
import ru.faimizufarov.news.models.toNews
import javax.inject.Inject

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsComposeBinding

    @Inject
    lateinit var newsViewModelFactory: NewsViewModelFactory
    private lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as NewsComponentProvider)
            .provideNewsComponent()
            .injectNewsFragment(this)
        newsViewModel =
            ViewModelProvider(
                this,
                newsViewModelFactory,
            )[NewsViewModel::class]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNewsComposeBinding.inflate(layoutInflater, container, false)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                HelpTheme {
                    NewsScreen(
                        clickFilter = { openFilterFragment() },
                        clickItem = { updateFeed(it.toNews()) },
                    )
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel.collectReadNewsIds()
    }

    private fun openFilterFragment() {
        TODO("Implement navigation to FilterFragment")
    }

    private fun updateFeed(news: News) {
        newsViewModel.emitReadNewsIds(news)

        val bundle = bundleOf(NEWS_ID to news.id)
        setFragmentResult(NEWS_ID_RESULT, bundle)

        TODO("Implement navigation to DetailDescriptionFragment")
    }

    companion object {
        const val NEWS_ID = "NEWS_ID"
        const val NEWS_ID_RESULT = "NEWS_ID_RESULT"

        fun newInstance() = NewsFragment()
    }
}
