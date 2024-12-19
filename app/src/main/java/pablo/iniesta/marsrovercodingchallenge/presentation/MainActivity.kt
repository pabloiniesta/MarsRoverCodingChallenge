package pablo.iniesta.marsrovercodingchallenge.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import pablo.iniesta.marsrovercodingchallenge.presentation.ui.theme.MarsRoverCodingChallengeTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dagger.hilt.android.AndroidEntryPoint
import pablo.iniesta.marsrovercodingchallenge.presentation.marsscreen.MarsScreen
import pablo.iniesta.marsrovercodingchallenge.presentation.startscreen.StartScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarsRoverCodingChallengeTheme {
               val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = StartScreen,
                        modifier = Modifier.padding(innerPadding)
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
}

@Serializable
object StartScreen

@Serializable
object MarsScreen