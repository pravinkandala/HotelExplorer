package com.example.hotelexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hotelexplorer.ui.HotelDetailPage
import com.example.hotelexplorer.ui.HotelSearchScreen
import com.example.hotelexplorer.ui.viewmodel.HotelSearchViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: HotelSearchViewModel = getViewModel()

        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "hotelSearchScreen") {
                composable(
                    "hotelSearchScreen"
                ) {
                    HotelSearchScreen(
                        viewModel = viewModel,
                        navController = navController
                    )
                }
                composable(
                    "hotelDetailScreen/{hotelId}",
                    arguments = listOf(navArgument("hotelId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val hotelId = backStackEntry.arguments?.getString("hotelId") ?: 0
                    HotelDetailPage(
                        hotel = viewModel.hotels.value?.find { it.id == hotelId },
                        navController = navController
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}