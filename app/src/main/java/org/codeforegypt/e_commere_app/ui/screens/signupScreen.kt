package org.codeforegypt.e_commere_app.ui.screens


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import org.codeforegypt.domain.model.UserData
import org.codeforegypt.e_commere_app.R
import org.codeforegypt.e_commere_app.state.UserState
import org.codeforegypt.e_commere_app.ui.theme.mainColor
import org.codeforegypt.e_commere_app.viewmodel.SignupViewModel


@Composable
fun SignupScreen(
    viewModel: SignupViewModel = hiltViewModel()
) {
    val signupState by viewModel.userDate.collectAsState()
    val localContext = LocalContext.current
    val isPasswordVisible by viewModel.isPasswordVisible.collectAsState()

    LaunchedEffect(signupState) {
        when (signupState) {
            is UserState.Error -> {
                Toast.makeText(
                    localContext,
                    (signupState as UserState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            is UserState.Loading -> {}
            is UserState.Success -> {
                Toast.makeText(localContext, "Success", Toast.LENGTH_SHORT).show()
                //navigate to Home screen
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mainColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp)
                .padding(horizontal = 16.dp)

        ) {
            Image(
                painter = painterResource(R.drawable.group_5),
                contentDescription = "Route Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(70.dp),
                alignment = Alignment.Center
            )
            Spacer(modifier = Modifier.height(25.dp))
            InputFields(
                label = "Full Name",
                placeholder = "Enter your name",
                value = viewModel.fullName,
                onValueChange = { viewModel.onFullNameChange(it) }
            )
            InputFields(
                label = "Mobile Number",
                placeholder = "Enter your mobile no.",
                value = viewModel.phone,
                onValueChange = { viewModel.onPhoneChange(it) }
            )
            InputFields(
                label = "E-mail address",
                placeholder = "Enter your email address",
                value = viewModel.email,
                onValueChange = { viewModel.onEmailChange(it) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Password",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                placeholder = {
                    Text(
                        text = "Enter your password",
                        color = Color.Gray
                    )
                },
                shape = RoundedCornerShape(12.dp),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                        Icon(
                            painter = if (isPasswordVisible) painterResource(id = R.drawable.baseline_visibility_24)
                            else painterResource(id = R.drawable.baseline_visibility_off_24),
                            contentDescription = "Toggle Password Visibility"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    cursorColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        RePassword(
            label = "Confirm Password",
            placeholder = "Confirm your password",
            password = viewModel.password,
            confirmPassword = viewModel.confirmPassword,
            onClickButton = { viewModel.togglePasswordVisibility() },
            onConfirmPasswordChange = { viewModel.onConfirmPasswordChange(it) }

        )
        Spacer(modifier = Modifier.height(35.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(55.dp),
            onClick = {
                if (
                    viewModel.fullName.isEmpty() ||
                    viewModel.phone.isEmpty() ||
                    viewModel.email.isEmpty() ||
                    viewModel.password.isEmpty() ||
                    viewModel.confirmPassword.isEmpty()
                ) {
                    Toast.makeText(localContext, "Please fill all fields", Toast.LENGTH_SHORT)
                        .show()
                    return@Button
                }
                val user = UserData(

                    name = viewModel.fullName,
                    phone = viewModel.phone,
                    email = viewModel.email,
                    password = viewModel.password,
                    rePassword = viewModel.confirmPassword
                )
                viewModel.signup(user)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Sign Up",
                color = mainColor,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}
