package com.hoy.ecommercecompose.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp


@Composable
fun RatingStars(rating: Double) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val filledStars = rating.toInt()
        val halfStar = if (rating - filledStars >= 0.5) 1 else 0
        val emptyStars = 5 - filledStars - halfStar

        repeat(filledStars) {
            Icon(
                Icons.Default.Star,
                contentDescription = "Filled Star",
                tint = Color.White,
                modifier = Modifier.size(14.dp)
            )
        }

        if (halfStar == 1) {
            Box(
                modifier = Modifier.size(14.dp)
            ) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = "Half Star Background",
                    tint = Color.Gray,
                    modifier = Modifier.size(14.dp)
                )
                Icon(
                    Icons.Default.Star,
                    contentDescription = "Half Star Foreground",
                    tint = Color.Yellow,
                    modifier = Modifier
                        .size(14.dp)
                        .clip(RectangleShape)
                        .width(7.dp)
                )
            }
        }

        repeat(emptyStars) {
            Icon(
                Icons.Default.Star,
                contentDescription = "Empty Star",
                tint = Color.Gray,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}
