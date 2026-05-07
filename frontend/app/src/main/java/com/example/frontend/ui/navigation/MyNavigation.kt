package com.example.frontend.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.frontend.Screen
import com.example.frontend.ui.account_settings.AccountSettingsScreen
import com.example.frontend.ui.account_settings.AccountSettingsViewModel
import com.example.frontend.ui.login.LoginScreen
import com.example.frontend.ui.login.LoginViewModel
import com.example.frontend.ui.movie_details.MovieDetailsScreen
import com.example.frontend.ui.movie_details.MovieDetailsViewModel
import com.example.frontend.ui.movie_list.MovieListScreen
import com.example.frontend.ui.movie_list.MovieListViewModel
import com.example.frontend.ui.profile.ProfileScreen
import com.example.frontend.ui.profile.ProfileViewModel
import com.example.frontend.ui.rated_movies.RatedMoviesScreen
import com.example.frontend.ui.rated_movies.RatedMoviesViewModel
import com.example.frontend.ui.register.RegisterScreen
import com.example.frontend.ui.register.RegisterViewModel
import com.example.frontend.ui.util.OnResumeEffect
import com.example.frontend.ui.watchlist.WatchlistScreen
import com.example.frontend.ui.watchlist.WatchlistViewModel

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

        // LOGIN SCREEN
        composable(Screen.Login.route) {
            val viewModel: LoginViewModel = viewModel()
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(state.isLoginSuccess) {
                if (state.isLoginSuccess) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            }

            LoginScreen(
                state = state,
                onUsernameChange = viewModel::onUsernameChange,
                onPasswordChange = viewModel::onPasswordChange,
                onLoginClick = viewModel::onLoginClick,
                onRegisterClick = { navController.navigate(Screen.Register.route) }
            )
        }

        // REGISTER SCREEN
        composable(Screen.Register.route) {
            val viewModel: RegisterViewModel = viewModel()
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(state.isRegisterSuccess) {
                if (state.isRegisterSuccess) {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            }

            RegisterScreen(
                state = state,
                onFirstNameChange = viewModel::onFirstNameChange,
                onLastNameChange = viewModel::onLastNameChange,
                onUsernameChange = viewModel::onUsernameChange,
                onEmailChange = viewModel::onEmailChange,
                onPasswordChange = viewModel::onPasswordChange,
                onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
                onRegisterClick = viewModel::onRegisterClick,
                onBackToLogin = { navController.popBackStack() }
            )
        }

        // MOVIE LIST SCREEN
        composable(Screen.Home.route) {
            val viewModel: MovieListViewModel = viewModel()
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            MovieListScreen(
                state = state,
                onSearchChanged = {query -> viewModel.onSearchQueryChanged(query)},
                onMovieClick = { movieId ->
                    navController.navigate(Screen.Details.createRoute(movieId))
                }
            )
        }

        // MOVIE DETAILS SCREEN
        composable(
            route = Screen.Details.route,
            arguments = Screen.Details.arguments
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getLong("movieId")

            val viewModel: MovieDetailsViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return MovieDetailsViewModel(movieId) as T
                    }
                }
            )

            val state by viewModel.uiState.collectAsStateWithLifecycle()

            MovieDetailsScreen(
                state = state,
                onBackClick = {
                    navController.popBackStack()
                },
                onAddToWatchlistClick = {
                    viewModel.toggleWatchlist()
                },
                onRatingSubmit = { rating ->
                    viewModel.updateRating(rating)
                },
                onCommentSubmit = { text ->
                    viewModel.postComment(text)
                }
            )
        }

        // RATED MOVIES SCREEN
        composable(Screen.Rated.route) {
            val viewModel: RatedMoviesViewModel = viewModel()
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            OnResumeEffect { viewModel.reload() }
            RatedMoviesScreen(
                state = state,
                onSearchChanged = { query -> viewModel.onSearchQueryChanged(query) },
                onMovieClick = { id ->
                    navController.navigate(Screen.Details.createRoute(id))
                }
            )
        }

        // WATCHLIST SCREEN
        composable(Screen.Watchlist.route) {
            val viewModel: WatchlistViewModel = viewModel()
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            OnResumeEffect { viewModel.reload() }
            WatchlistScreen(
                state = state,
                onSearchChanged = { query -> viewModel.onSearchQueryChanged(query) },
                onMovieClick = { id ->
                    navController.navigate(Screen.Details.createRoute(id))
                }
            )
        }

        // PROFILE SCREEN
        composable(Screen.Profile.route) {
            val viewModel: ProfileViewModel = viewModel()
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            OnResumeEffect { viewModel.reload() }
            ProfileScreen(
                state = state,
                onToggleTheme = {
                    viewModel.toggleTheme(onToggleTheme)
                },
                onLogoutClick = {
                    viewModel.logout {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0)
                        }
                    }
                },
                onSettingsClick = { navController.navigate(Screen.AccountSettings.route) }
            )
        }

        composable(Screen.AccountSettings.route) {
            val viewModel: AccountSettingsViewModel = viewModel()
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            AccountSettingsScreen(
                state = state,
                onBack = { navController.popBackStack() },
                onFirstNameChange = viewModel::onFirstNameChange,
                onLastNameChange = viewModel::onLastNameChange,
                onUsernameChange = viewModel::onUsernameChange,
                onEmailChange = viewModel::onEmailChange,
                onSave = viewModel::onSave
            )
        }
    }
}