package com.example.my_pesel_validator

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel

class PeselViewModel : ViewModel() {
    val text = mutableStateOf(TextFieldValue())
    val isValid = mutableStateOf("No input")
    val birthdayDate = mutableStateOf("")
    val gender = mutableStateOf("")
    val peselChecksum = mutableStateOf("")
}