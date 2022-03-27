package ru.infinity_coder.chatiumapp.presentation.authorization.sms_code

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun AuthSmsCodeScreen(
    navController: NavController,
    viewModel: AuthSmsCodeViewModel = viewModel(
        factory = AuthSmsCodeViewModelFactory(
            navController = navController
        )
    )
) {

    var smsCodeText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Your SMS code")

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            value = smsCodeText,
            onValueChange = { smsCodeText = it },
            label = { Text("SMS code") }
        )

        val activity = LocalContext.current.requireActivity()
        Button(
            modifier = Modifier.padding(vertical = 4.dp),
            onClick = {
                viewModel.onSmsCodeEntered(activity, smsCodeText)
            }
        ) {
            Text("Enter")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthSmsCodePreview() {
    val navController = rememberNavController()
    AuthSmsCodeScreen(
        navController = navController,
        viewModel = AuthSmsCodeViewModel(navController)
    )
}