package com.example.hotelexplorer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hotelexplorer.ui.viewmodel.HotelSearchViewModel

@Composable
fun HotelSearchScreen(
    viewModel: HotelSearchViewModel,
) {
    val searchQuery = viewModel.searchQuery
    val onSearchQueryChanged: (String) -> Unit = { viewModel.searchQuery = it }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        ) {
            // Add your content here
        }

        TopAppBar(
            title = {
                TextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChanged,
                    placeholder = { Text(text = "Search location") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            actions = {
                IconButton(
                    onClick = {
                        if (searchQuery.isNotBlank()) {
                            viewModel.searchLocation()
                        }
                    },
                    enabled = searchQuery.isNotBlank()
                ) {
                    Icon(Icons.Filled.Search, contentDescription = null)
                }
            },
            backgroundColor = MaterialTheme.colors.background,
            elevation = AppBarDefaults.TopAppBarElevation
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}