package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.detail_description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.faimizufarov.simbirtraining.databinding.FragmentDetailDescriptionBinding

class DetailDescriptionFragment : Fragment() {
    private lateinit var binding: FragmentDetailDescriptionBinding

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
