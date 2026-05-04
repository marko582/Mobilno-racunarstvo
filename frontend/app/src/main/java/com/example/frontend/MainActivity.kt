package com.example.frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.frontend.ui.login.LoginScreen
import com.example.frontend.ui.movie_list.MovieListScreen
import com.example.frontend.ui.register.RegisterScreen
import com.example.frontend.ui.theme.BrightOrange
import com.example.frontend.ui.theme.FrontendTheme

sealed class Screen(val route: String){
    object Login : Screen("login")
    object  Register : Screen("register")
    object Home : Screen("home")
    object Details : Screen("details/{movieId}"){
        fun createRoute(movieId: String) = "details/$movieId"
    }
    object Rated : Screen("rated")
    object Watchlist : Screen("watchlist")
    object Profile: Screen("profile")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkMode by remember { mutableStateOf(true) }
            FrontendTheme (darkTheme = isDarkMode) {
                MyNavigation(
                    isDarkMode = isDarkMode,
                    onToggleTheme = {isDarkMode = !isDarkMode}
                )
            }
        }
    }
}

@Composable
fun MyNavigation(isDarkMode: Boolean, onToggleTheme: ()-> Unit){
    val navController = rememberNavController()

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
            PlaceholderScreen("Details for Movie $movieId", Screen.Rated.route, navController, isDarkMode, onToggleTheme)
        }
        composable(Screen.Rated.route) {
            PlaceholderScreen("Rated Movies", Screen.Watchlist.route, navController, isDarkMode, onToggleTheme)
        }
        composable(Screen.Watchlist.route) {
            PlaceholderScreen("Watchlist", Screen.Profile.route, navController, isDarkMode, onToggleTheme)
        }
        composable (Screen.Profile.route){
            PlaceholderScreen("Profile", Screen.Login.route, navController, isDarkMode, onToggleTheme)
        }
    }
}

@Composable
fun PlaceholderScreen(
    name: String,
    nextRoute: String,
    navController: NavController,
    isDarkMode: Boolean,
    onToggleTheme: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = if (isDarkMode) "Dark" else "Light",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { onToggleTheme() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = BrightOrange,
                        checkedTrackColor = BrightOrange.copy(alpha = 0.5f)
                    )
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = name,
                color = BrightOrange,
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { navController.navigate(nextRoute) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrightOrange,
                    contentColor = Color.Black
                )
            ) {
                Text("Go to next screen")
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    FrontendTheme {
        val navController = rememberNavController()
        PlaceholderScreen("Preview Mode", "home", navController, true, { } )
    }
}