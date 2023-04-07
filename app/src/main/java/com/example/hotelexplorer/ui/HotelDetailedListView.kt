package com.example.hotelexplorer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.hotelexplorer.R
import com.example.hotelexplorer.data.model.HotelListResponse.PropertySearch.Property

@Composable
fun HotelDetailPage(hotel: Property?, navController: NavController) {
    if (hotel == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Something went wrong",
                style = MaterialTheme.typography.h6
            )
        }
    } else {
        val context = LocalContext.current
        val imageSize = 240.dp
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageSize)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(hotel.propertyImage?.image?.url)
                        .build(),
                    contentDescription = hotel.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .padding(16.dp)
                        .size(48.dp)
                        .background(Color.White, CircleShape)
                        .align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back_arrow_content_description),
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = hotel.name ?: "",
                    style = MaterialTheme.typography.h4,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colors.primary,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("${hotel.offerBadge?.primary?.text ?: ""} ")
                        }
                        hotel.offerBadge?.secondary?.text?.let { secondary ->
                            append("- $secondary")
                        }
                    },
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${hotel.price?.displayMessages?.firstOrNull()?.lineItems?.firstOrNull()?.price?.formatted ?: ""} ${hotel.price?.priceMessages?.firstOrNull()?.value}",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(modifier = Modifier.padding(bottom = 8.dp)) {
                    Icon(Icons.Filled.Star, contentDescription = null, tint = Color.Yellow)
                    Text(
                        text = "${hotel.reviews?.score ?: "N/A"} / ${hotel.reviews?.total ?: "0"} reviews",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}



