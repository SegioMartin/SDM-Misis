package games

import org.example.games.Base
import kotlin.random.Random

class LCM : Base() {
    override val gameRules: String = "Find the smallest common multiple of given numbers."

    override fun gameCycle() {
        val randomNumbers = getRandomQuestion()
        println("Question: ${randomNumbers.joinToString(", ")}")
        print("Your answer: ")
        val playerAnswer = readln().toIntOrNull()
        val rightAnswer = lcm(lcm(randomNumbers[0], randomNumbers[1]), randomNumbers[2])
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

    private fun lcm(
        a: Int,
        b: Int,
    ): Int {
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == 0 && lcm % b == 0) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }

    private fun getRandomQuestion(
        size: Int = 3,
        until: Int = 10,
    ): List<Int> = List(size) { Random.nextInt(1, until) }
}
