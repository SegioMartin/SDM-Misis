package org.example.games

import games.LCM
import org.example.enums.GameType

abstract class Base {
    companion object {
        const val WELCOME_MESSAGE = "Welcome to the Brain Games"
        const val YOUR_ANSWER = "Your answer: "
        const val ASK_FOR_NAME = "May I have your name? "
        const val CORRECT_ANSWER = "Correct!"

        fun getGame(choice: String): Base? {
            val gameType = getGameTypeByChoice(choice)
            return when (gameType) {
                GameType.GEOMETRY_PROGRESSION -> GeometryProgression()
                GameType.LCM -> LCM()
                else -> null
            }
        }

        fun printGameOptions() {
            println("Выберите игру:")
            GameType.entries.forEachIndexed { index, gameType ->
                println("${index + 1} - ${gameType.displayName}")
            }
        }

        private fun getGameTypeByChoice(choice: String): GameType? =
            try {
                val index = choice.toInt() - 1
                GameType.entries.getOrNull(index)
            } catch (e: NumberFormatException) {
                null
            }
    }

    abstract val gameRules: String
    protected var playerName: String = ""

    fun startGame() {
        welcomeUser()
        for (i in 0..2) {
            askQuestion()
            val userAnswer = readln()
            val rightAnswer = getRightAnswer()
            val hasWin = checkAnswer(userAnswer, rightAnswer)
            writeResult(hasWin, userAnswer, rightAnswer)
        }
    }

    private fun welcomeUser() {
        println(WELCOME_MESSAGE)
        readPlayerName()
        println(gameRules)
    }

    private fun readPlayerName() {
        print(ASK_FOR_NAME)
        playerName = readln()
        println("Hello, $playerName!")
    }

    private fun askQuestion() {
        println("Question: ${getRandomQuestion()}")
        print(YOUR_ANSWER)
    }

    private fun checkAnswer(
        userAnswer: String,
        rightAnswer: String,
    ): Boolean = userAnswer == rightAnswer

    private fun writeResult(
        hasWin: Boolean,
        userAnswer: String,
        rightAnswer: String,
    ) {
        if (hasWin) {
            println(CORRECT_ANSWER)
        } else {
            println("'$userAnswer' is wrong answer ;(. Correct answer was '$rightAnswer'")
            println("Let's try again, $playerName!")
        }
    }

    abstract fun getRightAnswer(): String

    abstract fun getRandomQuestion(): String
}
