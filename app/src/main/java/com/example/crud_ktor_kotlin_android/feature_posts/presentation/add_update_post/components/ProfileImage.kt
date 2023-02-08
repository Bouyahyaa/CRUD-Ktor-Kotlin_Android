package com.example.crud_ktor_kotlin_android.feature_posts.presentation.add_update_post.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.example.crud_ktor_kotlin_android.R

@Composable
fun ProfileImage(image: Any, height: Dp, width: Dp, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .height(height)
            .width(width), contentAlignment = Alignment.Center
    ) {
        val painter = rememberImagePainter(  //image request
            data = image,
            builder = {
                placeholder(R.drawable.error_loading)
                error(R.drawable.error_loading)
                transformations(
                    CircleCropTransformation(),
                    RoundedCornersTransformation(20f)
                )
            }
        )
        Image(
            painter = painter,
            contentDescription = "image",
            alignment = Alignment.Center,
            modifier = modifier.fillMaxSize()
        )
    }
}