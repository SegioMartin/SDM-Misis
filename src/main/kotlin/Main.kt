package org.example

import org.example.games.Base

fun main() {
    Base.printGameOptions()
    val choice = readln()
    val game = Base.getGame(choice)
    game?.let { game.startGame() } ?: "Invalid choice"
}
