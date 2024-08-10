package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.detail_description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import ru.faimizufarov.news.NewsFragment
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
            val newsId = bundle.getString(NewsFragment.NEWS_ID)
        }

        binding.imageViewBack.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
    }

    companion object {
        fun newInstance(): DetailDescriptionFragment {
            return DetailDescriptionFragment()
        }
    }
}
