package org.codeforegypt.e_commere_app.ui.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import org.codeforegypt.e_commere_app.R
import org.codeforegypt.e_commere_app.viewmodel.SignupViewModel


@Composable
fun InputFields(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            modifier = Modifier.padding(top = 10.dp)

        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                cursorColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            shape = RoundedCornerShape(12.dp),
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.Gray
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun RePassword(
    label: String,
    password: String,
    confirmPassword: String,
    placeholder: String,
    onClickButton: () -> Unit,
    onConfirmPasswordChange: (String) -> Unit

) {
    val viewModel: SignupViewModel = hiltViewModel()
    val isPasswordVisible by viewModel.isPasswordVisible.collectAsState()
    val isError = confirmPassword.isNotEmpty() && password != confirmPassword

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = label,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            modifier = Modifier.padding(top = 15.dp)

        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { onConfirmPasswordChange(it) },
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.Gray
                )
            },
            shape = RoundedCornerShape(12.dp),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { onClickButton() }) {
                    Icon(
                        painter = painterResource(
                            id = if (isPasswordVisible) R.drawable.baseline_visibility_24
                            else R.drawable.baseline_visibility_off_24
                        ),
                        contentDescription = "Toggle Password Visibility"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            isError = isError,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                cursorColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )
        if (isError) {
            Text(
                text = "Passwords do not match",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

    }
    Spacer(modifier = Modifier.height(10.dp))
}


