package com.example.my_pesel_validator

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


class PeselValidator {


    enum class Gender {
        FEMALE, MALE
    }

    data class PeselValidation(
            val birthdayDate: String,
            val gender: Gender,
            val correctChecksum: Boolean
    )

    fun validate(pesel: String): PeselValidation? {
        return if (peselLengthCorrect(pesel)) {
            PeselValidation(
                getBirthdayDate(pesel),
                getGender(pesel),
                checkChecksum(pesel)
            )
        } else
            null
    }

    private fun peselLengthCorrect(pesel: String): Boolean {
        return pesel.length == 11 && pesel.toLongOrNull() != null
    }

    private fun getBirthdayDate(pesel: String): String {
        val year = pesel.subSequence(0, 2).toString()
        val day = pesel.subSequence(4, 6).toString()
        val month = pesel.subSequence(2, 4).toString().toInt() % 20
        val century = getCentury(pesel.subSequence(2, 4).toString().toInt())

        val date = "${century}${year}-${month.toString().padStart(2, '0')}-${day}"

        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        } catch (e: DateTimeParseException) {
            return "Invalid date"
        }
        return date

    }

    private fun getCentury(month: Int): String {
        return when {
            month < 20 -> "19"
            month < 40 -> "20"
            month < 60 -> "21"
            month < 80 -> "22"
            else -> "18"
        }
    }

    private fun getGender(pesel: String): Gender {
        if (pesel[9].toInt() % 2 == 0)
            return Gender.FEMALE
        else
            return Gender.MALE
    }

    private fun checkChecksum(pesel: String): Boolean {
        var correctChecksum = 0
        val positionWeights = arrayOf(1, 3, 7, 9, 1, 3, 7, 9, 1, 3)

        for (i in 0 until pesel.length - 1)
            correctChecksum += (pesel[i].toString().toInt() * positionWeights[i]) % 10
        correctChecksum = 10 - (correctChecksum % 10)

        return correctChecksum == pesel.last().toString().toInt()
    }
}
