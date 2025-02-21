/*
 * Copyright (c) 2025, Seth Ladd
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 */

package com.example.textinputshowcase.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.textinputshowcase.screens.*

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Login : Screen("login")
    object Purchase : Screen("purchase")
    object Profile : Screen("profile")
    object Chat : Screen("chat")
    object Search : Screen("search")
    object Review : Screen("review")
    object Settings : Screen("settings")
    object Calendar : Screen("calendar")
    object SocialPost : Screen("social_post")
    object CopyPaste : Screen("copy_paste")
}

@Composable
fun TextInputShowcaseNavigation() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Purchase.route) { PurchaseScreen(navController) }
        composable(Screen.Profile.route) { ProfileScreen(navController) }
        composable(Screen.Chat.route) { ChatScreen(navController) }
        composable(Screen.Search.route) { SearchScreen(navController) }
        composable(Screen.Review.route) { ReviewScreen(navController) }
        composable(Screen.Settings.route) { SettingsScreen(navController) }
        composable(Screen.Calendar.route) { CalendarScreen(navController) }
        composable(Screen.SocialPost.route) { SocialPostScreen(navController) }
        composable(Screen.CopyPaste.route) { CopyPasteScreen(navController) }
    }
} 