package com.example.crud_ktor_kotlin_android.core.util

sealed class Screen(val route: String) {
    object PostsScreen : Screen("posts_screen")
    object AddUpdateScreen : Screen("add_update_screen")
}