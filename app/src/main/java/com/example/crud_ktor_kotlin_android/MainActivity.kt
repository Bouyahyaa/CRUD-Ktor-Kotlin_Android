package com.example.crud_ktor_kotlin_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.crud_ktor_kotlin_android.core.util.Screen
import com.example.crud_ktor_kotlin_android.feature_posts.presentation.add_update_post.AddUpdatePostScreen
import com.example.crud_ktor_kotlin_android.feature_posts.presentation.posts.PostsScreen
import com.example.crud_ktor_kotlin_android.ui.theme.CRUDKtorKotlin_AndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CRUDKtorKotlin_AndroidTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.PostsScreen.route
                    ) {
                        composable(route = Screen.PostsScreen.route) {
                            PostsScreen(
                                navController = navController
                            )
                        }

                        composable(
                            route = Screen.AddUpdateScreen.route + "?postId={postId}",
                            arguments = listOf(
                                navArgument("postId") {
                                    type = NavType.StringType
                                    defaultValue = ""
                                },
                            )
                        ) { entry ->
                            AddUpdatePostScreen(
                                navController = navController,
                                postId = entry.arguments?.getString("postId")!!,
                            )
                        }
                    }
                }
            }
        }
    }
}