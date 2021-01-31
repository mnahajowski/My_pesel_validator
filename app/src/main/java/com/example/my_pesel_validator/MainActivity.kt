package com.example.my_pesel_validator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.activity.viewModels
import androidx.compose.ui.unit.dp

class MainActivity : AppCompatActivity() {
    private val peselViewModel by viewModels<PeselViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PeselValidator(peselViewModel)
        }
    }
}

@Composable
fun PeselValidator(peselViewModel: PeselViewModel) {
    MaterialTheme {
        val typography = MaterialTheme.typography
        Column(
            modifier = Modifier.padding(32.dp)
        ) {
            OutlinedTextField(
                value = peselViewModel.text.value,
                onValueChange = { input: TextFieldValue ->
                    peselViewModel.text.value = input
                    onPESELInput(input.text, peselViewModel.isValid, peselViewModel.birthdayDate,
                        peselViewModel.gender, peselViewModel.peselChecksum)
                },
                label = { Text(text = "Enter PESEL") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),

            )

            Text(
                text = peselViewModel.isValid.value,
                style = typography.h5,
                modifier = Modifier.padding(0.dp, 16.dp)
            )

            TextPairRow(label = "Date: ", text = peselViewModel.birthdayDate.value, typography = typography)
            TextPairRow(label = "Gender: ", text = peselViewModel.gender.value, typography = typography)
            TextPairRow(label = "Valid checksum: ", text = peselViewModel.peselChecksum.value, typography = typography)
        }
    }
}

fun onPESELInput(text: String,
                 isValid: MutableState<String>,
                 birthDate: MutableState<String>,
                 gender: MutableState<String>,
                 checksum: MutableState<String>
) {

    val validatedPesel = PeselValidator().validate(text)

    if (validatedPesel != null) {
        isValid.value = "PESEL is valid"
        birthDate.value = validatedPesel.birthdayDate
        gender.value = validatedPesel.gender.toString()
        checksum.value = validatedPesel.correctChecksum.toString()
    } else {
        isValid.value = "Invalid PESEL"
        birthDate.value = ""
        gender.value = ""
        checksum.value = ""
    }
}

@Composable
fun TextPairRow(label: String, text: String, typography: Typography) {
    Row {
        Text(label, style = typography.h6)
        Text(text, style = typography.h6)
    }
}

