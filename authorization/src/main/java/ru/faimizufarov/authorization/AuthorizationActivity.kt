package ru.faimizufarov.authorization

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import ru.faimizufarov.core.theme.HelpTheme

class AuthorizationActivity : AppCompatActivity() {
    private val authorizationViewModel: AuthorizationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelpTheme {
                AuthorizationScreen(
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
        with(authorizationViewModel) {
            navigateToMainLiveEvent.observe(this@AuthorizationActivity) {
                setResultToMainActivity()
            }

            finishAuthorizationLiveEvent.observe(this@AuthorizationActivity) {
                finish()
            }
        }
    }

    private fun setResultToMainActivity() {
        setResult(Activity.RESULT_OK)
        finish()
    }
}
