package com.example.myreaderapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myreaderapp.screens.ReaderSplashScreen
import com.example.myreaderapp.screens.details.BookDetailsScreen
import com.example.myreaderapp.screens.home.Home
import com.example.myreaderapp.screens.home.HomeScreenViewModel
import com.example.myreaderapp.screens.login.ReaderLoginScreen
import com.example.myreaderapp.screens.search.BookSearchViewModel
import com.example.myreaderapp.screens.search.ReaderSearchScreen
import com.example.myreaderapp.screens.stats.ReaderStatsScreen
import com.example.myreaderapp.screens.update.BookUpdateScreen

@OptIn(ExperimentalComposeUiApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReaderNavigation() {

    //Controls where we need to go
    val navController = rememberNavController()

    //Hosts all the navigation screens
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name){

        //The actual composable/screen that wem want to show the user
        composable(ReaderScreens.SplashScreen.name){
            ReaderSplashScreen(navController = navController)
        }

        composable(ReaderScreens.LoginScreen.name){
            ReaderLoginScreen(navController = navController)
        }

        composable(ReaderScreens.ReaderHomeScreen.name){
            val homeViewModel = hiltViewModel<HomeScreenViewModel>()
            Home(navController = navController, viewModel = homeViewModel)
        }

        composable(ReaderScreens.SearchScreen.name){
            val searchViewModel = hiltViewModel<BookSearchViewModel>()
            ReaderSearchScreen(navController = navController, viewModel = searchViewModel)
        }

        composable(ReaderScreens.ReaderStatsScreen.name){
            ReaderStatsScreen(navController = navController)
        }

        val detailName = ReaderScreens.DetailScreen.name
        composable("$detailName/{bookId}", arguments = listOf(navArgument("bookId"){
            type = NavType.StringType
        })) { backStackEntry ->
            backStackEntry.arguments?.getString("bookId").let {

                BookDetailsScreen(navController = navController, bookId = it.toString())
            }
        }

        val updateName = ReaderScreens.UpdateScreen.name
        composable("$updateName/{bookItemId}",
            arguments = listOf(navArgument("bookItemId") {
            type = NavType.StringType
        })){navBackStackEntry ->
            navBackStackEntry.arguments?.getString("bookItemId").let {
                BookUpdateScreen(navController = navController, bookItemId = it.toString())
            }

        }
    }
}