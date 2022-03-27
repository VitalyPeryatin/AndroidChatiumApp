package ru.infinity_coder.chatiumapp.presentation.authorization.phone_number

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.infinity_coder.chatiumapp.presentation.ext.requireActivity

@Composable
fun AuthPhoneNumberScreen(
    navController: NavController,
    viewModel: AuthPhoneNumberViewModel = viewModel(
        factory = AuthPhoneNumberViewModelFactory(
            navController = navController
        )
    )
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        var phoneNumberText by remember { mutableStateOf("") }

        Text("Your phone number")

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            value = phoneNumberText,
            onValueChange = { phoneNumberText = it },
            label = { Text(text = "Phone number") }
        )

        val activity = LocalContext.current.requireActivity()
        Button(
            modifier = Modifier
                .padding(vertical = 4.dp),
            onClick = {
                viewModel.onSignInClicked(activity, phoneNumberText)
            }
        ) {
            Text(text = "LogIn")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthPhonePreview() {
    val navController = rememberNavController()
    AuthPhoneNumberScreen(
        navController = navController,
        viewModel = AuthPhoneNumberViewModel(navController)
    )
}
