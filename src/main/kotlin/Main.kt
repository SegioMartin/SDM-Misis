package org.example

import games.LCM
import org.example.games.GeometryProgression
import kotlin.random.Random

fun main() {
    val welcomeMessage = "Welcome to the Brain Games"
    if (Random.nextBoolean()) {
        LCM(welcomeMessage).startGame()
    } else {
        GeometryProgression(welcomeMessage).startGame()
    }
}
