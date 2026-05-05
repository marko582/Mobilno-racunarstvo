package com.example.frontend.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.navigation.compose.rememberNavController
import com.example.frontend.Screen
import com.example.frontend.ui.theme.FrontendTheme

@Composable
fun LoginScreen(
    state: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
)
{
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "MOVIE APP",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 32.dp)
            )


            OutlinedTextField(
                value = state.email,
                onValueChange = onEmailChange,
                label = {Text("Email")},
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = state.password,
                onValueChange = onPasswordChange,
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )

            state.error?.let {
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = it, color = Color.Red, modifier = Modifier.padding(8.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Sign In", color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(32.dp))

            TextButton(onClick = onRegisterClick) {
                Text(
                    text = "Don't have an account? Sign up!",
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    FrontendTheme {
        val navController = rememberNavController()
        LoginScreen(LoginUiState(), {},{},{},{})
    }
}