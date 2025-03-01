package org.example.games

import kotlin.math.pow
import kotlin.random.Random

class GeometryProgression(
    welcomeMessage: String,
) : Base(welcomeMessage) {
    override val gameRules: String = "What number is missing in the progression?"

    override fun gameCycle() {
        val randomNumbers = getRandomQuestion()
        val indexToRemove = Random.nextInt(randomNumbers.size)
        val rightAnswer = randomNumbers[indexToRemove]

        val question = randomNumbers.map { it.toString() }.toMutableList()
        question[indexToRemove] = ".."
        println("Question: ${question.joinToString(", ")}")

        print("Your answer: ")
        val playerAnswer = readln().toIntOrNull()
        val result = playerAnswer?.let { rightAnswer == playerAnswer }
        when (result) {
            true -> {
                println("Correct!")
            }
            false -> {
                println("'$playerAnswer' is wrong answer ;(. Correct answer was '$rightAnswer'.")
                println("Let's try again, $playerName!")
            }
            null -> {
                println("That's not an integer number ;(")
            }
        }
    }

    private fun getRandomQuestion(
        untilFirst: Int = 10,
        untilCommonRatio: Int = 10,
    ): List<Int> {
        val first = Random.nextInt(1, untilFirst).toDouble()
        val commonRatio = Random.nextInt(1, untilCommonRatio).toDouble()
        val size = Random.nextInt(5, 10)
        return List(size) { (first * commonRatio.pow(it)).toInt() }
    }
}
