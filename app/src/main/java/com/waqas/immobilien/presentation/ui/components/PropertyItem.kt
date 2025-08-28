package com.waqas.immobilien.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.waqas.immobilien.domain.model.Property

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
            // Property Image
            Box(modifier = Modifier.height(180.dp)) {
                AsyncImage(
                    model = property.url,
                    contentDescription = "Property Image",
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                    contentScale = ContentScale.Crop,
                )
            }

            // Price tag with background
            Box(
                modifier =
                    Modifier
                        .align(Alignment.Start)
                        .padding(15.dp)
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 5.dp),
            ) {
                Text(
                    text = "${property.price} €",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
            }

            // Property Details
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Text(
                    text = property.city,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${property.propertyType} for sale",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = property.professional,
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
                        value = formatNullableInt(property.rooms),
                        label = "Rooms",
                    )
                    FeatureItem(
                        value = "${property.area} m²",
                        label = "m²",
                    )
                    FeatureItem(
                        value = formatNullableInt(property.bedrooms),
                        label = "Bedrooms",
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
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
