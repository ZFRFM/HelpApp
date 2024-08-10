package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.detail_description

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import ru.faimizufarov.news.NewsFragment
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.FragmentDetailDescriptionBinding
import ru.faimizufarov.simbirtraining.java.App
import javax.inject.Inject

class DetailDescriptionFragment : Fragment() {
    private lateinit var binding: FragmentDetailDescriptionBinding

    @Inject
    lateinit var detailDescriptionViewModelFactory: DetailDescriptionViewModelFactory
    private lateinit var detailDescriptionViewModel: DetailDescriptionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App)
            .appComponent
            .injectDetailDescriptionFragment(this)
        detailDescriptionViewModel =
            ViewModelProvider(
                this,
                detailDescriptionViewModelFactory,
            )[DetailDescriptionViewModel::class]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentDetailDescriptionBinding
                .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener(NewsFragment.NEWS_ID_RESULT) { _, bundle ->
            val newsId =
                bundle.getString(NewsFragment.NEWS_ID)
                    ?: error("NewsID from setFragmentResultListener() is null")

            detailDescriptionViewModel.getNewsDetailDescription(newsId)
        }

        detailDescriptionViewModel.newsData.observe(viewLifecycleOwner) { news ->

            val startDate = convertToLocalDateTime(news.startDate)

            val finishDate = convertToLocalDateTime(news.finishDate)

            val finishDay = convertToLocalDateTime(news.finishDate).date.toEpochDays()

            val today = Clock.System.todayIn(TimeZone.currentSystemDefault()).toEpochDays()

            with(binding.contentDetailDescription) {
                val imageUrlList = news.newsImages
                val imageUrl = imageUrlList.first().toString()

                if (!imageUrl.startsWith("images/news")) {
                    Glide
                        .with(requireContext())
                        .load(imageUrl)
                        .into(binding.contentDetailDescription.imageViewFirstPicture)
                } else {
                    Glide
                        .with(requireContext())
                        .load(Uri.parse("file:///android_asset/$imageUrl"))
                        .into(binding.contentDetailDescription.imageViewFirstPicture)
                }

                textViewNews.text = news.nameText
                textViewDescTop.text = news.descriptionText

                textViewRemainingTime.text =
                    if (finishDay - today >= 0) {
                        getString(
                            R.string.news_remaining_time_with_args,
                            finishDay - today,
                            startDate.dayOfMonth,
                            startDate.monthNumber,
                            finishDate.dayOfMonth,
                            finishDate.monthNumber,
                        )
                    } else {
                        getString(R.string.news_event_finished)
                    }
            }
        }

        binding.imageViewBack.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
    }

    private fun convertToLocalDateTime(milliseconds: Long) =
        LocalDateTime
            .parse(
                Instant.fromEpochMilliseconds(milliseconds)
                    .toLocalDateTime(TimeZone.currentSystemDefault())
                    .toString(),
            )

    companion object {
        fun newInstance(): DetailDescriptionFragment {
            return DetailDescriptionFragment()
        }
    }
}
