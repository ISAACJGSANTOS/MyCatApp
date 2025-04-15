package com.example.mycatapp.ui.screens

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun ErrorAlertDialog(error: String) {
    val showDialog = remember { mutableStateOf(true) }
    LaunchedEffect(error) {
        showDialog.value = true
    }
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            title = { Text(text = "Ups, there is an error") },
            text = { Text(text = error) },
            confirmButton = {
                Button(onClick = {
                    showDialog.value = false
                }) {
                    Text("OK")
                }
            }
        )
    }
}
