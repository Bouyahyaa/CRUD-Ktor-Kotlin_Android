package com.example.crud_ktor_kotlin_android.feature_posts.presentation.posts.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun PostListItem(
    painterBaseImage: Painter,
    title: String,
) {
    Card(
        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(15.dp), elevation = 5.dp
    ) {
        Box(modifier = Modifier.height(200.dp)) {
            Image(
                painter = painterBaseImage,
                contentDescription = "Image",
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                UserImageNameItem(
                    username = title,
                )
            }
        }
    }
}