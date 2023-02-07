package com.example.crud_ktor_kotlin_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.crud_ktor_kotlin_android.core.util.Screen
import com.example.crud_ktor_kotlin_android.feature_posts.presentation.posts.PostsScreen
import com.example.crud_ktor_kotlin_android.ui.theme.CRUDKtorKotlin_AndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CRUDKtorKotlin_AndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(
                        navController = rememberNavController(),
                        startDestination = Screen.PostsScreen.route
                    ) {
                        composable(route = Screen.PostsScreen.route) {
                            PostsScreen()
                        }

                        composable(route = Screen.AddUpdateScreen.route) {}
                    }
                }
            }
        }
    }
}