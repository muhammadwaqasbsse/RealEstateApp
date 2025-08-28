package com.android.immobilien

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.immobilien.presentation.ui.screen.PropertyDetailScreen
import com.android.immobilien.presentation.ui.screen.PropertyScreen
import com.android.immobilien.presentation.ui.theme.ImmobilienAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImmobilienAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    ImmobilienAppUI()
                }
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun ImmobilienAppUI() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "list") {
        composable(route = "list") {
            PropertyScreen(
                onItemClick = { id ->
                    navController.navigate("detail/$id")
                },
            )
        }
        composable("detail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0
            PropertyDetailScreen(
                id = id,
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ImmobilienAppTheme {
        ImmobilienAppUI()
    }
}
