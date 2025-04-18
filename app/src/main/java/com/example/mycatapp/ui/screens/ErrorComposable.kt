package com.example.mycatapp.ui.screens

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun ErrorAlertDialog(error: String, buttonAction: () -> Unit) {
    val showDialog = remember { mutableStateOf(true) }
    LaunchedEffect(error) {
        showDialog.value = true
    }
    if (showDialog.value) {
        AlertDialog(
            modifier = Modifier.testTag("ErrorAlertDialog"),
            onDismissRequest = {
                showDialog.value = false
            },
            title = { Text(text = "Ups, there is an error") },
            text = { Text(text = error) },
            confirmButton = {
                Button(onClick = {
                    showDialog.value = false
                    buttonAction()
                }) {
                    Text("OK")
                }
            }
        )
    }
}
