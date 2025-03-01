package org.example.games

abstract class Base(
    private val welcomeMessage: String = "Welcome!",
) {
    abstract val gameRules: String
    protected var playerName: String = ""

    fun startGame() {
        println(welcomeMessage)
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
