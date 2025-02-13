package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.faimizufarov.news.NewsFragment
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.ActivityMainBinding
import ru.faimizufarov.simbirtraining.java.App
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.categories.CategoriesFragment
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.detail_description.DetailDescriptionFragment
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news_filter.NewsFilterFragment
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.profile.ProfileFragment
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.search.SearchFragment
import javax.inject.Inject

@Suppress("ktlint:standard:no-empty-first-line-in-class-body")
class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(viewBinding.root)
        viewBinding.bottomNavView.selectedItemId = R.id.action_help

        (applicationContext as App).appComponent.injectMainActivity(this)
        mainViewModel =
            ViewModelProvider(
                this,
                mainViewModelFactory,
            )[MainViewModel::class]

        viewBinding.bottomNavView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_help -> setCurrentFragment(CategoriesFragment.newInstance())
                R.id.action_profile -> setCurrentFragment(ProfileFragment.newInstance())
                R.id.action_search -> setCurrentFragment(SearchFragment.newInstance())
                R.id.action_news -> setCurrentFragment(NewsFragment.newInstance())
            }
            true
        }

        mainViewModel.badgeCountLiveData.observe(this) { badgeCount ->
            updateBadgeCount(badgeCount)
        }

        navigateToDetailDescriptionFragment()
        navigateToFilterFragment()
    }

    private fun navigateToDetailDescriptionFragment() {
        supportFragmentManager.setFragmentResultListener(
            NewsFragment.NAVIGATE_TO_DETAIL_DESTINATION_FRAGMENT_RESULT,
            this,
        ) { _, bundle ->
            val isNavigateToDetailDescriptionFragment =
                bundle.getBoolean(NewsFragment.NAVIGATE_TO_DETAIL_DESTINATION_FRAGMENT)

            if (isNavigateToDetailDescriptionFragment) {
                supportFragmentManager.beginTransaction()
                    .add(
                        R.id.fragmentContainerView,
                        DetailDescriptionFragment.newInstance(),
                    )
                    .commit()
            } else {
                error("Navigation to DetailDescriptionFragment by setFragmentResult() is broken")
            }
        }
    }

    private fun navigateToFilterFragment() {
        supportFragmentManager.setFragmentResultListener(
            NewsFragment.NAVIGATE_TO_FILTER_FRAGMENT_RESULT,
            this,
        ) { _, bundle ->
            val isNavigateToFilterFragment =
                bundle.getBoolean(NewsFragment.NAVIGATE_TO_FILTER_FRAGMENT)

            if (isNavigateToFilterFragment) {
                supportFragmentManager.beginTransaction()
                    .add(
                        R.id.fragmentContainerView,
                        NewsFilterFragment.newInstance(),
                    )
                    .commit()
            } else {
                error("Navigation to NewsFilterFragment by setFragmentResult() is broken")
            }
        }
    }

    private fun updateBadgeCount(unreadNewsCount: Int) {
        val badge = viewBinding.bottomNavView.getOrCreateBadge(R.id.action_news)
        val isNewsUnread = unreadNewsCount > 0
        if (isNewsUnread) {
            badge.number = unreadNewsCount
        }
        badge.isVisible = isNewsUnread
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, fragment)
            commit()
        }
    }
}
