package com.android.immobilien.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.android.immobilien.R
import com.android.immobilien.domain.model.Property
import com.android.immobilien.utils.formatNullable

@Composable
fun PropertyItem(
    property: Property,
    onClick: () -> Unit,
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column {
            PropertyHeader(
                imageUrl = property.url,
                price = property.price,
            )

            PropertyDetails(
                city = property.city,
                propertyType = property.propertyType,
                professional = property.professional,
                rooms = property.rooms,
                area = property.area,
                bedrooms = property.bedrooms,
            )
        }
    }
}

@Composable
private fun PropertyHeader(
    imageUrl: String?,
    price: Int,
) {
    Box(modifier = Modifier.height(180.dp)) {
        AsyncImage(
            model = imageUrl,
            contentDescription = stringResource(R.string.property_image_desc),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(180.dp),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_launcher_background),
            error = painterResource(R.drawable.ic_launcher_background),
        )

        PriceTag(price = price)
    }
}

@Composable
private fun PriceTag(price: Int) {
    Box(
        contentAlignment = Alignment.BottomStart,
        modifier =
            Modifier
                .fillMaxSize()
                .padding(15.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 5.dp),
        ) {
            Text(
                text = stringResource(R.string.price_format, price),
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
        }
    }
}

@Composable
private fun PropertyDetails(
    city: String,
    propertyType: String,
    professional: String,
    rooms: Int?,
    area: Int,
    bedrooms: Int?,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        Text(
            text = city,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(R.string.property_type_format, propertyType),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = professional,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Key features
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            FeatureItem(
                value = rooms.formatNullable(context = context, defaultResId = R.string.not_available),
                label = stringResource(R.string.rooms),
            )
            FeatureItem(
                value = stringResource(R.string.area_format, area),
                label = stringResource(R.string.square_meters),
            )
            FeatureItem(
                value =
                    bedrooms.formatNullable(
                        context = context,
                        defaultResId = R.string.not_available,
                    ),
                label = stringResource(R.string.bedrooms),
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun FeatureItem(
    value: String,
    label: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp),
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Preview
@Composable
fun ListingItemPreview() {
    PropertyItem(
        Property(
            id = 1,
            bedrooms = 2,
            city = "Berlin",
            area = 24,
            url = "Link",
            price = 2300,
            professional = "GSL EXPLORE",
            propertyType = "Maison - Villa",
            offerType = 1,
            rooms = 8,
        ),
    ) {
    }
}
