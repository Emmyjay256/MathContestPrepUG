package com.mathy.contestprep

data class LadderItem(
    val number: Int,
    val key: String,
    val imageRes: Int,
    val backgroundColorRes: Int,
    val prompt: String,
    val answerLabel: String,
    val inputType: LadderInputType,
    val acceptedAnswers: List<String>
) {
    fun isCorrect(userAnswer: String): Boolean {
        val normalized = userAnswer.trim().lowercase()
        return acceptedAnswers.any { it.trim().lowercase() == normalized }
    }
}

sealed class LadderInputType {
    data class Choice(val options: List<String>) : LadderInputType()

    data class SingleInput(
        val hint: String,
        val inputMode: InputMode = InputMode.NUMBER
    ) : LadderInputType()

    data class TripleInput(
        val hint1: String = "",
        val hint2: String = "",
        val hint3: String = ""
    ) : LadderInputType()
}

enum class InputMode {
    NUMBER,
    DECIMAL
}