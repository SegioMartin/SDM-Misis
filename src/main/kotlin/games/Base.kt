package org.example.games

import games.LCM
import org.example.enums.GameType

abstract class Base {
    companion object {
        const val WELCOME_MESSAGE = "Welcome to the Brain Games"

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
        println(WELCOME_MESSAGE)
        readPlayerName()
        println(gameRules)
        while (true) {
            gameCycle()
        }
    }

    private fun readPlayerName() {
        print("May I have your name? ")
        playerName = readln()
        println("Hello, $playerName!")
    }

    abstract fun gameCycle()
}
