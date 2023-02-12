package com.example.crud_ktor_kotlin_android.feature_posts.presentation.posts.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.crud_ktor_kotlin_android.feature_posts.domain.models.Post

@Composable
fun PostListItem(
    painterBaseImage: Painter,
    post: Post,
    onDelete: (String) -> Unit,
    onEdit: () -> Unit,
    isLoading: Boolean,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        Box(modifier = Modifier.height(200.dp)) {
            Image(
                modifier = Modifier.fillMaxSize(),
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
                    username = post.title,
                )
            }

            IconButton(
                onClick = {
                    onDelete(post.id)
                },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                if (!post.isLoading)
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Image",
                        tint = Color.Red.copy(alpha = 0.5f)
                    )
                else CircularProgressIndicator(
                    modifier = Modifier.size(15.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            }

            IconButton(onClick = {
                onEdit()
            }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Image"
                )
            }
        }
    }
}