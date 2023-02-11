package com.example.crud_ktor_kotlin_android.feature_posts.presentation.posts

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.crud_ktor_kotlin_android.core.util.Screen
import com.example.crud_ktor_kotlin_android.feature_posts.presentation.posts.components.PostListItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostsScreen(
    navController: NavController,
    postsViewModel: PostsViewModel = hiltViewModel()
) {
    val state = postsViewModel.state.value

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddUpdateScreen.route)
                },
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Color.Gray
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add FAB",
                    tint = Color.White
                )
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            ) {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    cells = GridCells.Fixed(1),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(state.posts) { post ->
                        PostListItem(
                            painterBaseImage = rememberImagePainter(post.image),
                            post = post,
                            onDelete = {
                                postsViewModel.onEvent(PostEvent.OnDeletePost(it))
                            }
                        )
                    }
                }

                if (state.error.isNotBlank()) {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colors.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    )
                }

                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}