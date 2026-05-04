package com.example.frontend.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.frontend.Screen
import com.example.frontend.ui.login.LoginScreen
import com.example.frontend.ui.movie_details.MovieDetailsScreen
import com.example.frontend.ui.movie_list.MovieListScreen
import com.example.frontend.ui.profile.ProfileScreen
import com.example.frontend.ui.rated_movies.RatedMoviesScreen
import com.example.frontend.ui.register.RegisterScreen
import com.example.frontend.ui.watchlist.WatchlistScreen

sealed class NavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : NavItem(Screen.Home.route, Icons.Default.Home, "Home")
    object Rated : NavItem(Screen.Rated.route, Icons.Default.StarRate, "Rated")
    object Watchlist : NavItem(Screen.Watchlist.route, Icons.Default.List, "Watchlist")
    object Profile : NavItem(Screen.Profile.route, Icons.Default.Person, "Profile")
}

@Composable
fun MyNavigation(isDarkMode: Boolean, onToggleTheme: ()-> Unit, navController: NavHostController)
{
    NavHost(navController = navController, startDestination = Screen.Login.route) {

        composable(Screen.Login.route) {
            LoginScreen(navController, {navController.navigate(Screen.Register.route)})
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }
        composable(Screen.Home.route) {
            MovieListScreen(navController)
        }
        composable(Screen.Details.route) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")
            MovieDetailsScreen(movieId, navController)
        }
        composable(Screen.Rated.route) {
            RatedMoviesScreen(navController)
        }
        composable(Screen.Watchlist.route) {
            WatchlistScreen(navController)
        }
        composable (Screen.Profile.route){
            ProfileScreen(navController, isDarkMode, onToggleTheme)
        }
    }
}