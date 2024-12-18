package pablo.iniesta.marsrovercodingchallenge.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import pablo.iniesta.marsrovercodingchallenge.presentation.ui.theme.MarsRoverCodingChallengeTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pablo.iniesta.marsrovercodingchallenge.presentation.marsscreen.MarsScreen
import pablo.iniesta.marsrovercodingchallenge.presentation.startscreen.StartScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarsRoverCodingChallengeTheme {
               val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = StartScreen
                ) {
                    composable<StartScreen> {
                        StartScreen(navController)
                    }
                    composable<MarsScreen> {
                        MarsScreen()
                    }
                }
            }
        }
    }
}

@Serializable
object StartScreen

@Serializable
object MarsScreen