package com.example.frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.frontend.data.AppGraph
import com.example.frontend.ui.components.NavBar
import com.example.frontend.ui.login.LoginScreen
import com.example.frontend.ui.movie_details.MovieDetailsScreen
import com.example.frontend.ui.movie_list.MovieListScreen
import com.example.frontend.ui.navigation.MyNavigation
import com.example.frontend.ui.profile.ProfileScreen
import com.example.frontend.ui.rated_movies.RatedMoviesScreen
import com.example.frontend.ui.register.RegisterScreen
import com.example.frontend.ui.theme.FrontendTheme
import com.example.frontend.ui.watchlist.WatchlistScreen

sealed class Screen(val route: String){
    object Login : Screen("login")
    object  Register : Screen("register")
    object Home : Screen("home")
    object Details : Screen("details/{movieId}") {
        val arguments = listOf(
            navArgument("movieId") { type = NavType.LongType }
        )
        fun createRoute(movieId: Long) = "details/$movieId"
    }
    object Rated : Screen("rated")
    object Watchlist : Screen("watchlist")
    object Profile: Screen("profile")
    object AccountSettings: Screen("account-settings")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppGraph.init(applicationContext)
        enableEdgeToEdge()
        setContent {
            var isDarkMode by remember { mutableStateOf(true) }
            FrontendTheme (darkTheme = isDarkMode) {
                MainLayout(
                    isDarkMode = isDarkMode,
                    onToggleTheme = {isDarkMode = !isDarkMode}
                )
            }
        }
    }
}

@Composable
fun MainLayout(isDarkMode: Boolean, onToggleTheme: () -> Unit) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val noBottomBarRoutes = listOf(Screen.Login.route, Screen.Register.route)

    Scaffold(
        bottomBar = {
            if (currentRoute !in noBottomBarRoutes) {
                NavBar(navController)
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            MyNavigation(
                navController = navController,
                isDarkMode = isDarkMode,
                onToggleTheme = onToggleTheme
            )
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    FrontendTheme {
        val navController = rememberNavController()
        MainLayout(true, {})
    }
}