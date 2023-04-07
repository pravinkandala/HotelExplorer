package com.example.hotelexplorer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.hotelexplorer.R
import com.example.hotelexplorer.data.model.HotelListResponse.PropertySearch.Property
import com.example.hotelexplorer.ui.viewmodel.HotelSearchViewModel

@Composable
fun HotelSearchScreen(
    viewModel: HotelSearchViewModel,
    navController: NavController
) {
    val hotels by viewModel.hotels.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)

    val searchQuery = viewModel.searchQuery
    val onSearchQueryChanged: (String) -> Unit = { viewModel.searchQuery = it }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        value = searchQuery,
                        onValueChange = onSearchQueryChanged,
                        placeholder = { Text(text = stringResource(R.string.search_bar_hint)) },
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
        },
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else if (hotels.isEmpty()) {
                    Text(
                        text = stringResource(R.string.no_hotels_found),
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    LazyColumn {
                        val size = hotels.size
                        items(size) { index ->
                            HotelListItem(
                                hotel = hotels[index],
                                onHotelItemClick = { navController.navigate("hotelDetailScreen/${hotels[index].id}") })
                        }
                    }
                }
            }
        },
        backgroundColor = Color.White
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HotelListItem(hotel: Property, onHotelItemClick: (Property) -> Unit) {
    val context = LocalContext.current
    val imageSize = 120.dp
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(imageSize + 48.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
        onClick = { onHotelItemClick(hotel) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(hotel.propertyImage?.image?.url)
                    .build(),
                contentDescription = hotel.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .size(imageSize)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = hotel.name ?: "",
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = buildAnnotatedString {
                        hotel.offerBadge?.primary?.text?.let {
                            append(it)
                            hotel.offerBadge.secondary?.text?.let { secondary ->
                                append(" - $secondary")
                            }
                        }
                    },
                    style = MaterialTheme.typography.h5,
                    color = Color.Blue,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}


